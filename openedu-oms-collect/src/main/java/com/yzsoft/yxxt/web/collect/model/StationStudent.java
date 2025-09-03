package com.yzsoft.yxxt.web.collect.model;

import org.beangle.product.core.model.StudentObject;
import org.beangle.website.system.model.DictData;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 学生到达登记
 */
@Entity
public class StationStudent extends StudentObject {
    //是否按时报到
    private Boolean intime = true;
    //交通工具
    private DictData vehicle;
    //是否需要接站
    private Boolean needPickup;
    //到达日期
    @Temporal(TemporalType.DATE)
    private Date arriveDate;
    //到达时间
    private String arriveTime;
    //到达站
    private String station;
    //陪同人数
    private Integer num;
    //不按时报到原因
    private String reason;

    public Boolean getIntime() {
        return intime;
    }

    public void setIntime(Boolean intime) {
        this.intime = intime;
    }

    public DictData getVehicle() {
        return vehicle;
    }

    public void setVehicle(DictData vehicle) {
        this.vehicle = vehicle;
    }

    public Boolean getNeedPickup() {
        return needPickup;
    }

    public void setNeedPickup(Boolean needPickup) {
        this.needPickup = needPickup;
    }

    public Date getArriveDate() {
        return arriveDate;
    }

    public void setArriveDate(Date arriveDate) {
        this.arriveDate = arriveDate;
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
