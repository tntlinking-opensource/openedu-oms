package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ShoesSize extends LongIdObject {

	//代码
	private String code;
	//尺码
	private String name;
	//脚长
	private String footLength;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFootLength() {
		return footLength;
	}

	public void setFootLength(String footLength) {
		this.footLength = footLength;
	}
}
