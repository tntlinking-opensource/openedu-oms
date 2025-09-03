package com.yzsoft.yxxt.prepare.service;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.model.PrepareConfig;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface PrepareService {

    String getCode();

    List<String> findGrade();

    void createStudent();

    void createStudent(StudentEnroll student);

    void removeStudent(StudentEnroll student);

    StudentEnroll getStudentEnroll(String username);
    StudentEnroll getStudentEnroll(Student student);

	PrepareConfig getConfig();

	List<DictData> findPasswordType();
}
