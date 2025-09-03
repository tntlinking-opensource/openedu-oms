package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;

import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class MajorPlan extends LongIdObject {

    private Major major;
    private Integer total;
    private Integer remainder;
    //志愿外学生数
    @Transient
    private Integer alternateNum;

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRemainder() {
        return remainder;
    }

    public void setRemainder(Integer remainder) {
        this.remainder = remainder;
    }

    public Integer getAlternateNum() {
        return alternateNum;
    }

    public void setAlternateNum(Integer alternateNum) {
        this.alternateNum = alternateNum;
    }
}
