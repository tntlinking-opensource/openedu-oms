package com.yzsoft.yxxt.core.action;

import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.MajorService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MajorSelectAction extends BaseAction {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private MajorService majorService;

	public String index() {
		put("majorIds", get("majorIds"));
		put("departments", departmentService.findTeachingDepartment());
		put("majors", majorService.findAllMajor());
		return forward();
	}
}
