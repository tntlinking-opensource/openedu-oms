package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 日用品
 */
@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Supplies extends LongIdObject {
	//代码
	private String code;
	//名称
	private String name;
	//规格
	private String spec;
	//价格
	private Double price;
	//最大订购数量
	private Integer num;
	//最大订购数量
	private Integer maxNum;
	//类型：订购、信息收集（CODE = SUPPLIES_TYPE）
	private DictData type;
	//规格
	@Column(length = 300)
	private String remark;
	//是否启用
	private Boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpec() {
		return spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public DictData getType() {
		return type;
	}

	public void setType(DictData type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(Integer maxNum) {
		this.maxNum = maxNum;
	}

}
