package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 采集开关
 */
@Entity
@Cacheable
@Cache(region = "yxxt.web", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CollectSwitch extends LongIdObject {
	private String code;
	private Integer sort;
	private String icon;
	private String name;
	private String url;
	@Column(length = 3000)
	private String remark;
	private Date startTime;
	private Date endTime;
	private boolean enabled = true;
	private boolean editable = true;
	private Integer adflag= 1;////20201106增加是否取消宿舍功字段
	private boolean required;
	private boolean payment;
	//招生类别
	@ManyToMany
	@JoinTable(name = "YXW_COLLECT_SWITCHES_ETS")
	private List<DictData> enrollTypes = new ArrayList<DictData>(0);
	//学历层次
	@ManyToMany
	@JoinTable(name = "YXW_COLLECT_SWITCHES_EDUS")
	private List<DictData> educations = new ArrayList<DictData>(0);

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public boolean isPayment() {
		return payment;
	}

	public void setPayment(boolean payment) {
		this.payment = payment;
	}

	public boolean isEditable() {
		Date now = new Date();
		if (startTime != null && startTime.after(now)) {
			return false;
		}
		if (endTime != null && endTime.before(now)) {
			return false;
		}
		if (!enabled) {
			return false;
		}
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public List<DictData> getEnrollTypes() {
		return enrollTypes;
	}

	public void setEnrollTypes(List<DictData> enrollTypes) {
		this.enrollTypes = enrollTypes;
	}

	public List<DictData> getEducations() {
		return educations;
	}

	public void setEducations(List<DictData> educations) {
		this.educations = educations;
	}

	public Integer isAdflag() {
		return adflag;
	}

	public void setAdflag(Integer adflag) {
		this.adflag = adflag;
	}
}
