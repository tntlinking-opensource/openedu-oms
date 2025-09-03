package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 个人信息采集状态
 */
@Entity
@Table(name = "YXW_COLLECT_STATE_SI")
public class StudentInfoCollectState extends LongIdObject {

    private Student student;

    private String code;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
