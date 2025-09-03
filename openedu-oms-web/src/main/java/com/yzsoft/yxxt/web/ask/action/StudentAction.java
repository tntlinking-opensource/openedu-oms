package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.service.AskService;
import com.yzsoft.yxxt.web.ask.model.AskStudent;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentAction extends SecurityActionSupport {

	@Resource
	private AskService askService;

	public String index() {
//		put("plates", askService.findPlate());
		return forward();
	}

	public String search() {
		String key = get("key");
		PageLimit limit = getPageLimit();
		List<AskStudent> asks = askService.findAsk(getCurrentUser(), key, limit);
		put("asks", asks);
		return forward();
	}

}
