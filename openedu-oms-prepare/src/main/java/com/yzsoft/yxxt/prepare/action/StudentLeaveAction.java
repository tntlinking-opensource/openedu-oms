package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.StudentLeave;
import com.yzsoft.yxxt.prepare.model.StudentLeaveView;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentLeaveAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private StudentService studentService;

    @Override
    protected String getEntityName() {
        return StudentLeave.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("grade", yxxtService.getGrade());
        put("genders", studentService.findGender());
    }

    @Override
    protected QueryBuilder<?> getQueryBuilder() {
        Integer year = yxxtService.getYear();
        OqlBuilder<?> query = OqlBuilder.from(StudentLeaveView.class, "studentLeave");
        populateConditions(query);
        query.orderBy(getOrderString()).limit(getPageLimit());
        Boolean isLeave = getBoolean("isLeave");
//        query.where("year = :year", year);
        if (isLeave != null) {
            if (isLeave) {
                query.where("leave_id is not null");
            } else {
                query.where("leave_id is null");
            }
        }
        return query;
    }

    @Override
    public String edit() {
        try {
            Long id = getLong("studentLeave.id");
            StudentLeave studentLeave = entityDao.getEntity(StudentLeave.class, "student.id", id);
            if (studentLeave == null) {
                Student student = entityDao.get(Student.class, id);
                studentLeave = new StudentLeave();
                studentLeave.setStudent(student);
            } else {
                if (StudentLeave.STATE_YXJ.equals(studentLeave.getState())) {
                    return redirect("search", "已销假的学生不能再请假");
                }
            }
            put("studentLeave", studentLeave);
        } catch (Exception e) {
            redirect("search");
        }
        return forward();
    }

    @Override
    public String info() {
        Long id = getLong("studentLeave.id");
        StudentLeave studentLeave = entityDao.getEntity(StudentLeave.class, "student.id", id);
        put("studentLeave", studentLeave);
        return forward();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        StudentLeave studentLeave = (StudentLeave) entity;
        if (studentLeave.getState() == null) {
            studentLeave.setState(StudentLeave.STATE_WXJ);
        }
        return super.saveAndForward(entity);
    }

    public String reportBack() {
        Long[] ids = getEntityIds(getShortName());
        List<StudentLeave> studentLeaves = entityDao.get(StudentLeave.class, "student.id", ids);
        for (StudentLeave studentLeave : studentLeaves) {
            studentLeave.setState(StudentLeave.STATE_YXJ);
        }
        entityDao.saveOrUpdate(studentLeaves);
        return redirect("search", "info.save.success");
    }

    @Override
    public String remove() throws Exception {
        Long[] ids = getEntityIds(getShortName());
        List<StudentLeave> studentLeaves = entityDao.get(StudentLeave.class, "student.id", ids);
        return removeAndForward(studentLeaves);
    }
}
