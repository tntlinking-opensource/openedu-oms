package com.yzsoft.yxxt.web.ask.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.MsgGroupMsg")
public class MsgGroupMsg extends LongIdObject {
	//讨论组
	private MsgGroup group;
	//发送人
	private User user;
	//发送内容
	@Column(length = 300)
	private String content;
	//发送时间
	private Date createTime;
	//引用消息
	private MsgGroupMsg quote;

	public MsgGroup getGroup() {
		return group;
	}

	public void setGroup(MsgGroup group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public MsgGroupMsg getQuote() {
		return quote;
	}

	public void setQuote(MsgGroupMsg quote) {
		this.quote = quote;
	}
}
