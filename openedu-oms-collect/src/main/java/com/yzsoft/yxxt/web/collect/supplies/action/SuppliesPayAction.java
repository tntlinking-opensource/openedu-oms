package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import com.yzsoft.yxxt.finance.model.FinanceOrderItem;
import com.yzsoft.yxxt.finance.service.FinancePay;
import com.yzsoft.yxxt.finance.service.FinanceOrderService;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import com.yzsoft.yxxt.web.collect.supplies.service.SuppliesService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesPayAction extends SecurityActionSupport {

	private static final String FINANCE_ORDER_KEY = "SUPPLIES";

	@Resource
	private SuppliesService suppliesService;
	@Resource
	private FinanceOrderService financeOrderService;

	/**
	 * 在线支付
	 *
	 * @return
	 */
	public String index() {
		FinanceOrder financeOrder = financeOrderService.getOrder(getUserId(), FINANCE_ORDER_KEY);
		//只能存在一个订单
		if (financeOrder != null) {
			FinancePay.info(financeOrder);
			return null;
		}
		SuppliesStd suppliesStd = suppliesService.get(getUserId());

		financeOrder = financeOrderService.createOrder(getCurrentUser(), FINANCE_ORDER_KEY, "生活物品", suppliesStd.getTotal());
		for (SuppliesStdItem sitem : suppliesStd.getItems()) {
			if (sitem.getTotal() == null || sitem.getTotal() == 0) {
				continue;
			}
			FinanceOrderItem fitem = new FinanceOrderItem();
			fitem.setOrder(financeOrder);
			fitem.setName(sitem.getSupplies().getName());
			fitem.setNum(sitem.getNum());
			fitem.setMoney(sitem.getTotal());
			financeOrder.getItems().add(fitem);
		}
		entityDao.saveOrUpdate(financeOrder);
		FinancePay.gotoPay(financeOrder);
		return null;
	}
}
