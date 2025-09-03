package com.yzsoft.yxxt.finance.service;

import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import org.beangle.product.core.model.Student;

public interface FinanceStudentService {

	/**
	 * 生成当前学年所有学生的缴费名单
	 */
	void create();

	/**
	 * 根据学生查询收费模板
	 * 优先选择相关专业的收费模板
	 * 其次选择专业类别相同且学历层次相同的模板
	 * 最后选择学历层次相同的模板
	 *
	 * @param student
	 * @return
	 */
	FinanceTemplate findTempalte(Student student);

	/**
	 * 查询学生收费状态
	 *
	 * @param student
	 * @return
	 */
	FinanceStudent getFinanceStudent(Student student);

	FinanceStudent getOrCreateFinanceStudent(Student student);

	/**
	 * 更新学生缴费项
	 *
	 * @param student
	 * @param code
	 * @param enabled
	 */
	void updateFinanceStudent(Student student, String code, boolean enabled);

	/**
	 * 计算学生缴费项
	 *
	 * @param financeStudent
	 */
	void count(FinanceStudent financeStudent);

	/**
	 * 更新学生缴费项
	 *
	 * @param financeStudent
	 * @param item
	 * @param money
	 * @param enabled
	 */
	void updateFinanceStudentItem(FinanceStudent financeStudent, FinanceItem item, Double money, boolean enabled);
}
