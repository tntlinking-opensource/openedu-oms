package com.yzsoft.yxxt.msg.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;
import java.util.Date;

@MappedSuperclass
public class MessageBase extends LongIdObject {

	private User from;
	private User to;
	@Column(length = 300)
	private String content;
	private Date createTime;

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

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
