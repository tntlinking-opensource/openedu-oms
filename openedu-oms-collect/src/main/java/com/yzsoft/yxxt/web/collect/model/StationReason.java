package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;

/**
 * 不按时报到原因
 */
@Entity
public class StationReason extends LongIdObject {
	private String name;
	private Boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
}
