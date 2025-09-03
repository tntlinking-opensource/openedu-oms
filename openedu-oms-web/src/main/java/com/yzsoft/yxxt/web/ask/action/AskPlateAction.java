package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.service.AskService;
import com.yzsoft.yxxt.web.ask.model.AskPlate;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AskPlateAction extends EntityDrivenAction {

	@Resource
	private AskService askService;

	@Override
	protected String getEntityName() {
		return AskPlate.class.getName();
	}

	public String activate() {
		Long[] askPlateIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<AskPlate> askPlates = entityDao.get(AskPlate.class, askPlateIds);
		for (AskPlate askPlate : askPlates) {
			askPlate.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(askPlates);
		return redirect("search", "info.save.success");
	}

	public String move(){
		return forward();
	}

	public String moveSave(){
		Long askPlateSourceId = getLong("askPlateSourceId");
		Long askPlateTargetId = getLong("askPlateTargetId");
		askService.moveMsg(askPlateSourceId, askPlateTargetId);
		return forward();
	}

}
