package com.yzsoft.yxxt.web.finance.action;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import com.yzsoft.yxxt.finance.service.FinanceOrderService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.util.Assert;

import javax.annotation.Resource;

public class FinanceOrderInfoAction extends SecurityActionSupport {

	@Resource
	private FinanceOrderService financeOrderService;

	@Override
	protected String getEntityName() {
		return FinanceOrder.class.getName();
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("financeOrders", entityDao.search(getOqlBuilder()));
	}

	@Override
	protected OqlBuilder<?> getOqlBuilder() {
		OqlBuilder query = super.getOqlBuilder();
		query.where("user.id = :userid", getUserId());
		return query;
	}

	/**
	 * 取消订单
	 *
	 * @return
	 */
	public String cancle() {
		FinanceOrder financeOrder = (FinanceOrder) getEntity();
		Assert.notNull(financeOrder);
		Assert.notNull(financeOrder.getUser());
		Assert.isTrue(financeOrder.getUser().getId().equals(getUserId()));
		financeOrderService.cancleOrder(financeOrder.getCode());
		return redirect("info", null, "id=" + financeOrder.getId());
	}
}
