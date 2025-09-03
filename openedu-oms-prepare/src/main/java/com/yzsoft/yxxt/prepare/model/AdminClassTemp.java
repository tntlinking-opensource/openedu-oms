package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Direction;
import org.beangle.product.core.model.Major;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "com.yzsoft.yxxt.prepare.model.AdminClassTemp")
public class AdminClassTemp extends LongIdObject {
    private String grade;
    private String code;
    private String name;
    private Major major;
    private Direction direction;
    @OneToMany(mappedBy = "adminClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClass> students = new ArrayList<StudentClass>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<StudentClass> getStudents() {
        return students;
    }

    public void setStudents(List<StudentClass> students) {
        this.students = students;
    }
}
