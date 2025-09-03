package com.yzsoft.yxxt.core.service;

import com.yzsoft.yxxt.core.model.SystemConfig;
import org.beangle.ems.security.User;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface YxxtService {

    Integer getYear();

    String getGrade();

    void saveYear(Integer systemYear);

    /**
     * 当学生没有关联用户时，根据学号查询用户，并关联
     *
     * @param student
     * @return
     */
    User getUser(Student student);

	SystemConfig getSystemConfig();
    String createXh(Student student);

    Student getStudent();
    Student getStudent(Long userId, String username);

    List<Student> findStudentByCity(Student student);
}
