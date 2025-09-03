package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.importer.StudentMajorImportListener;
import com.yzsoft.yxxt.prepare.model.MajorPlan;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.service.MajorPlanService;
import org.beangle.commons.exception.MyException;
import org.beangle.model.Entity;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentMajorAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;
	@Resource
	private StudentService studentService;
	@Resource
	private MajorPlanService majorPlanService;


	@Override
	protected String getEntityName() {
		return StudentMajor.class.getName();
	}

	@Override
	public String index() {
		put("grade", yxxtService.getGrade());
		put("educations", studentService.findEducation());
		return super.index();
	}

	@Override
	public String search() {
		String grade = get("studentMajor.student.grade");
		if (grade == null) {
			grade = yxxtService.getGrade();
		}
		Long educationId = getLong("studentMajor.student.education.id");
		majorPlanService.init(grade, educationId);
		put("studentAlloced", majorPlanService.countStudentAlloced(grade, educationId));
		put("studentTotal", majorPlanService.countStudent(grade, educationId));
		put("grade", grade);
		put("education", entityDao.get(DictData.class, educationId));
		put("majorNum", majorPlanService.getNum());
		return super.search();
	}

	@Override
	protected String getDefaultOrderString() {
		return "id";
	}

	@Override
	public String edit() {
		return super.edit();
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		StudentMajor studentMajor = (StudentMajor) entity;
		studentMajor.setWishOrder(StudentMajor.WishOrder.WISH_ORDER_10);
		return super.saveAndForward(entity);
	}

	public String alloc() {
		String grade = get("studentMajor.student.grade");
		if (grade == null) {
			grade = yxxtService.getGrade();
		}
		Long educationId = getLong("studentMajor.student.education.id");
		put("majors", majorPlanService.findMajor(educationId));
		put("majorPlans", entityDao.getAll(MajorPlan.class));
		put("majorCount", majorPlanService.countStudentByMajor(grade, educationId));
		put("major1Count", majorPlanService.countStudentByMajor1(grade, educationId));
		put("studentTotal", majorPlanService.countStudent(grade, educationId));
		put("grade", grade);
		put("education", entityDao.get(DictData.class, educationId));
		put("educationId", educationId);
		return forward();
	}

	public String allocSave() throws Exception {
		String grade = get("grade");
		List<MajorPlan> majorPlans = populateList(MajorPlan.class, "majorPlan");
		try {
			majorPlanService.allocStudentMajor(grade, majorPlans);
			entityDao.remove(entityDao.getAll(MajorPlan.class));
			entityDao.saveOrUpdate(majorPlans);
			return redirect("search", "info.save.success");
		} catch (MyException e) {
			alloc();
			put("majorPlans", majorPlans);
			addActionError(e.getMessage());
			return forward("alloc");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("search", "info.save.failure");
		}
	}

	@Override
	public ItemImporterListener getImporterListener() {
		return new StudentMajorImportListener();
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("学号", "student.code");
		map.put("姓名", "student.name");
		map.put("院系代码", "major.department.code");
		map.put("院系名称", "major.department.name");
		map.put("专业代码", "major.code");
		map.put("专业名称", "major.name");
		return map;
	}

	public String clean() {
		String grade = get("grade");
		Long educationId = getLong("educationId");
		majorPlanService.clean(grade, educationId);
		return redirect("alloc", "清除成功");
	}

	public String publish() {
		majorPlanService.publish();
		return redirect("search", "发布成功");
	}

	public String publishMajor1() {
		majorPlanService.allocMajor1();
		return redirect("search", "发布成功");
	}
}
