package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.model.AskStudent;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.TeacherService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class TeacherAction extends SecurityActionSupport {

    @Resource
    private TeacherService teacherService;

    @Override
    protected String getEntityName() {
        return AskStudent.class.getName();
    }

    @Override
    protected OqlBuilder<?> getOqlBuilder() {
        Teacher teacher = teacherService.getTeacherByCode(getUsername());
        OqlBuilder query = super.getOqlBuilder();
        if (teacher != null) {
            query.where("department = :department", teacher.getDepartment());
        }
        return query;
    }

    @Override
    protected String getDefaultOrderString() {
        return "createTime desc";
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        AskStudent askStudent = (AskStudent) entity;
        askStudent.setTeahcer(getCurrentUser());
        askStudent.setReplyTime(new Date());
        askStudent.setStatus(AskStudent.STATUS_REPLYED);
        return super.saveAndForward(entity);
    }
}
