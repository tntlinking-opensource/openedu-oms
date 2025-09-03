package com.yzsoft.yxxt.statistics.dto;

public class CountItem {

	private String year;
	private String name;
	private Long num;

	public CountItem() {
	}

	public CountItem(String name, Long num) {
		this.name = name;
		this.num = num;
	}

	public CountItem(String year, String name, Long num) {
		this(name, num);
		this.year = year;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getNum() {
		return num;
	}

	public void setNum(Long num) {
		this.num = num;
	}
}


