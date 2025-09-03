package com.yzsoft.yxxt.mobile.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;
import java.util.Calendar;
import java.util.Date;

@Entity
public class HomeHelp extends LongIdObject {

	private User user;
	private Date birthdayClickTime;
	private Date createTime;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getBirthdayClickTime() {
		return birthdayClickTime;
	}

	public void setBirthdayClickTime(Date birthdayClickTime) {
		this.birthdayClickTime = birthdayClickTime;
	}

	public Boolean getBirthdayClicked() {
		if (birthdayClickTime == null) {
			return false;
		}
		Calendar now = Calendar.getInstance();
		Calendar clickTime = Calendar.getInstance();
		clickTime.setTime(birthdayClickTime);
		return now.get(Calendar.YEAR) == clickTime.get(Calendar.YEAR);
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
