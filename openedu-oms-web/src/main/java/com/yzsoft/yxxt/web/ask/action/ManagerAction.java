package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.model.AskCommon;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Date;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ManagerAction extends SecurityActionSupport {

	@Override
	protected String getEntityName() {
		return AskCommon.class.getName();
	}

	@Override
	protected String getDefaultOrderString() {
		return "top desc, sort";
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		AskCommon askCommon = (AskCommon) entity;
		askCommon.setCreateTime(new Date());
		return super.saveAndForward(entity);
	}

	public String activate() {
		Long[] askCommonIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<AskCommon> askCommons = entityDao.get(AskCommon.class, askCommonIds);
		for (AskCommon askCommon : askCommons) {
			askCommon.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(askCommons);
		return redirect("search", "info.save.success");
	}
}
