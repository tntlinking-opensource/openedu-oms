package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.yxxt.welcome.process.service.DormAllocService;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormAllocAction extends ProcessLinkActionSupport {


    @Resource
    protected DormAllocService dormAllocService;
//    @Resource
//    private DormAllocService dormAllocService;

    @Override
    protected void editSetting(Long batchId, Student student) {

        super.editSetting(batchId, student);

//        DeptAccomPlan deptAccomPlan = dormAllocService.getDeptAccomPlan(yxxtService.getGrade(), student);
//
//        Integer totalNum = dormPlanService.getBedTotalNumByStudent(student.getId());
//        Integer freeNum = dormPlanService.getBedFreeNumByStudent(student.getId());
//        DormBed bed = dormPlanService.getBed(student.getId());
//        put("totalNum", totalNum);
//        put("freeNum", freeNum);
//        put("bed", bed);

        DormPlanBed dormPlanBed = dormAllocService.getDormPlanBed(student.getId());
        put("dormPlanBed", dormPlanBed);
    }

    @Override
    protected void save(Long batchId, Student student) {
        super.save(batchId, student);

        Long planBedId = getLong("planBedId");
//        dormAllocService.checkIn(student.getId(), bedId);
//        DormStudent dormStudent = dormAllocService.getDormStudent(student.getId());
//        dormAllocService.alloc(dormStudent.getId(), bedId);
        if (planBedId != null) {
            dormAllocService.chooseBed(student, planBedId);
        }
    }

    @Override
    protected void cancel(Long batchId, Student student) {
        super.cancel(batchId, student);
//        dormPlanService.checkOut(student.getId());
//        DormStudent dormStudent = dormAllocService.getDormStudent(student.getId());
//        dormAllocService.allocCancle(new Long[]{dormStudent.getId()});
    }

    public String chooseBed() {
        return forward();
    }

    public String findZone() {
        put("datas", dormAllocService.findZone(getLong("studentId")));
        return forward("json");
    }

    public String findBuilding() {
        Long zoneId = getLong("zoneId");
        put("datas", dormAllocService.findBuilding(zoneId, getLong("studentId")));
        return forward("json");
    }

    public String findRoom() {
        Long buildingId = getLong("buildingId");
        put("datas", dormAllocService.findRoom(buildingId, getLong("studentId")));
        return forward("json");
    }

    public String findBed() {
        Long roomId = getLong("roomId");
        put("datas", dormAllocService.findBed(roomId, getLong("studentId")));
        return forward("json");
    }

}
