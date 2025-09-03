package com.yzsoft.yxxt.welcome.process.service;

import com.yzsoft.dorm.model.*;
import org.beangle.product.core.model.Student;

import java.util.List;

public interface DormAllocService {


    void chooseBed(Student student, Long planBedId);

    void cancleCooseBed(Student student);

    enum PlanType {
        DEPT, MAJOR, CLASS
    }

    PlanType getPlanType();

    DormPlanBed getDormPlanBed(Long studentId);

    DormStudent getDormStudent(Long studentId);

    List<DormZone> findZone(Long studentId);

    List<DormBuilding> findBuilding(Long zoneId, Long studentId);

    List<DormRoom> findRoom(Long buildingId, Long studentId);

    List<DormBed> findBed(Long roomId, Long studentId);

}
