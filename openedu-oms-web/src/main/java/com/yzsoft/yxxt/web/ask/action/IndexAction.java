package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.service.AskService;
import com.yzsoft.yxxt.web.ask.model.AskCommon;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends BaseAction {

	@Resource
	private AskService askService;

	public String index() {
		return forward();
	}

	public String search() {
		String key = get("key");
		PageLimit limit = getPageLimit();
		List<AskCommon> asks = askService.findAskCommon(key, limit);
		put("asks", asks);
		return forward();
	}

}
