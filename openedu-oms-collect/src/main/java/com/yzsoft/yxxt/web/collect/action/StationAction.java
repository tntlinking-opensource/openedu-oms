package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.StationStudent;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationAction extends CollectAction {

	@Resource
	private StationService stationService;

	@Override
	public String getCode() {
		return "02";
	}

	@Override
	public String index() {
		StationStudent stationStudent = entityDao.getEntity(StationStudent.class, "student.user.id", getUserId());
		if (stationStudent == null) {
			if (getSwitch().isEditable()) {
				return redirect("edit");
			}
		}
		put("stationStudent", stationStudent);
		return super.index();
	}

	@Override
	public String edit() {
		StationStudent stationStudent = entityDao.getEntity(StationStudent.class, "student.user.id", getUserId());
		if (stationStudent == null) {
			stationStudent = new StationStudent();
		}
		put("stationStudent", stationStudent);
		put("vehicles", stationService.findVehicle());
		put("stationDate", stationService.getDate());
		put("stations", stationService.findStation());
		put("reasons", stationService.findReason());
		return super.edit();
	}

	@Override
	public String save() {
		StationStudent stationStudent = populateEntity(StationStudent.class, "stationStudent");
		beforeSave(stationStudent);
		entityDao.saveOrUpdate(stationStudent);
		afterSave();
		return redirect("index");
	}
}
