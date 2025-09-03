package com.yzsoft.yxxt.prepare.service;

import com.yzsoft.yxxt.prepare.model.AdminClassPlan;
import com.yzsoft.yxxt.prepare.model.AdminClassRule;
import org.beangle.product.core.model.Department;

import java.util.List;

public interface AdminClassPlanService {
	/**
	 * 按专业、专业方向统计学生人数
	 * Object[]：major.id, major.name, direction.id, direction.name, studentNum
	 * @return
	 * @param department
	 * @param educationId
	 */
	List<Object[]> countStudent(Department department, Long educationId);

	void alloc(List<AdminClassPlan> adminClassPlen, List<AdminClassRule> rule);

	List<AdminClassRule> findRule();

	void generateCode(Department department, AdminClassRule rule);

	void publish(Department department);

    void init();
    void init(String grade, Long educationId, Department department);
}
