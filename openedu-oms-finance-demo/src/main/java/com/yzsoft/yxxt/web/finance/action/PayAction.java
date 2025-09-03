package com.yzsoft.yxxt.web.finance.action;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import com.yzsoft.yxxt.finance.service.FinancePay;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 支付页面
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PayAction extends FinancePayAction {

	@Override
	protected void indexSetting(FinanceOrder financeOrder) {
		super.indexSetting(financeOrder);
		//设置接口参数
	}

	public String notice() {
		//过滤非法请求
		String code = get("code");
		financeOrderService.finishOrder(code);
		//返回值，告诉在线支付通知成功
//		return "OK";
		FinanceOrder financeOrder = entityDao.getEntity(FinanceOrder.class, "code", code);
		FinancePay.info(financeOrder);
		return null;
	}
}
