package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;
import org.beangle.website.system.model.DictData;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FinanceTemplate extends LongIdObject {
	//年份
	private Integer year;
	//名称
	private String name;
	//是否限制专业
	private boolean limitMajor = false;
	//状态
	private boolean enabled = true;
	//总金额
	private Double money;
	//学历层次
	private DictData education;
	//专业类别
	private DictData majorType;
	//适用专业
	@ManyToMany
	private List<Major> majors = new ArrayList<Major>();
	@OneToMany(mappedBy = "template", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FinanceTemplateItem> items = new ArrayList<FinanceTemplateItem>();

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public DictData getEducation() {
		return education;
	}

	public void setEducation(DictData education) {
		this.education = education;
	}

	public DictData getMajorType() {
		return majorType;
	}

	public void setMajorType(DictData majorType) {
		this.majorType = majorType;
	}

	public List<Major> getMajors() {
		return majors;
	}

	public void setMajors(List<Major> majors) {
		this.majors = majors;
	}

	public List<FinanceTemplateItem> getItems() {
		return items;
	}

	public void setItems(List<FinanceTemplateItem> items) {
		this.items = items;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isLimitMajor() {
		return limitMajor;
	}

	public void setLimitMajor(boolean limitMajor) {
		this.limitMajor = limitMajor;
	}

	public FinanceTemplate copy() {
		FinanceTemplate template = new FinanceTemplate();
		template.year = year;
		template.name = name;
		template.limitMajor = limitMajor;
		template.enabled = enabled;
		template.money = money;
		template.education = education;
		template.majors.addAll(majors);
		for (FinanceTemplateItem item : items) {
			FinanceTemplateItem newItem = item.copy();
			newItem.setTemplate(template);
			template.getItems().add(newItem);
		}
		return template;
	}

	public double getMoney(FinanceItem fitem) {
		for (FinanceTemplateItem item : items) {
			if (item.getItem().equals(fitem)) {
				return item.getMoney();
			}
		}
		return 0;
	}
}
