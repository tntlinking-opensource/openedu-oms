package com.yzsoft.yxxt.welcome.process.service.impl;

import com.yzsoft.dorm.model.*;
import com.yzsoft.dorm.web.collect.service.DormChooseService;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.welcome.process.service.DormAllocService;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DormAllocServiceImpl extends EntityDaoSupport implements DormAllocService {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private DormChooseService dormChooseService;

    @Override
    public void chooseBed(Student student, Long planBedId) {
        DormPlanBed planBed = entityDao.get(DormPlanBed.class, planBedId);
        cancleCooseBed(student);
        //验证是否已选择床位
//        Assert.isTrue(!dormChooseService.checkHasChooseBed(student, dormChooseService.getSystemYear()), "你已选择好床位，请不要重复选择！");
        Assert.isNull(planBed.getStudent(), "对不起，床位已被占用，请选择其他床位！");
        int update = dormChooseService.chooseBed(planBed.getId(), student);
        Assert.isTrue(update == 1, "对不起，床位已被占用，请选择其他床位！");
        dormChooseService.updateDormStudent(planBed.getId(), student.getId(), planBed.getDeptAccomPlan().getDormPlan().getId());
    }

    public void cancleCooseBed(Student student) {
        DormPlanBed planBed = entityDao.getEntity(DormPlanBed.class, "student.id", student.getId());
        if (planBed != null) {
            planBed.setStudent(null);
            entityDao.saveOrUpdate(planBed);
        }
    }

    @Override
    public PlanType getPlanType() {
        //自动识别选择计划（判断院系计划是否为空）
        String year = yxxtService.getGrade();
        long count = entityDao.count(ClassAccomPlan.class, "dormPlan.year", year);//班级计划
        if (count > 0) {
            return PlanType.CLASS;
        } else {
            count = entityDao.count(MajorAccomPlan.class, "dormPlan.year", year);//专业计划
            if (count > 0) {
                return PlanType.MAJOR;
            } else {
                return PlanType.DEPT;
            }
        }
    }

    @Override
    public DormPlanBed getDormPlanBed(Long studentId) {
        return entityDao.getEntity(DormPlanBed.class, "student.id", studentId);
    }

    @Override
    public DormStudent getDormStudent(Long studentId) {
        return entityDao.getEntity(DormStudent.class, "student.id", studentId);
    }

    private OqlBuilder getQuery(Long studentId) {
        Student student = entityDao.get(Student.class, studentId);
        OqlBuilder query = OqlBuilder.from(DormPlanBed.class, "planBed");
//         query.where("planBed.plan.grade = :grade", student.getGrade());
//         query.where("planBed.plan.department = :department", student.getDepartment());
//         query.where("planBed.plan.gender = :gender", student.getGender().getName());

        String year = dormChooseService.getSystemYear();
        ClassAccomPlan classAccomPlan = dormChooseService.getClassAccomPlan(year, student);
        if (classAccomPlan != null) {
            query.where("planBed.classAccomPlan = :classAccomPlan", classAccomPlan);
        } else {
            MajorAccomPlan majorAccomPlan = dormChooseService.getMajorAccomPlan(year, student);
            if (majorAccomPlan != null) {
                query.where("planBed.majorAccomPlan = :majorAccomPlan", majorAccomPlan);
            } else {
                DeptAccomPlan deptAccomPlan = dormChooseService.getDeptAccomPlan(year, student);
                if (deptAccomPlan != null) {
                    query.where("planBed.deptAccomPlan = :deptAccomPlan", deptAccomPlan);
                } else {
                    query.where("1 = 2");
                }
            }
        }
        query.where("planBed.student is null");
        query.where("planBed.bed.student is null");
        return query;
    }

    @Deprecated
    private ClassAccomPlan getClassAcconPlan(Student student) {
        if (student.getAdminClass() == null) {
            return null;
        }
        OqlBuilder query = OqlBuilder.from(ClassAccomPlan.class);
        query.where("adminClass = :adminClass", student.getAdminClass());
        query.where("gender = :gender", student.getGender().getName());
        List<ClassAccomPlan> classAccomPlans = entityDao.search(query);
        return classAccomPlans.isEmpty() ? null : classAccomPlans.get(0);
    }

    @Deprecated
    private MajorAccomPlan getMajorAccomPlan(Student student) {
        if (student.getMajor() == null) {
            return null;
        }
        OqlBuilder query = OqlBuilder.from(MajorAccomPlan.class);
        query.where("major = :major", student.getMajor());
        query.where("gender = :gender", student.getGender().getName());
        List<MajorAccomPlan> majorAccomPlans = entityDao.search(query);
        return majorAccomPlans.isEmpty() ? null : majorAccomPlans.get(0);
    }

    @Deprecated
    private DeptAccomPlan getDeptAccomPlan(Student student) {
        if (student.getDepartment() == null) {
            return null;
        }
        OqlBuilder query = OqlBuilder.from(DeptAccomPlan.class);
        query.where("department = :department", student.getDepartment());
        query.where("gender = :gender", student.getGender().getName());
        List<DeptAccomPlan> deptAccomPlans = entityDao.search(query);
        return deptAccomPlans.isEmpty() ? null : deptAccomPlans.get(0);
    }


    @Override
    public List<DormZone> findZone(Long studentId) {
        OqlBuilder query = getQuery(studentId);
        query.select("distinct planBed.bed.room.building.zone.id, planBed.bed.room.building.zone.name");
        query.orderBy("planBed.bed.room.building.zone.name");
        List<Object[]> datas = entityDao.search(query);
        List<DormZone> dormZones = new ArrayList<DormZone>();
        for (Object[] oo : datas) {
            DormZone dormZone = new DormZone();
            dormZone.setId((Long) oo[0]);
            dormZone.setName((String) oo[1]);
            dormZones.add(dormZone);
        }
        return dormZones;
    }

    @Override
    public List<DormBuilding> findBuilding(Long zoneId, Long studentId) {
        OqlBuilder query = getQuery(studentId);
        query.where("planBed.bed.room.building.zone.id = :zoneId", zoneId);
        query.select("distinct planBed.bed.room.building");
        List<DormBuilding> dormBuildings = entityDao.search(query);
        Collections.sort(dormBuildings, new PropertyComparator<DormBuilding>("name"));
        return dormBuildings;
    }

    @Override
    public List<DormRoom> findRoom(Long buildingId, Long studentId) {
        OqlBuilder query = getQuery(studentId);
        query.where("planBed.bed.room.building.id = :buildingId", buildingId);
        query.select("distinct planBed.bed.room");
        List<DormRoom> dormRooms = entityDao.search(query);
        Collections.sort(dormRooms, new PropertyComparator<DormRoom>("name"));
        return dormRooms;
    }

    @Override
    public List<DormBed> findBed(Long roomId, Long studentId) {
        OqlBuilder query = getQuery(studentId);
        query.where("planBed.bed.room.id = :roomId", roomId);
        query.select("distinct planBed");
        List<DormPlanBed> dormPlanBeds = entityDao.search(query);
        List<DormBed> dormBeds = new ArrayList<DormBed>();
        for (DormPlanBed dormPlanBed : dormPlanBeds) {
            DormBed dormBed = new DormBed();
            dormBed.setRoom(dormPlanBed.getBed().getRoom());
            dormBed.setId(dormPlanBed.getId());
            dormBed.setName(dormPlanBed.getBed().getName());
            dormBeds.add(dormBed);
        }
        Collections.sort(dormBeds, new PropertyComparator<DormBed>("name"));
        return dormBeds;
    }

}
