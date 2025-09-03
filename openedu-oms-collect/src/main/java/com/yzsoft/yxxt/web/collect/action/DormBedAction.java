package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.dorm.model.DormRoom;
import com.yzsoft.dorm.model.DormRoomBedType;
import com.yzsoft.dorm.model.DormStudent;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceStudentItem;
import com.yzsoft.yxxt.finance.service.FinanceService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchState;
import net.sf.json.JSONObject;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Controller
@Scope("prototype")
public class DormBedAction
        extends CollectAction {
    @Autowired
    protected FinanceService financeService;
    @Autowired
    protected FinanceStudentService financeStudentService;
    @Autowired
    protected com.yzsoft.yxxt.finance.service.DormService dormService;
    @Autowired
    protected com.yzsoft.dorm.system.service.DormService dormService2;

    public String getCode() {
        return "G042";
    }

    public String index() {
        CollectSwitch collectSwitch = getSwitch();
        CollectSwitchState collectSwitchState = this.collectService.getState(getUserId(), getCode());
        if ((collectSwitchState == null) && (collectSwitch.isEditable())) {
            return redirect("edit");
        }

        return super.index();
    }

    protected void indexSetting() {
        super.indexSetting();
        Student student = getStudent();
        StudentInfo studentInfo = student.getInfo();
        FinanceStudent financeStudent = this.financeStudentService.getOrCreateFinanceStudent(student);
        DormPlanBed dormPlanBed = getDormPlanBed(student);
        
        //20201106增加是否取消宿舍功能 begin
        CollectSwitch collectSwitch = getSwitch();
        put("adflag", collectSwitch.isAdflag());
        //20201106增加是否取消宿舍功能 end
        put("flag", Boolean.valueOf(false));
        put("studentInfo", studentInfo);
        put("financeStudent", financeStudent);
        put("financeStudentItem", getFinanceStudentItem(financeStudent, "003"));
        put("dormPlanBed", dormPlanBed);

        //20230823 修复学生可以一直选择床位，导致占用多个床位的bug
        boolean bedFlag=false;
        OqlBuilder<DormPlanBed> oqlBuilder = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        oqlBuilder.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        oqlBuilder.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", new Object[]{student.getAdminClass()});
        oqlBuilder.where("dormPlanBed.student =:student",student);
        List<DormPlanBed> dormPlanBeds = this.entityDao.search(oqlBuilder);
        if(dormPlanBeds.size()>0){
            bedFlag=true;
        }
        put("bedFlag",bedFlag);
    }

    private FinanceStudentItem getFinanceStudentItem(FinanceStudent financeStudent, String code) {
        if (financeStudent == null) {
            return null;
        }
        for (FinanceStudentItem item : financeStudent.getItems()) {
            if (item.getItem().getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public String edit() {
        indexSetting();


        return super.edit();
    }

    public String save() {
        boolean accommodationed = getBool("accommodationed");


        Student student = getStudent();


        StudentInfo studentInfo = student.getInfo();
        studentInfo.setAccommodationed(Boolean.valueOf(accommodationed));
        this.entityDao.saveOrUpdate(new Object[]{studentInfo});
        afterSave();
        return redirect("index");
    }

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
//		query.select("distinct dormPlanBed.bed.room");
        query.select("dormPlanBed.bed.room");
        query.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        query.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", new Object[]{student.getAdminClass()});
        query.where("dormPlanBed.student is null");
        query.orderBy("dormPlanBed.bed.room.code");
        put("dormPlanBed", dormPlanBed);
        List<DormPlanBed> list = this.entityDao.search(query);
        list = new ArrayList<DormPlanBed>(new HashSet<DormPlanBed>(list));
        try {
            Collections.sort(list, new MultiPropertyComparator<DormPlanBed>("bed.room.code"));
        } catch (Exception e) {
        }
        put("rooms", this.entityDao.search(query));

        OqlBuilder<DormPlanBed> oqlBuilder = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        oqlBuilder.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        oqlBuilder.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", new Object[]{student.getAdminClass()});
        oqlBuilder.where("dormPlanBed.student is null");
        List<DormPlanBed> dormPlanBeds = this.entityDao.search(oqlBuilder);
        put("dormPlanBeds", dormPlanBeds);

        return forward();
    }

    public String bedSelect() {
        checkSwitch();
        Student student = getStudent();
        DormPlanBed dormPlanBed = getDormPlanBed(student);
        DormRoom dormRoom = (DormRoom) this.entityDao.get(DormRoom.class, getLong("dormRoomId"));
        DormRoomBedType dormRoomBedType = getDormRoomBedType(dormRoom);
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        query.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        query.where("dormPlanBed.bed.room = :room", new Object[]{dormRoom});
        query.where("dormPlanBed.student is null");
        query.where("dormPlanBed.bed.enabled=1");
        put("dormPlanBed", dormPlanBed);
        put("dormRoomBedType", dormRoomBedType);


        OqlBuilder<DormPlanBed> oqlBuilder = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        oqlBuilder.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
        oqlBuilder.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", new Object[]{student.getAdminClass()});
        oqlBuilder.where("dormPlanBed.student is null");
        List<DormPlanBed> dormPlanBeds = this.entityDao.search(oqlBuilder);
        ArrayList<DormPlanBed> dormPlanBeds1 = new ArrayList();
        for (DormPlanBed d : dormPlanBeds) {
            if (d.getBed().getRoom().getId() == dormRoom.getId()) {
                dormPlanBeds1.add(d);
            }
        }
        put("dormPlanBeds", dormPlanBeds1);
        return forward();
    }

    private DormRoomBedType getDormRoomBedType(DormRoom dormRoom) {
        OqlBuilder query = OqlBuilder.from(DormRoomBedType.class);
        query.where("bedNum = :bedNum", new Object[]{dormRoom.getBedNum()});
        query.cacheable();
        List<DormRoomBedType> bedTypes = this.entityDao.search(query);
        return bedTypes.isEmpty() ? null : (DormRoomBedType) bedTypes.get(0);
    }

    protected DormPlanBed getDormPlanBed(Student student) {
        return (DormPlanBed) this.entityDao.getEntity(DormPlanBed.class, "student.id", new Object[]{student.getId()});
    }

    public String bedSave() {
        JSONObject json = new JSONObject();
        try {
            JSONObject data = new JSONObject();
            Student student = getStudent();
            Long dormPlanBedId = getLong("dormPlanBedId");
            DormPlanBed dormPlanBedSrc = getDormPlanBed(student);
            //20230823 修复学生可以一直选择床位，导致占用多个床位的bug
            OqlBuilder<DormPlanBed> oqlBuilder = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
            oqlBuilder.where("dormPlanBed.bed.room.gender = :gender", student.getGender().getName());
            oqlBuilder.where("dormPlanBed.classAccomPlan.adminClass = :adminClass", new Object[]{student.getAdminClass()});
            oqlBuilder.where("dormPlanBed.student =:student",student);
            List<DormPlanBed> dormPlanBeds = this.entityDao.search(oqlBuilder);
            if(dormPlanBeds.size()>0){
                for(DormPlanBed dormPlanBed:dormPlanBeds){
                    dormPlanBed.setStudent(null);
                }
                this.entityDao.saveOrUpdate(dormPlanBeds);
            }

            DormPlanBed dormPlanBed = (DormPlanBed) this.entityDao.get(DormPlanBed.class, dormPlanBedId);
            Assert.notNull(dormPlanBed, "床位ID有误");
            Assert.isNull(dormPlanBed.getStudent(), "该床位已有学生");
            Assert.isTrue(dormPlanBed.getClassAccomPlan().getAdminClass().equals(student.getAdminClass()), "403");
            dormPlanBed.setStudent(student);
            this.entityDao.saveOrUpdate(new Object[]{dormPlanBed});
            this.dormService2.check(student.getId(), dormPlanBed.getBed().getId());
            if (dormPlanBedSrc != null) {
                dormPlanBedSrc.setStudent(null);
                this.entityDao.saveOrUpdate(new Object[]{dormPlanBedSrc});
            }
            json.put("data", data);
            json.put("success", "OK");
        } catch (Exception e) {
            json.put("error", e.getMessage());
        }
        put("json", json.toString());
        return forward();
    }

    public String bedCancle() {
        Student student = getStudent();
        DormPlanBed dormPlanBed = getDormPlanBed(student);
        if (dormPlanBed != null) {
            dormPlanBed.setStudent(null);
            this.entityDao.saveOrUpdate(new Object[]{dormPlanBed});
            this.dormService2.checkOut(student.getId());
        }
        return redirect("index");
    }

    public boolean isDormPlanedStudent(Student student) {
        if (student == null) return false;
        OqlBuilder<DormStudent> query = OqlBuilder.from(DormStudent.class, "dormStudent");
        query.where("dormStudent.student.id =:id", student.getId());
        List<DormStudent> dormStudents = entityDao.search(query);
        return dormStudents.size() != 0;
    }
}
