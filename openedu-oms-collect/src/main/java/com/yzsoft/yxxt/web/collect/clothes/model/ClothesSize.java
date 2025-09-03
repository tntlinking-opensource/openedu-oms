package com.yzsoft.yxxt.web.collect.clothes.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import java.util.Arrays;
import java.util.List;

@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClothesSize extends LongIdObject {
	//类型
	private ClothesType type;
	//排序
	private String code;
	//名称：鞋码或身高等对应的尺码名称
	private String name;
	//值
	private String value;

	public ClothesType getType() {
		return type;
	}

	public void setType(ClothesType type) {
		this.type = type;
	}

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public List<String> getCols() {
		return Arrays.asList(value.split(" "));
	}
}
