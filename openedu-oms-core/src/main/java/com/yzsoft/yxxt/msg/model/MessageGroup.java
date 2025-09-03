package com.yzsoft.yxxt.msg.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class MessageGroup extends LongIdObject {

	private User from;
	private User to;
	private Date updateTime;

	public User getFrom() {
		return from;
	}

	public void setFrom(User from) {
		this.from = from;
	}

	public User getTo() {
		return to;
	}

	public void setTo(User to) {
		this.to = to;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
}
