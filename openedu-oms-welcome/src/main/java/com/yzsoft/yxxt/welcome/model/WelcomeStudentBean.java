package com.yzsoft.yxxt.welcome.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;

/**
 * 迎新学生
 */
@MappedSuperclass
public class WelcomeStudentBean extends LongIdObject {

    //批次
    private ProcessBatch batch;
    //学生
    private Student student;
    //是否已报到
    private Boolean registered = false;
    //未报到原因
    private DictData reason;
    
    //未报到原因
    private String remark;

    public WelcomeStudentBean() {
    }

	public ProcessBatch getBatch() {
		return batch;
	}

	public void setBatch(ProcessBatch batch) {
		this.batch = batch;
	}

	public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Boolean getRegistered() {
        return registered;
    }

    public void setRegistered(Boolean registered) {
        this.registered = registered;
    }

    public DictData getReason() {
        return reason;
    }

    public void setReason(DictData reason) {
        this.reason = reason;
    }

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
