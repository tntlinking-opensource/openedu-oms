package com.yzsoft.yxxt.web.collect.freemarker;

import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.struts2.view.freemarker.TemplateDirective;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class CollectSwitches extends TemplateDirective {

	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		super.execute(env, params, loopVars, body);
		CollectService collectService = getBean(CollectService.class);
//		put("switches", collectService.findSwitch());
		put("switches", collectService.findSwitch(SecurityUtils.getUsername()));
		put("states", collectService.findState(SecurityUtils.getUserId()));
		body.render(env.getOut());
	}
}
