package com.yzsoft.yxxt.web.manager.action;

import org.beangle.struts2.action.BaseAction;
import org.beangle.workflow3.view.action.BaseFwptAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ViewSetAction extends BaseAction {

	public String index() {
		List<String[]> list = new ArrayList<String[]>();
		list.add(new String[]{"首页", "view-set-index", "_blank"});
		put("list", list);
		return forward();
	}
}
