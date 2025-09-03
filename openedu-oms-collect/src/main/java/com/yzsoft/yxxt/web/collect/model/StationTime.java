package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * 到达时间设置
 */
@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class StationTime extends LongIdObject {

	private StationDate date;

	private Integer sort;
	private String time;

	public StationDate getDate() {
		return date;
	}

	public void setDate(StationDate date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
}
