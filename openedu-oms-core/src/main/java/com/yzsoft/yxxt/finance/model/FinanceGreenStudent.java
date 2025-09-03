package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FinanceGreenStudent extends LongIdObject {

	private ProcessBatch batch;
	private Student student;
	private Double money;
	@Column(length = 300)
	private String remark;
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FinanceGreenStudentItem> items = new ArrayList<FinanceGreenStudentItem>();

	public ProcessBatch getBatch() {
		return batch;
	}

	public void setBatch(ProcessBatch batch) {
		this.batch = batch;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<FinanceGreenStudentItem> getItems() {
		return items;
	}

	public void setItems(List<FinanceGreenStudentItem> items) {
		this.items = items;
	}
}
