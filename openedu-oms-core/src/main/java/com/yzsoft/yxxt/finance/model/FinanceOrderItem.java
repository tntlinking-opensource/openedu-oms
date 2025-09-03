package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;

/**
 * 订单项
 */
@Entity
public class FinanceOrderItem extends LongIdObject {
	//订单
	private FinanceOrder order;
	//物品名称
	private String name;
	//数量
	private Integer num;
	//金额
	private Double money;

	public FinanceOrder getOrder() {
		return order;
	}

	public void setOrder(FinanceOrder order) {
		this.order = order;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}
}
