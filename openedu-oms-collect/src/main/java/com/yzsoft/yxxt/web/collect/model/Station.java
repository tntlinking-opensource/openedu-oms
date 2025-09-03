package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * 到达站
 */
@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Station extends LongIdObject {

    //交通工具
    private DictData vehicle;
    //站名
	private String name;

    public DictData getVehicle() {
        return vehicle;
    }

    public void setVehicle(DictData vehicle) {
        this.vehicle = vehicle;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
