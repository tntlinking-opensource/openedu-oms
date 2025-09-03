package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.statistics.dto.CountData;
import com.yzsoft.yxxt.statistics.service.StudentStatisticsService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 全校迎新统计
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentStatisticsAction extends BaseAction {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentStatisticsService studentStatisticsService;

	public String index() {
		return forward();
	}

	/**
	 * 院系统计
	 *
	 * @return
	 */
	public String department() {
		return forward();
	}

	public String departmentCurrent() {
		CountData data = studentStatisticsService.departmentCurrent();
		put("data", data);
		return forward();
	}

	public String departmentHistory() {
		CountData data = studentStatisticsService.departmentHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 专业统计
	 *
	 * @return
	 */
	public String major() {
		return forward();
	}

	/**
	 * 当前年份专业统计
	 *
	 * @return
	 */
	public String majorCurrent() {
		CountData data = studentStatisticsService.majorCurrent();
		put("data", data);
		return forward();
	}

	/**
	 * 历年年份专业统计
	 *
	 * @return
	 */
	public String majorHistory() {
		CountData data = studentStatisticsService.majorHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 民族统计
	 *
	 * @return
	 */
	public String nation() {
		return forward();
	}

	public String nationCurrent() {
		CountData data = studentStatisticsService.nationCurrent();
		put("data", data);
		return forward();
	}

	public String nationHistory() {
		CountData data = studentStatisticsService.nationHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 生源地统计
	 *
	 * @return
	 */
	public String city() {
		return forward();
	}

	public String cityCurrent() {
		CountData data = studentStatisticsService.cityCurrent();
		put("data", data);
		return forward();
	}

	public String cityHistory() {
		CountData data = studentStatisticsService.cityHistory();
		put("data", data);
		return forward();
	}

	/**
	 * 性别统计
	 *
	 * @return
	 */
	public String gender() {
		return forward();
	}

	public String genderCurrent() {
		CountData data = studentStatisticsService.genderCurrent();
		put("data", data);
		return forward();
	}

	public String genderHistory() {
		CountData data = studentStatisticsService.genderHistory();
		put("data", data);
		return forward();
	}

}
