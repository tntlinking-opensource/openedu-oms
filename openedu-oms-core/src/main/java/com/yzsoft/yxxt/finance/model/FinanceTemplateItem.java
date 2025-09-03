package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;
import org.beangle.website.system.model.DictData;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FinanceTemplateItem extends LongIdObject {
	private FinanceTemplate template;
	private FinanceItem item;
	private Double money;

	public FinanceTemplate getTemplate() {
		return template;
	}

	public void setTemplate(FinanceTemplate template) {
		this.template = template;
	}

	public FinanceItem getItem() {
		return item;
	}

	public void setItem(FinanceItem item) {
		this.item = item;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public FinanceTemplateItem copy() {
		FinanceTemplateItem newItem = new FinanceTemplateItem();
		newItem.template = template;
		newItem.item = item;
		newItem.money = money;
		return newItem;
	}
}
