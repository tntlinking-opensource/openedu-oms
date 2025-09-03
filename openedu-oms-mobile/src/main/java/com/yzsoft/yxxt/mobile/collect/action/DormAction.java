package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.welcome.process.service.DormAllocService;
import org.beangle.model.Entity;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormAction extends CollectAction {

	@Resource
	protected DormAllocService dormAllocService;

	@Override
	public String getCode() {
		return "SS_01";
	}

	@Override
	public String index() {
		Student student = getStudent();
		DormPlanBed dormPlanBed = dormAllocService.getDormPlanBed(student.getId());
		if (dormPlanBed == null) {
			if (getSwitch().isEditable()) {
				return redirect("edit");
			}
		}
		put("dormPlanBed", dormPlanBed);
		return super.index();
	}

	@Override
	public String edit() {
		Student student = getStudent();
		DormPlanBed dormPlanBed = dormAllocService.getDormPlanBed(student.getId());
		put("dormPlanBed", dormPlanBed);
		return super.edit();
	}

	@Override
	public String save() {
		checkSwitch();
		Student student = getStudent();
		Long planBedId = getLong("planBedId");
		dormAllocService.chooseBed(student, planBedId);
		return redirect("index");
	}


	public String findZone() {
		Student student = getStudent();
		put("datas", dormAllocService.findZone(student.getId()));
		return forward("json");
	}

	public String findBuilding() {
		Student student = getStudent();
		Long zoneId = getLong("zoneId");
		put("datas", dormAllocService.findBuilding(zoneId, student.getId()));
		return forward("json");
	}

	public String findRoom() {
		Student student = getStudent();
		Long buildingId = getLong("buildingId");
		put("datas", dormAllocService.findRoom(buildingId, student.getId()));
		return forward("json");
	}

	public String findBed() {
		Student student = getStudent();
		Long roomId = getLong("roomId");
		put("datas", dormAllocService.findBed(roomId, student.getId()));
		return forward();
	}

}
