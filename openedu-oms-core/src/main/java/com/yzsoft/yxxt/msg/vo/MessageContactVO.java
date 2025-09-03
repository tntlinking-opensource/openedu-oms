package com.yzsoft.yxxt.msg.vo;

public class MessageContactVO {

	private Long userId;
	private String name;
	private String gender;
	private String major;
	private String adminClass;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getAdminClass() {
		return adminClass;
	}

	public void setAdminClass(String adminClass) {
		this.adminClass = adminClass;
	}
}
