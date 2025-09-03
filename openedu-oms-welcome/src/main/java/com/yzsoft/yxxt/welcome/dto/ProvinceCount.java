package com.yzsoft.yxxt.welcome.dto;

public class ProvinceCount {
	//省份
	private String provinceName;
	//学生数
	private Long studentNum;
	//完成数
	private Long completeNum;
	//百分比
	private Double percent;

	public Long getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(Long completeNum) {
		this.completeNum = completeNum;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public Long getStudentNum() {
		return studentNum;
	}

	public void setStudentNum(Long studentNum) {
		this.studentNum = studentNum;
	}

	public Double getPercent() {
		return percent;
	}

	public void setPercent(Double percent) {
		this.percent = percent;
	}
}
