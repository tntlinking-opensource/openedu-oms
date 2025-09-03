package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;

public class AdminClassPlan extends LongIdObject{
	private String  grade;
	private Long majorId;
	private Long directionId;
	private Long num;

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Long getMajorId() {
		return majorId;
	}

	public void setMajorId(Long majorId) {
		this.majorId = majorId;
	}

	public Long getDirectionId() {
		return directionId;
	}

	public void setDirectionId(Long directionId) {
		this.directionId = directionId;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
}
