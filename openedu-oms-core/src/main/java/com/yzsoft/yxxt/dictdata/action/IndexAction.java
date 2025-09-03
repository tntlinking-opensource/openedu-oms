package com.yzsoft.yxxt.dictdata.action;

import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends BaseAction {

	public String index() {
		return forward();
	}

}
