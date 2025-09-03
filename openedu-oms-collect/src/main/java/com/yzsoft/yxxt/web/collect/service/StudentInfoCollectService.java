package com.yzsoft.yxxt.web.collect.service;

import com.yzsoft.yxxt.web.collect.model.StudentInfoCollectState;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentFamily;

import java.util.List;

public interface StudentInfoCollectService {

    String CODE_FAMILY = "FAMILY";
    String CODE_EDUCATION = "EDUCATION";

    boolean finished(Student student);

    void saveState(Student student, String code);

    void initFamily(StudentFamily family);

    List<StudentInfoCollectState> findState(Student student);
}
