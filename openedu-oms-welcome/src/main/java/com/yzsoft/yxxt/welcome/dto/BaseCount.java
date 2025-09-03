package com.yzsoft.yxxt.welcome.dto;

public class BaseCount {
	private String name;
	private String department;
	//学生数
	private Long studentNum;
	//完成数
	private Long completeNum;
	//百分比
	private Double percent;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public Long getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(Long studentNum) {
		this.studentNum = studentNum;
	}

	public Long getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(Long completeNum) {
		this.completeNum = completeNum;
	}

	public Double getPercent() {
		if (percent == null && studentNum != null && studentNum > 0 && completeNum != null) {
			percent = completeNum * 1.0 / studentNum;
		}
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}
}
