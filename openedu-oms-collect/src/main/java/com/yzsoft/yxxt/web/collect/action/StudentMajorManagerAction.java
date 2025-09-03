package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.model.StudentMajorDetail;
import com.yzsoft.yxxt.prepare.service.MajorPlanService;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import com.yzsoft.yxxt.web.collect.service.MajorInfoService;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.struts2.helper.QueryHelper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentMajorManagerAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;
	@Resource
	private MajorService majorService;
	@Resource
	private MajorInfoService majorInfoService;
	@Resource
	private MajorPlanService majorPlanService;
	@Resource
	protected StudentService studentService;

	@Override
	protected String getEntityName() {
		return StudentMajor.class.getName();
	}

	@Override
	public String index() {
		put("year", yxxtService.getYear());
		put("enrollTypes", studentService.findEnrollType());
		return super.index();
	}

	@Override
	public String search() {
		put("majorNum", majorPlanService.getNum());
		return super.search();
	}

	@Override
	protected void querySetting(OqlBuilder query) {
		super.querySetting(query);
		Boolean isCollect = getBoolean("isCollect");
		if (isCollect != null) {
			if (isCollect) {
				query.where("size(studentMajor.details) > 0");
			} else {
				query.where("size(studentMajor.details) = 0");
			}
		}
		QueryHelper.populateAdParams(query, get("adSearchParams"), getShortName(),"long");
		QueryHelper.populateAdParams(query, get("adSearchBooleanParams"), getShortName(),"boolean");
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		StudentMajor studentMajor = (StudentMajor) entity;
		Student student = studentMajor.getStudent();
		put("studentMajorNum", majorPlanService.getNum());
		if (student != null) {
			put("majorInfos", majorInfoService.find(student));
		}
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		StudentMajor studentMajor = (StudentMajor) entity;
		if (studentMajor.getStudent() == null) {
			String studentCode = get("studentCode");
//            if (entityDao.exist(StudentMajor.class, "student.code", studentCode)) {
//                throw new IllegalArgumentException("学号“" + studentCode + "”的志愿已存在，不能重复添加");
//            }
			StudentMajor existStudentMajor = entityDao.getEntity(StudentMajor.class, "student.code", studentCode);
			if (existStudentMajor != null) {
				entityDao.remove(existStudentMajor);
			}
			Student student = entityDao.getEntity(Student.class, "code", studentCode);
			Assert.notNull(student, "学号“" + studentCode + "”不存在");
			studentMajor.setStudent(student);
			studentMajor.setGrade(student.getGrade());
//			studentMajor.setUser(student.getUser());
		}
		List<StudentMajorDetail> details = populateList(StudentMajorDetail.class, "detail");
		for (StudentMajorDetail detail : details) {
			detail.setStudentMajor(studentMajor);
		}
		studentMajor.getDetails().clear();
		studentMajor.getDetails().addAll(details);
		return super.saveAndForward(entity);
	}

	public String count() {
		Integer year = getInteger("year");
		if (year == null) {
			year = yxxtService.getYear();
		}
		List<Major> majors = majorService.findAllMajor();
		Map<String, List<Long[]>> datas = new HashMap<String, List<Long[]>>();
		Integer studentMajorNum = majorPlanService.getNum();
		for (int i = 1; i <= studentMajorNum; i++) {
			datas.put("major" + i + "Count", countStudentByMajor(year, i));
		}
		put("majors", majors);
		put("datas", datas);
		put("majorNum", majorPlanService.getNum());
		return forward();
	}

	private List<Long[]> countStudentByMajor(Integer year, Integer sort) {
		OqlBuilder query = OqlBuilder.from(StudentMajor.class, "studentMajor");
		query.select("detail.major.id, count(*)");
		query.join("studentMajor.details", "detail");
		query.where("detail.sort = :sort", sort);
		query.where("detail.major.id is not null");
		query.where("studentMajor.grade = :grade", year.toString());
		query.groupBy("detail.major.id");
		return entityDao.search(query);
	}

	public String students() {
		Long majorId = getLong("majorId");
		Integer sort = getInteger("sort");
		OqlBuilder query = OqlBuilder.from(StudentMajor.class);
		query.join("studentMajor.details", "detail");
		query.where("detail.sort = :sort", sort);
		query.where("detail.major.id = :major_id", majorId);
		List<StudentMajor> studentMajors = entityDao.search(query);
		put("studentMajors", studentMajors);
		return forward();
	}

	public String print() {
		count();
		return forward();
	}

	public void exportExcel() {
		count();

		HttpServletRequest request = ServletActionContext.getRequest();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		int rowNum = 0;

//        sheet.createRow(rowNum++).createCell(0).setCellValue("学生志愿统计");

		String[] nums = new String[]{"一", "二", "三", "四", "五", "六"};
		Map<String, Object> datas = (Map<String, Object>) request.getAttribute("datas");

		HSSFRow titleRow = sheet.createRow(rowNum++);
		titleRow.createCell(0).setCellValue("专业名称");

		Integer majorNum = (Integer) request.getAttribute("majorNum");
		for (int i = 0; i < majorNum; i++) {
			titleRow.createCell(i + 1).setCellValue("第" + nums[i] + "志愿人数");
		}

		List<Major> majors = (List<Major>) request.getAttribute("majors");
		for (Major major : majors) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(major.getName());
			for (int i = 1; i <= majorNum; i++) {
				Long num = studentNum(major, datas.get("major" + i + "Count"));
				row.createCell(i).setCellValue(num);
			}
		}
		CountUtil.output(wb, "export.xls");
	}

	private Long studentNum(Major major, Object data) {
		List<Object[]> count = (List<Object[]>) data;
		for (Object[] oo : count) {
			if (oo[0].equals(major.getId())) {
				return (Long) oo[1];
			}
		}
		return 0L;
	}

}
