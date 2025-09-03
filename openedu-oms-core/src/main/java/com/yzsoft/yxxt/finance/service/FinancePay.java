package com.yzsoft.yxxt.finance.service;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FinancePay {
	/**
	 * 支付页面
	 */
	public static final String PAY_ACTION = "/web/finance/pay.action";
	public static final String INFO_ACTION = "/web/info/finance.action";

	public static void gotoPay(FinanceOrder financeOrder) {
		redirect(PAY_ACTION + "?id=" + financeOrder.getId());
	}

	public static void info(FinanceOrder financeOrder) {
		redirect(INFO_ACTION + "?method=info&id=" + financeOrder.getId());
	}

	private static void redirect(String url) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			response.sendRedirect(request.getContextPath() + url);
		} catch (IOException e) {
		}
	}
}
