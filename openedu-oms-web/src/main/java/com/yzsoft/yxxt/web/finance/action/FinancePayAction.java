package com.yzsoft.yxxt.web.finance.action;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import com.yzsoft.yxxt.finance.service.FinanceOrderService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * 支付
 */
public class FinancePayAction extends BaseAction {

	@Resource
	protected FinanceOrderService financeOrderService;

	public String index() {
		Long id = getLong("id");
		Assert.notNull(id, "订单ID不能为空");
		FinanceOrder financeOrder = entityDao.get(FinanceOrder.class, id);
		Assert.notNull(financeOrder, "订单ID无效");
		//如果订单已完成，则跳转到查看页面
		if (financeOrder.isFinished()) {
			return redirect("info", null, "id=" + id);
		}
		indexSetting(financeOrder);
		put("financeOrder", financeOrder);
		return forward();
	}

	protected void indexSetting(FinanceOrder financeOrder) {
	}

	public String info() {
		Long id = getLong("id");
		FinanceOrder financeOrder = entityDao.get(FinanceOrder.class, id);
		put("financeOrder", financeOrder);
		return forward();
	}
}
