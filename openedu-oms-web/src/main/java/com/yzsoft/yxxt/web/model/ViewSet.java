package com.yzsoft.yxxt.web.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.persistence.Column;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(region = "yxxt", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ViewSet extends LongIdObject {
	/**
	 * 名称
	 */
	private String name;

	/**
	 * 配置
	 */
	@Lob
	@Basic(fetch = FetchType.EAGER)
	@Column(columnDefinition = "CLOB")
	private String config;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getConfig() {
		return config;
	}

	public void setConfig(String config) {
		this.config = config;
	}
}
