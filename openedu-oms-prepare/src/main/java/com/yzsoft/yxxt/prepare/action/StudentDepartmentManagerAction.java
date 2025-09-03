package com.yzsoft.yxxt.prepare.action;

import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.*;
import org.beangle.struts2.helper.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 院系学生管理
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentDepartmentManagerAction extends SecurityActionSupport {

	@Autowired
	private TeacherService teacherService;
	@Autowired
	private MajorService majorService;
	@Autowired
	private AdminClassService adminClassService;
	@Autowired
	private StudentService studentService;
	@Autowired
	protected GradeService gradeService;

	@Override
	protected String getEntityName() {
		return Student.class.getName();
	}


	@Override
	protected void indexSetting() {
		super.indexSetting();
		Teacher teacher = teacherService.getTeacherByCode(getUsername());
		put("majors", majorService.findMajorByDepartmentId(teacher.getDepartment().getId()));
		put("genders", studentService.findGender());
		put("grades", gradeService.findAllGrade());
	}

	@Override
	protected void querySetting(OqlBuilder query) {
		super.querySetting(query);
		Teacher teacher = teacherService.getTeacherByCode(getUsername());
		QueryHelper.populateAdParams(query, get("adSearchParams"), getShortName(),"long");
		QueryHelper.populateAdParams(query, get("adSearchBooleanParams"), getShortName(),"boolean");
		query.where("student.department = :department", teacher.getDepartment());
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		Student student = (Student) entity;
		if (student.getMajor() != null) {
			put("adminClasses", adminClassService.findAdminClassByGradeAndMajor(student.getGrade(), student.getMajor().getCode()));
		}
	}
}
