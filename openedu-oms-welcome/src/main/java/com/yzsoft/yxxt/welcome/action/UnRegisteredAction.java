package com.yzsoft.yxxt.welcome.action;

import java.util.List;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.welcome.model.WelcomeStudent;
import com.yzsoft.yxxt.welcome.model.WelcomeStudentView;
import com.yzsoft.yxxt.welcome.service.WelcomeService;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.service.ProcessLinkLogService;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.TeacherService;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class UnRegisteredAction extends BaseCommonAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private WelcomeService welcomeService;
    @Resource
	protected RestrictionHelper restrictionHelper;
    @Resource
	protected TeacherService teacherService;
    @Resource
    protected ProcessLinkLogService processLinkLogService;

    @Override
    protected String getEntityName() {
        return WelcomeStudentView.class.getName();
    }

    @Override
    public String index() {
        String grade = yxxtService.getGrade();
        put("year", grade);
        put("years", welcomeService.findYear());
        put("departments",getDeparts());
        return super.index();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("reasons", dictDataService.findDictData("UNREGISTER_REASON"));
    }

    @Override
    protected QueryBuilder<?> getQueryBuilder() {
        OqlBuilder query = (OqlBuilder) super.getQueryBuilder();
//        query.select("welcomeStudent, student");
//        query.select("new " + WelcomeStudent.class.getName() + "(welcomeStudent, student)");
//        query.join("right", "welcomeStudent.student", "student");
        if(getDeparts().size() == 1){
			query.where("student.department in (:depts)",getDeparts());
		}
        Boolean isdone = getBoolean("isdone");
        if (isdone != null) {
            if (isdone) {
                query.where("reason is not null");
            } else {
                query.where("reason is null");
                query.where("registered is null or registered = false");
            }
        }
        return query;
    }
    
    @SuppressWarnings("unchecked")
	protected List<Department> getDeparts() {
		List<Department> departments = restrictionHelper.getDeparts();
		if(departments.isEmpty()) {
			Teacher teacher = teacherService.getTeacherByCode(getUsername());
			if(null!=teacher&&null!=teacher.getDepartment()) {
				departments.add(teacher.getDepartment());
			}
		}
		return departments;
	}

    @Override
    protected String getDefaultOrderString() {
        return "student.code";
    }

    @Override
    public String edit() {
        try {
            Long id = getLong("welcomeStudentView.id");
            WelcomeStudent welcomeStudent = entityDao.getEntity(WelcomeStudent.class, "student.id", id);
            if (welcomeStudent == null) {
                Student student = entityDao.get(Student.class, id);
                welcomeStudent = new WelcomeStudent();
                welcomeStudent.setStudent(student);
            }
            indexSetting();
            put("welcomeStudent", welcomeStudent);
        } catch (Exception e) {
            redirect("search");
        }
        return forward();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        WelcomeStudent welcomeStudent = (WelcomeStudent) populateEntity(WelcomeStudent.class, "welcomeStudent");
        if(welcomeStudent.getBatch() == null){
            welcomeStudent.setBatch(welcomeService.getBatch(welcomeStudent.getStudent().getId()));
        }
        welcomeStudent.setRegistered(false);
        return super.saveAndForward(welcomeStudent);
    }

    public String registered() {
        Long[] studentIds = getEntityIds(getShortName());
        Boolean registered = getBoolean("registered");
        if(registered){
            for (Long studentId : studentIds) {//确认办理
                processLinkLogService.saveLog(84l, 289l, studentId);
                welcomeService.register(studentId, registered);
            }
        }else {
            for (Long studentId : studentIds) {//确认办理
                processLinkLogService.cancleLog(84l, 289l, studentId);
                welcomeService.register(studentId, registered);
            }
        }


        return redirect("search", "info.save.success");
    }

    @Override
    protected QueryBuilder<?> getExportQueryBuilder() {
        OqlBuilder query = (OqlBuilder) super.getExportQueryBuilder();
//        query.where("welcomeStudent.reason is not null");
        return query;
    }
}
