package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.ClothesSize;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesSizeAction extends EntityDrivenAction {

	@Override
	protected String getEntityName() {
		return ClothesSize.class.getName();
	}

	@Override
	protected String getDefaultOrderString() {
		return "code";
	}
}
