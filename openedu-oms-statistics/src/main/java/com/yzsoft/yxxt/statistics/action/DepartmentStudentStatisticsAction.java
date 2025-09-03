package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.statistics.dto.CountData;
import com.yzsoft.yxxt.statistics.service.StudentStatisticsService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.TeacherService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 需要有院系权限，功能开发后会给各院系的管理员设置权限，每个院系只能看自己的数据。
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DepartmentStudentStatisticsAction extends BaseAction {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private StudentStatisticsService studentStatisticsService;

	public String index() {
		return forward();
	}

	/**
	 * 专业统计
	 * @return
	 */
	public String major() {
		return forward();
	}

	/**
	 * 当前年份专业统计
	 * @return
	 */
	public String majorCurrent() {
		CountData data = studentStatisticsService.majorCurrent(getDepartment());
		put("data", data);
		return forward();
	}

	/**
	 * 历年年份专业统计
	 * @return
	 */
	public String majorHistory() {
		CountData data = studentStatisticsService.majorHistory(getDepartment());
		put("data", data);
		return forward();
	}

	/**
	 * 民族统计
	 * @return
	 */
	public String nation() {
		return forward();
	}

	public String nationCurrent() {
		CountData data = studentStatisticsService.nationCurrent(getDepartment());
		put("data", data);
		return forward();
	}

	public String nationHistory() {
		CountData data = studentStatisticsService.nationHistory(getDepartment());
		put("data", data);
		return forward();
	}

	/**
	 * 生源地统计
	 * @return
	 */
	public String city() {
		return forward();
	}

	public String cityCurrent() {
		CountData data = studentStatisticsService.cityCurrent(getDepartment());
		put("data", data);
		return forward();
	}

	public String cityHistory() {
		CountData data = studentStatisticsService.cityHistory(getDepartment());
		put("data", data);
		return forward();
	}

	/**
	 * 性别统计
	 * @return
	 */
	public String gender() {
		return forward();
	}

	public String genderCurrent() {
		CountData data = studentStatisticsService.genderCurrent(getDepartment());
		put("data", data);
		return forward();
	}

	public String genderHistory() {
		CountData data = studentStatisticsService.genderHistory(getDepartment());
		put("data", data);
		return forward();
	}

	/**
	 * 住宿情况
	 * @return
	 */
	public String dorm() {
		return forward();
	}

	public String dormCurrent() {
		CountData data = studentStatisticsService.dormCurrent(getDepartment());
		put("data", data);
		return forward();
	}

	public String dormHistory() {
		CountData data = studentStatisticsService.dormHistory(getDepartment());
		put("data", data);
		return forward();
	}

	private Department getDepartment() {
		Teacher teacher = teacherService.getTeacherByCode(SecurityUtils.getUsername());
		if (teacher != null) {
			return teacher.getDepartment();
		}
		return null;
	}
}
