package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;

/**
 * 学生打印状态视图
 */
/*
create or replace view yxp_student_print_views as
select s.id,
       s.id student_id,
       p.id print_id,
       case
         when p.print_notice is null then
          0
         else
          p.print_notice
       end print_notice,
       case
         when p.print_face is null then
          0
         else
          p.print_face
       end print_face
  from cp_students s
  left join yxp_student_prints p
    on s.id = p.student_id
 where s.registed = 1;
 */
@Entity
public class StudentPrintView extends LongIdObject {

    private Student student;
    private StudentPrint print;
    private boolean printNotice;
    private boolean printFace;

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public StudentPrint getPrint() {
        return print;
    }

    public void setPrint(StudentPrint print) {
        this.print = print;
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
