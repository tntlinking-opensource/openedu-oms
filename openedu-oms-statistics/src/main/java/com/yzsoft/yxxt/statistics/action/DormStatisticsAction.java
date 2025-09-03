package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.statistics.dto.CountData;
import com.yzsoft.yxxt.statistics.service.DormStatisticsService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 宿舍统计
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormStatisticsAction extends BaseAction {

	@Autowired
	private DormStatisticsService dormStatisticsService;

	public String index() {
		return forward();
	}

	/**
	 * 住宿情况统计
	 *
	 * @return
	 */
	public String student() {
		return forward();
	}

	public String studentCurrent() {
		CountData data = dormStatisticsService.studentCurrent();
		put("data", data);
		return forward();
	}

	public String studentHistory() {
		CountData data = dormStatisticsService.studentHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 寝室使用率统计
	 *
	 * @return
	 */
	public String room() {
		return forward();
	}

	public String roomCurrent() {
		CountData data = dormStatisticsService.roomCurrent();
		put("data", data);
		return forward();
	}

	public String roomHistory() {
		CountData data = dormStatisticsService.roomHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 床位利用率统计
	 *
	 * @return
	 */
	public String bed() {
		return forward();
	}

	public String bedCurrent() {
		CountData data = dormStatisticsService.bedCurrent();
		put("data", data);
		return forward();
	}

	public String bedHistory() {
		CountData data = dormStatisticsService.bedHistory();
		put("data", data);
		return forward();
	}

}
