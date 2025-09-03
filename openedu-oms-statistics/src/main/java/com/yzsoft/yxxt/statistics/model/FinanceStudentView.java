package com.yzsoft.yxxt.statistics.model;

import com.yzsoft.yxxt.finance.model.FinanceStudent;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * create or replace view V_YXF_FINANCE_STUDENTS as
 * select s.id, s.id Student_id, fs.id finance_Student_id
 * from cp_students s
 * left join yxf_finance_students fs
 * on s.id = fs.student_id
 */
@Entity
@Table(name = "V_YXF_FINANCE_STUDENTS")
public class FinanceStudentView extends LongIdObject {
	private Student student;
	private FinanceStudent financeStudent;

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public FinanceStudent getFinanceStudent() {
		return financeStudent;
	}

	public void setFinanceStudent(FinanceStudent financeStudent) {
		this.financeStudent = financeStudent;
	}
}
