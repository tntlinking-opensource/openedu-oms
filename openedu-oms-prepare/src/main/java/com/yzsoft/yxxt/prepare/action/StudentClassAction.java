package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.importer.StudentClassImportListener;
import com.yzsoft.yxxt.prepare.model.*;
import com.yzsoft.yxxt.prepare.service.AdminClassPlanService;
import org.beangle.commons.exception.MyException;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentClassAction extends SecurityActionSupport {

	@Resource
	private YxxtService yxxtService;
	@Resource
	private AdminClassPlanService adminClassPlanService;
	@Resource
	private TeacherService teacherService;
	@Resource
	private StudentService studentService;
	@Resource
	private MajorService majorService;

	@Override
	protected String getEntityName() {
		return StudentClass.class.getName();
	}

	@Override
	public String index() {
		put("grade", yxxtService.getGrade());
		put("educations", studentService.findEducation());
		Teacher teacher = teacherService.getTeacherByCode(getUsername());
		if (teacher != null) {
			put("majors", majorService.findMajorByDepartmentId(teacher.getDepartment().getId()));
		}
		return super.index();
	}

	@Override
	public String search() {
		return super.search();
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder query = (OqlBuilder) super.getQueryBuilder();
//        query.select("new " + getEntityName() + "(studentClass, student)");
//        query.join("right", "studentClass.student", "student");
		Teacher teacher = teacherService.getTeacherByCode(getUsername());
		if (teacher != null) {
			query.where("studentClass.student.department = :department", teacher.getDepartment());
		}
//        populateConditions(query);
		put("teacher", teacher);
		return query;
	}

	@Override
	protected String getDefaultOrderString() {
		return "student.code";
	}

	@Override
	public String edit() {
		Long studentId = getEntityId(getShortName());
		OqlBuilder query = OqlBuilder.from(getEntityName());
		query.where("student.id = :studentid", studentId);
		List<StudentClass> studentClasses = entityDao.search(query);
		StudentClass entity = studentClasses.isEmpty() ? null : studentClasses.get(0);
		if (entity == null) {
			entity = new StudentClass();
			entity.setEnroll(entityDao.get(StudentEnroll.class, studentId));
		}
		put(getShortName(), entity);
		editSetting(entity);
		return forward();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		StudentClass studentClass = (StudentClass) entity;
		OqlBuilder query = OqlBuilder.from(AdminClassTemp.class);
		query.where("major = :major", studentClass.getStudent().getMajor());
		query.orderBy("code");
		List<AdminClassTemp> adminClassTemps = entityDao.search(query);
		adminClassTemps.remove(studentClass.getAdminClass());
		put("adminClasss", entityDao.search(query));
	}

	public String alloc() {
		try {
			Teacher teacher = teacherService.getTeacherByCode(getUsername());
			Assert.notNull(teacher, "当前用户不是老师，不能进行分班操作。");
			Assert.notNull(teacher.getDepartment(), "当前用户所在部门为空，不能进行分班操作。");
			List<AdminClassRule> rules = adminClassPlanService.findRule();
			put("rules", rules);
			Long educationId = getLong("studentClass.student.education.id");
			put("studentNums", adminClassPlanService.countStudent(teacher.getDepartment(), educationId));
		} catch (IllegalArgumentException e) {
			put("errormsg", e.getMessage());
			return forward("/template/yxxt/error");
		}
		return forward();
	}

	public String allocSave() throws Exception {
		List<AdminClassPlan> adminClassPlen = populateList(AdminClassPlan.class, "plan");
		try {
			String[] types = new String[]{"CODE", "NAME", "GENDER", "SCORE"};
			List<AdminClassRule> rules = getRules(types);
			adminClassPlanService.alloc(adminClassPlen, rules);
			return redirect("search", "info.save.success");
		} catch (MyException e) {
			alloc();
			put("adminClassPlen", adminClassPlen);
			addActionError(e.getMessage());
			return forward("alloc");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}
	}

	public String generateCode() {
		List<AdminClassRule> rules = adminClassPlanService.findRule();
		put("rules", rules);
		return forward();
	}

	public String generateCodeSave() {
		try {
			Teacher teacher = teacherService.getTeacherByCode(getUsername());
			AdminClassRule rule = entityDao.get(AdminClassRule.class, getLong("rule.STUDENT_CODE"));
			adminClassPlanService.generateCode(teacher.getDepartment(), rule);
			return redirect("search", "info.save.success");
		} catch (MyException e) {
			generateCode();
			addActionError(e.getMessage());
			return forward("alloc");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}
	}

	private List<AdminClassRule> getRules(String[] types) {
		List<Long> ruleIds = new ArrayList<Long>();
		for (String type : types) {
			ruleIds.add(getLong("rule." + type));
		}
		List<AdminClassRule> rules = entityDao.get(AdminClassRule.class, ruleIds);
		return rules;
	}

	@Override
	public ItemImporterListener getImporterListener() {
		return new StudentClassImportListener();
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("学号", "student.code");
		map.put("姓名", "student.name");
		map.put("院系代码", "student.department.code");
		map.put("院系名称", "student.department.name");
		map.put("专业代码", "student.major.code");
		map.put("专业名称", "student.major.name");
		map.put("班级代码", "adminClass.code");
		map.put("班级名称", "adminClass.name");
		return map;
	}

	public String publish() {
		try {
			Teacher teacher = teacherService.getTeacherByCode(getUsername());
			Assert.notNull(teacher, "当前用户不是老师，不能进行分班发布操作。");
			Assert.notNull(teacher.getDepartment(), "当前用户所在部门为空，不能进行分班发布操作。");
			adminClassPlanService.publish(teacher.getDepartment());
			return redirect("search", "info.save.success");
		} catch (IllegalArgumentException e) {
			put("errormsg", e.getMessage());
			return forward("/template/yxxt/error");
		}
	}
}
