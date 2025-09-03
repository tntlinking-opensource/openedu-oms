package com.yzsoft.yxxt.prepare.model;

import org.apache.commons.beanutils.BeanUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity(name = "com.yzsoft.yxxt.prepare.model.StudentClass")
public class StudentClass extends LongIdObject {

    @NotNull
    private Batch batch;

    @NotNull
    private StudentEnroll enroll;

    @NotNull
    private Student student;

    private AdminClassTemp adminClass;

    public StudentClass() {
    }

    public StudentClass(StudentClass studentClass, StudentEnroll student) {
        try {
            setId(student.getId());
            BeanUtils.copyProperties(this, studentClass);
        } catch (IllegalAccessException e) {
        } catch (Exception e) {
        }
        this.enroll = student;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public StudentEnroll getEnroll() {
        return enroll;
    }

    public void setEnroll(StudentEnroll enroll) {
        this.enroll = enroll;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AdminClassTemp getAdminClass() {
        return adminClass;
    }

    public void setAdminClass(AdminClassTemp adminClass) {
        this.adminClass = adminClass;
    }

    @Override
    public Long getId() {
        return student.getId();
    }

    public Long getId2() {
        return id;
    }
}
