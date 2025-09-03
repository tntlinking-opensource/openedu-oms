package com.yzsoft.yxxt.prepare.action;

import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.GradeService;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.helper.QueryHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 招办学生管理
 * 招办权限修改：可以改院系、专业备注字段
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentAdminManagerAction extends SecurityActionSupport {

	@Autowired
	private MajorService majorService;
	@Autowired
	private DepartmentService departmentService;
	@Resource
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
		put("departments", departmentService.findTeachingDepartment());
		put("majors", majorService.findAllMajor());
		put("genders", studentService.findGender());
		put("grades", gradeService.findAllGrade());
	}

	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
		populateConditions(builder);
		QueryHelper.populateIds(builder, getShortName() + ".id");
		QueryHelper.populateAdParams(builder, get("adSearchParams"), getShortName(),"long");
		QueryHelper.populateAdParams(builder, get("adSearchBooleanParams"), getShortName(),"boolean");
		builder.orderBy(getOrderString()).limit(getPageLimit());
		return builder;
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		put("departments", departmentService.findTeachingDepartment());
		put("majors", majorService.findAllMajor());
	}
}
