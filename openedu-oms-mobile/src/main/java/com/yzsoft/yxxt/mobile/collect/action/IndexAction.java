package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.yxxt.web.collect.service.MobileCollectAction;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.struts2.action.BaseAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends BaseAction {

	@Resource
	private CollectService collectService;

	public String index() {
		List<CollectSwitch> switches = collectService.findSwitch(SecurityUtils.getUsername());
		for (Iterator<CollectSwitch> itor = switches.iterator(); itor.hasNext(); ) {
			CollectSwitch collectSwitch = itor.next();
			if (!MobileCollectAction.hasAction(collectSwitch.getCode())) {
				itor.remove();
			}
		}
		put("switches", switches);
		put("states", collectService.findState(SecurityUtils.getUserId()));
		return forward();
	}

	public String info() {
		String code = get("code");
		Class action = MobileCollectAction.getAction(code);
		return redirect(new Action(action));
	}
}
