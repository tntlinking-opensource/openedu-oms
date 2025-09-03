package com.yzsoft.yxxt.finance.service;

import com.yzsoft.yxxt.finance.model.FinanceOrder;
import org.beangle.ems.security.User;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface FinanceOrderService {

	String STATE = "FINANCE_ORDER_STATE";
	String NOT_PAED = "FINANCE_ORDER_STATE_01";
	String FINISHED = "FINANCE_ORDER_STATE_02";
	String CANCLED = "FINANCE_ORDER_STATE_03";

	List<DictData> findState();

	/**
	 * 创建订单
	 *
	 * @param user  用户
	 * @param key   业务关键字
	 * @param name  订单名称
	 * @param money 订单金额
	 * @return
	 */
	FinanceOrder createOrder(User user, String key, String name, Double money);

	/**
	 * 完成支付
	 *
	 * @param code 订单号
	 */
	void finishOrder(String code);

	/**
	 * 取消订单
	 *
	 * @param code 订单号
	 */
	void cancleOrder(String code);

	/**
	 * 查询订单
	 *
	 * @param userId 用户ID
	 * @param key    业务关键字
	 * @return
	 */
	FinanceOrder getOrder(Long userId, String key);
}
