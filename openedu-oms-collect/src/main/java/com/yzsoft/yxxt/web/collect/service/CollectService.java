package com.yzsoft.yxxt.web.collect.service;


import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchState;

import java.util.List;

public interface CollectService {

	/**
	 * 查询所有开关
	 * @return
	 */
	List<CollectSwitch> findSwitch();
	List<CollectSwitch> findSwitch(String  username);

	/**
	 * 根据代码查询开关
	 * @param code 采集代码
	 * @return
	 */
	CollectSwitch getSwitch(String code);

	/**
	 * 标记为已采集
	 * @param userId 用户ID
	 * @param code 采集代码
	 */
	void finish(Long userId, String username, String code);
	/**
	 * 标记为未采集
	 * @param userId 用户ID
	 * @param code 采集代码
	 */
	void cancle(Long userId, String code);

	/**
	 * 查询用户采集状态
	 * @param userId
	 * @return
	 */
	List<CollectSwitchState> findState(Long userId);

	CollectSwitchState getState(Long userId, String code);
}
