package com.yzsoft.yxxt.welcome.dto;

public class NationCount {
	//专业名称
	private String nationName;
	//完成数
	private Long completeNum;

	public Long getCompleteNum() {
		return completeNum;
	}

	public void setCompleteNum(Long completeNum) {
		this.completeNum = completeNum;
	}

	public String getNationName() {
		return nationName;
	}

	public void setNationName(String nationName) {
		this.nationName = nationName;
	}
}
