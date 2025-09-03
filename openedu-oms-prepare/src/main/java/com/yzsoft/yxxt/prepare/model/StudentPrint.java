package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;

/**
 * 学生打印状态
 */
@Entity
public class StudentPrint extends LongIdObject {

    private Student student;
    private boolean printNotice;
    private boolean printFace;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isPrintNotice() {
        return printNotice;
    }

    public void setPrintNotice(boolean printNotice) {
        this.printNotice = printNotice;
    }

    public boolean isPrintFace() {
        return printFace;
    }

    public void setPrintFace(boolean printFace) {
        this.printFace = printFace;
    }
}
