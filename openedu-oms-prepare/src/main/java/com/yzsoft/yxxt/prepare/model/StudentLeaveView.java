package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;

/*
 create or replace view YXP_STUDENT_LEAVE_VIEWS as
 select S.ID,
        S.ID STUDENT_ID,
        SL.ID LEAVE_ID
   from CP_STUDENTS S
   LEFT JOIN YXP_STUDENT_LEAVES SL
     ON S.ID = SL.STUDENT_ID
 */
@Entity
public class StudentLeaveView extends LongIdObject {
    //学生
    private Student student;
    private StudentLeave leave;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentLeave getLeave() {
        return leave;
    }

    public void setLeave(StudentLeave leave) {
        this.leave = leave;
    }
}
