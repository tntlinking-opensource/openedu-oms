package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.model.PrepareConfig;
import com.yzsoft.yxxt.prepare.service.PrepareService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.print.action.PrintDesignerBaseAction;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrepareConfigAction extends EntityDrivenAction {

	@Resource
	private PrepareService prepareService;

	@Override
	protected String getEntityName() {
		return PrepareConfig.class.getName();
	}

	@Override
	public String index() {
		put("prepareConfig", prepareService.getConfig());
		return forward();
	}

	@Override
	public String edit() {
		put("prepareConfig", prepareService.getConfig());
		put("passwordTypes", prepareService.findPasswordType());
		return forward();
	}

	@Override
	public String save() {
		PrepareConfig prepareConfig = prepareService.getConfig();
		populate(prepareConfig, "prepareConfig");
		entityDao.saveOrUpdate(prepareConfig);
		return redirect("index", "info.save.success");
	}


}
