package com.yzsoft.yxxt.web.ask.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.MsgGroup")
@Cacheable
@Cache(region = "yxxt.ask", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MsgGroup extends LongIdObject {

	//同班交流
	public static final Integer TYPE_CLASS = 0;
	//师生交流
	public static final Integer TYPE_STU_TEA = 1;

	//名称
	private String name;
	//代码
	private String code;
	//类型
	private Integer type;

	public MsgGroup() {
	}

	public MsgGroup(Long id) {
		super(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
