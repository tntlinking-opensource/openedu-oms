package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.model.*;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FinanceStudentServiceImpl extends EntityDaoSupport implements FinanceStudentService {

	@Resource
	private YxxtService yxxtService;

	@Override
	public void create() {
		OqlBuilder query = OqlBuilder.from(FinanceStudent.class, "fs");
		query.join("right", "fs.student", "s");
		query.where("fs.id is null");
		query.where("s.grade = :grade", yxxtService.getGrade());
		query.select("s");
		int i = 0;
		while (true) {
			i++;
			query.limit(i, 100);
			List<Student> students = entityDao.search(query);
			if (students.isEmpty()) {
				break;
			}
			for (Student student : students) {
				getOrCreateFinanceStudent(student);
			}
		}
	}

	@Override
	public FinanceTemplate findTempalte(Student student) {
		FinanceTemplate financeTemplate = findTempalteByMajor(student);
		if (financeTemplate == null) {
			financeTemplate = findTempalteByMajorType(student);
		}
		if (financeTemplate == null) {
			financeTemplate = findTempalteByEducation(student);
		}
		return financeTemplate;
	}

	private FinanceTemplate findTempalteByMajor(Student student) {
		if (student.getMajor() == null) {
			return null;
		}
		OqlBuilder query = OqlBuilder.from(FinanceTemplate.class, "template");
		query.join("template.majors", "major");
		query.where("year  = :year", yxxtService.getYear());
		query.where("major  = :major", student.getMajor());
		query.where("template.education  = :education", student.getEducation());
		query.where("template.enabled = true");
		query.cacheable();
		List<FinanceTemplate> financeTemplates = entityDao.search(query);
		FinanceTemplate financeTemplate = financeTemplates.isEmpty() ? null : financeTemplates.get(0);
//		if (financeTemplate != null && financeTemplate.getEducation() != null) {
//			if (!financeTemplate.getEducation().equals(student.getEducation())) {
//				return null;
//			}
//		}
		return financeTemplate;
	}

	private FinanceTemplate findTempalteByMajorType(Student student) {
		if (student.getMajor() == null || student.getMajor().getType() == null) {
			return null;
		}
		OqlBuilder query = OqlBuilder.from(FinanceTemplate.class);
		query.where("year  = :year", yxxtService.getYear());
		query.where("education  = :education", student.getEducation());
//		query.where("majorType  = :majorType", student.getMajor().getType());
		query.where("enabled = true");
		query.cacheable();
		List<FinanceTemplate> financeTemplates = entityDao.search(query);
		return financeTemplates.isEmpty() ? null : financeTemplates.get(0);
	}

	private FinanceTemplate findTempalteByEducation(Student student) {
		OqlBuilder query = OqlBuilder.from(FinanceTemplate.class);
		query.where("year  = :year", yxxtService.getYear());
		query.where("education  = :education", student.getEducation());
		query.where("majorType is null");
		query.where("limitMajor = false");
		query.where("enabled = true");
		query.cacheable();
		List<FinanceTemplate> financeTemplates = entityDao.search(query);
		return financeTemplates.isEmpty() ? null : financeTemplates.get(0);
	}

	@Override
	public FinanceStudent getFinanceStudent(Student student) {
		OqlBuilder query = OqlBuilder.from(FinanceStudent.class);
		query.where("student  = :student", student);
		List<FinanceStudent> financeStatuses = entityDao.search(query);
		return financeStatuses.isEmpty() ? null : financeStatuses.get(0);
	}

	@Override
	public FinanceStudent getOrCreateFinanceStudent(Student student) {
		if (student.getMajor() == null) {
			student = entityDao.get(Student.class, student.getId());
		}
		FinanceStudent financeStudent = getFinanceStudent(student);
		if (financeStudent == null) {
			FinanceTemplate financeTemplate = findTempalte(student);
			if (financeTemplate == null) {
				return null;
			}
			financeStudent = new FinanceStudent();
			financeStudent.setYear(yxxtService.getYear());
			financeStudent.setStudent(student);
			for (FinanceTemplateItem item : financeTemplate.getItems()) {
				FinanceStudentItem sitem = new FinanceStudentItem();
				sitem.setFinanceStudent(financeStudent);
				sitem.setItem(item.getItem());
				sitem.setFeeTotal(item.getMoney());
				financeStudent.getItems().add(sitem);
			}
			financeStudent.countTotal();
			financeStudent.countFeeOdd();
			entityDao.saveOrUpdate(financeStudent);
		}
		return financeStudent;
	}

	@Override
	public void updateFinanceStudent(Student student, String code, boolean enabled) {
		FinanceStudent financeStudent = getOrCreateFinanceStudent(student);
		FinanceStudentItem item = getFinanceStudentItem(financeStudent, code);
		if (enabled) {
			FinanceTemplateItem fitem = getFinanceTemplateItem(student, code);
			Assert.notNull(fitem, "收费模板设置有误");
			if (item == null) {
				item = new FinanceStudentItem();
				item.setFinanceStudent(financeStudent);
				item.setItem(fitem.getItem());
				financeStudent.getItems().add(item);
			}
			item.setFeeTotal(fitem.getMoney());
			entityDao.saveOrUpdate(financeStudent);
		} else {
			if (item != null) {
				financeStudent.getItems().remove(item);
				entityDao.saveOrUpdate(financeStudent);
			}
		}
	}

	@Override
	public void count(FinanceStudent financeStudent) {
		Double feeTotal = 0D;
		Double feePaid = 0D;
		for (FinanceStudentItem item : financeStudent.getItems()) {
			feeTotal += item.getFeeTotal();
			feePaid += item.getFeePaid();
			item.setFeeOdd(item.getFeeTotal() - item.getFeePaid());
		}
		financeStudent.setFeeTotal(feeTotal);
		financeStudent.setFeePaid(feePaid);
		financeStudent.setFeeOdd(feeTotal - feePaid - financeStudent.getGreenMoney());
	}

	@Override
	public void updateFinanceStudentItem(FinanceStudent financeStudent, FinanceItem item, Double money, boolean enabled) {
		FinanceStudentItem sitem = getFinanceStudentItem(financeStudent, item.getCode());
		if (enabled) {
			if (sitem == null) {
				sitem = new FinanceStudentItem();
				sitem.setFinanceStudent(financeStudent);
				sitem.setItem(item);
				financeStudent.getItems().add(sitem);
			}
			sitem.setFeeTotal(money);
		} else {
			if (sitem != null) {
				financeStudent.getItems().remove(sitem);
			}
		}
	}

	private FinanceTemplateItem getFinanceTemplateItem(Student student, String code) {
		FinanceTemplate financeTemplate = findTempalte(student);
		for (FinanceTemplateItem item : financeTemplate.getItems()) {
			if (item.getItem().getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}

	private FinanceStudentItem getFinanceStudentItem(FinanceStudent financeStudent, String code) {
		for (FinanceStudentItem item : financeStudent.getItems()) {
			if (item.getItem().getCode().equals(code)) {
				return item;
			}
		}
		return null;
	}
}
