package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.model.StationReason;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationReasonManagerAction extends EntityDrivenAction{

	@Override
	protected String getEntityName() {
		return StationReason.class.getName();
	}
}
