package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生缴费状态
 */
@Entity
public class FinanceStudent extends LongIdObject {
	//年份
	private Integer year;
	//学生
	private Student student;
	//应缴金额
	private Double feeTotal = 0D;
	//已缴金额
	private Double feePaid = 0D;
	//未缴金额
	private Double feeOdd = 0D;
	//绿色通道
	private Double greenMoney = 0D;
	//备注
	@Column(length = 300)
	private String remark;
	
	//是否通过
	private String iftongguo;

	@OneToMany(mappedBy = "financeStudent", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FinanceStudentItem> items = new ArrayList<FinanceStudentItem>();

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Double getFeeTotal() {
		return feeTotal;
	}

	public void setFeeTotal(Double feeTotal) {
		this.feeTotal = feeTotal;
	}

	public Double getFeePaid() {
		return feePaid;
	}

	public void setFeePaid(Double feePaid) {
		this.feePaid = feePaid;
	}

	public Double getFeeOdd() {
		return feeOdd;
	}

	public void setFeeOdd(Double feeOdd) {
		this.feeOdd = feeOdd;
	}

	public Double getGreenMoney() {
		return greenMoney;
	}

	public void setGreenMoney(Double greenMoney) {
		this.greenMoney = greenMoney;
	}

	public List<FinanceStudentItem> getItems() {
		return items;
	}

	public void setItems(List<FinanceStudentItem> items) {
		this.items = items;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 未缴金额
	 *
	 * @return
	 */
	public Double getFeeLeft() {
		return feeTotal - greenMoney - feePaid;
	}

	public void countFeeOdd() {
		feeOdd = getFeeLeft();
	}

	public void countTotal() {
		feeTotal = 0D;
		for (FinanceStudentItem item : items) {
			feeTotal += item.getFeeTotal();
		}
	}

	public String getIftongguo() {
		return iftongguo;
	}

	public void setIftongguo(String iftongguo) {
		this.iftongguo = iftongguo;
	}

	
}
