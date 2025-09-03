package com.yzsoft.yxxt.core.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Entity;

@Entity(name = "com.yzsoft.yxxt.core.model.SystemConfig")
@Cache(region = "yxxt.core", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemConfig extends LongIdObject {

	//年份
	private Integer year;
	//是否有院系
	private Boolean hasDepartment;
	//部门名称
	private String departmentName;

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Boolean getHasDepartment() {
		return hasDepartment;
	}

	public void setHasDepartment(Boolean hasDepartment) {
		this.hasDepartment = hasDepartment;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
}
