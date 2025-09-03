package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.product.core.model.StudentFamily;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentFamilyManagerAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;

	@Override
	protected String getEntityName() {
		return StudentFamily.class.getName();
	}

	@Override
	public String index() {
		put("year", yxxtService.getYear());
		return super.index();
	}

}
