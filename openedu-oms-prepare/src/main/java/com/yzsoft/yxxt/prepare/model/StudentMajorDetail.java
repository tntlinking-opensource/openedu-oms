package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;

import javax.persistence.Entity;

@Entity
public class StudentMajorDetail extends LongIdObject {

    private StudentMajor studentMajor;

    private Integer sort;

    private Major major;

    public StudentMajor getStudentMajor() {
        return studentMajor;
    }

    public void setStudentMajor(StudentMajor studentMajor) {
        this.studentMajor = studentMajor;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

}
