package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * 学生请假
 */
@Entity
public class StudentLeave extends LongIdObject {

    public static final String STATE_WXJ = "未销假";
    public static final String STATE_YXJ = "已销假";

    //学生
    private Student student;
    //开始日期
    @Temporal(TemporalType.DATE)
    private Date startDate;
    //天数
    private Integer days;
    //原因
    private String reason;
    //状态
    @Column(length = 10)
    private String state;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
