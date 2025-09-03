package com.yzsoft.yxxt.web.ask.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.AskPlate")
@Cacheable
@Cache(region = "yxxt.ask", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AskPlate extends LongIdObject {

	private String code;
	private String name;
	private User user;
	private boolean enabled = true;

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
