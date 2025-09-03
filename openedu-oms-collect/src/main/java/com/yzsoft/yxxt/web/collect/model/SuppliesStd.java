package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.StudentObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 日用品
 */
@Entity
public class SuppliesStd extends StudentObject {

	private Boolean agree;
	private Integer num;
	private Double total = 0D;
	//是否领取
	private boolean used = Boolean.FALSE;

	@OneToMany(mappedBy = "suppliesStd", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("id")
	private List<SuppliesStdItem> items = new ArrayList<SuppliesStdItem>();

	public Boolean getAgree() {
		return agree;
	}

	public void setAgree(Boolean agree) {
		this.agree = agree;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<SuppliesStdItem> getItems() {
		return items;
	}

	public void setItems(List<SuppliesStdItem> items) {
		this.items = items;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
}
