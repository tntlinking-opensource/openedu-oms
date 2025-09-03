package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;

@Entity
public class SuppliesStdItem extends LongIdObject {

	private SuppliesStd suppliesStd;
	private Supplies supplies;
	private Double price;
	private Integer num;
	private Double total;

    public SuppliesStd getSuppliesStd() {
        return suppliesStd;
    }

    public void setSuppliesStd(SuppliesStd suppliesStd) {
        this.suppliesStd = suppliesStd;
    }

    public Supplies getSupplies() {
        return supplies;
    }

    public void setSupplies(Supplies supplies) {
        this.supplies = supplies;
    }

	public Integer getNum() {
		return num;
	}

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
