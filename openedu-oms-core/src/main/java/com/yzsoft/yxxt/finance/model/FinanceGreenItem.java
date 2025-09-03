package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;

@Entity(name = "com.yzsoft.yxxt.finance.model.FinanceGreenItem")
public class FinanceGreenItem extends LongIdObject {
	private FinanceGreen green;
	private FinanceItem item;
	private double money;

	public FinanceGreen getGreen() {
		return green;
	}

	public void setGreen(FinanceGreen green) {
		this.green = green;
	}

	public FinanceItem getItem() {
		return item;
	}

	public void setItem(FinanceItem item) {
		this.item = item;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}
}
