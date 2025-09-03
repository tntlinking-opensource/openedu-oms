package com.yzsoft.yxxt.web.collect.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;
import java.util.Date;

/**
 * 采集开关状态
 */
@Entity
public class CollectSwitchState extends LongIdObject {
    private CollectSwitch collectSwitch;
    private Student student;
    private User user;
    private boolean finished;
    private Date createTime;

    public CollectSwitch getCollectSwitch() {
        return collectSwitch;
    }

    public void setCollectSwitch(CollectSwitch collectSwitch) {
        this.collectSwitch = collectSwitch;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
