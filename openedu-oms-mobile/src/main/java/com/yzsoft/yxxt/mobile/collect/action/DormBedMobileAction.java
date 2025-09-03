package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.dorm.model.DormStudent;
import com.yzsoft.yxxt.web.collect.action.DormBedAction;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormBedMobileAction extends DormBedAction {


    public String bedEdit() {
        checkSwitch();
        Student student = getStudent();
		//住宿计划外的新生选不了宿舍
		if (!isDormPlanedStudent(student)) {
			ArrayList<DormPlanBed> dormPlanBeds = new ArrayList<DormPlanBed>();
			put("dormPlanBed", null);
			put("rooms", dormPlanBeds);
			put("dormPlanBeds", dormPlanBeds);
			return forward();
		}
        DormPlanBed dormPlanBed = getDormPlanBed(student);
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        query.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        query.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", student.getAdminClass());
        query.where("dormPlanBed.student is null");
        query.orderBy("dormPlanBed.bed.code");
        put("dormPlanBed", dormPlanBed);
        put("dormPlanBeds", entityDao.search(query));
        return forward();
    }

}
