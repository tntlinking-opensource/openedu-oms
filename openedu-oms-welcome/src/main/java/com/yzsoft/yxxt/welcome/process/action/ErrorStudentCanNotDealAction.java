package com.yzsoft.yxxt.welcome.process.action;

import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorStudentCanNotDealAction extends BaseAction {

    public String index() {
        Long id = getLong("id");
        Student student = entityDao.get(Student.class, id);
        put("student", student);
        return forward();
    }

}
