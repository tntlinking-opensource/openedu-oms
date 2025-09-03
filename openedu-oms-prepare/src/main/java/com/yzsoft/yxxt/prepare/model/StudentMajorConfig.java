package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class StudentMajorConfig extends LongIdObject {

	private Integer num = 3;
	private boolean showAdjust = true;

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public boolean isShowAdjust() {
		return showAdjust;
	}

	public void setShowAdjust(boolean showAdjust) {
		this.showAdjust = showAdjust;
	}
}
