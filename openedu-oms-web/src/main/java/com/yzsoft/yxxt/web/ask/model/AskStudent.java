package com.yzsoft.yxxt.web.ask.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Department;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "com.yzsoft.yxxt.web.ask.model.AskStudent")
@Cacheable
@Cache(region = "yxxt.web", usage = CacheConcurrencyStrategy.READ_WRITE)
public class AskStudent extends LongIdObject {

	public static final Integer SCOPE_HIDDEN = 0;
	public static final Integer SCOPE_STUDENT = 1;
	public static final Integer SCOPE_DEPARTMENT = 2;
	public static final String STATUS_NOT_REPLY = "未回复";
	public static final String STATUS_REPLYED = "已回复";

//	@NotNull
	private AskPlate plate;
	@NotNull
	private User student;
	private Department department;
	@NotNull
	@Column(length = 1000)
	private String content;
	@NotNull
	private Date createTime;
	private User teahcer;
	@Column(length = 1000)
	private String replyContent;
	private Date replyTime;
	@Column(length = 10)
	private String status = STATUS_NOT_REPLY;
	private Integer scope = SCOPE_STUDENT;

	public AskPlate getPlate() {
		return plate;
	}

	public void setPlate(AskPlate plate) {
		this.plate = plate;
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

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public Date getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(Date replyTime) {
		this.replyTime = replyTime;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public User getTeahcer() {
		return teahcer;
	}

	public void setTeahcer(User teahcer) {
		this.teahcer = teahcer;
	}

	public Integer getScope() {
		return scope;
	}

	public void setScope(Integer scope) {
		this.scope = scope;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
}
