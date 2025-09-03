package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "YXF_FINANCE_GREEN_SITEMS")
public class FinanceGreenStudentItem extends LongIdObject {

	private FinanceGreenStudent student;
	private FinanceGreen green;
	private Double money;

	public FinanceGreenStudent getStudent() {
		return student;
	}

	public void setStudent(FinanceGreenStudent student) {
		this.student = student;
	}

	public FinanceGreen getGreen() {
		return green;
	}

	public void setGreen(FinanceGreen green) {
		this.green = green;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}
