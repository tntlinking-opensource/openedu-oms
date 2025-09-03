package com.yzsoft.yxxt.web.ask.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;
import java.util.Date;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.MsgGroupMember")
public class MsgGroupMember extends LongIdObject {
	//讨论组
	private MsgGroup group;
	//用户
	private User user;
	//最后一次接收消息时间
	private Date lastTime;
	//未读数量
	private Integer unreadNum = 0;

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

	public Date getLastTime() {
		return lastTime;
	}

	public void setLastTime(Date lastTime) {
		this.lastTime = lastTime;
	}

	public Integer getUnreadNum() {
		return unreadNum;
	}

	public void setUnreadNum(Integer unreadNum) {
		this.unreadNum = unreadNum;
	}
}
