package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationManagerAction extends EntityDrivenAction{

    @Resource
    private StationService stationService;

	@Override
	protected String getEntityName() {
		return Station.class.getName();
	}

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("vehicles", stationService.findVehicle());
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
    }
}
