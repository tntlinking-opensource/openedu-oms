package com.yzsoft.yxxt.prepare.service;

import com.yzsoft.yxxt.prepare.model.MajorPlan;
import com.yzsoft.yxxt.prepare.model.StudentMajorConfig;
import org.beangle.product.core.model.Major;

import java.util.List;

public interface MajorPlanService {

    void allocStudentMajor(List<MajorPlan> majorPlans);
    void allocStudentMajor(String grade, List<MajorPlan> majorPlans);

    void init();

    void init(String grade, Long educationId);

    Long countStudent();
    Long countStudent(String grade, Long educationId);

    Long countStudentAlloced(String grade, Long educationId);

    List<Object[]> countStudentByMajor();
    List<Object[]> countStudentByMajor(String grade, Long educationId);

    List<Long[]> countStudentByMajor1();
    List<Long[]> countStudentByMajor1(String grade, Long educationId);

    List<Major> findMajor();

    List<Major> findMajor(Long educationId);

    void publish();

    void publishCancle();

    void allocMajor1();

    void allocClean();

    void clean(String grade, Long educationId);

    Integer getNum();

    StudentMajorConfig getConfig();

    //将所有没专业的学生分配到学生自选专业
    void wish();
}
