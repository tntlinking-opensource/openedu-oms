package com.yzsoft.yxxt.welcome.process.bean;

import com.yzsoft.dorm.model.DormBed;
import org.beangle.product.core.model.Student;

public class StudentPrint {

    private Student student;
    private DormBed bed;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public DormBed getBed() {
        return bed;
    }

    public void setBed(DormBed bed) {
        this.bed = bed;
    }
}
