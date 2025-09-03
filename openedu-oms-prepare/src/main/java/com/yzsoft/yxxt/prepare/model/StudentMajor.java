package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class StudentMajor extends LongIdObject {

    public enum WishOrder {
        WISH_ORDER_1("第一志愿"), WISH_ORDER_2("第二志愿"), WISH_ORDER_3("第三志愿"),
        WISH_ORDER_4("第三志愿"), WISH_ORDER_5("第五志愿"), WISH_ORDER_6("第六志愿"),
        WISH_ORDER_7("第七志愿"), WISH_ORDER_8("第八志愿"), WISH_ORDER_9("第九志愿"),
        WISH_ORDER_10("管理员修改"), WISH_ORDER_11("非志愿");
        private String name;

        WishOrder(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }

        public static WishOrder get(Integer sort) {
            if (sort == null) {
                return WISH_ORDER_11;
            }
            switch (sort) {
                case 1:
                    return WISH_ORDER_1;
                case 2:
                    return WISH_ORDER_2;
                case 3:
                    return WISH_ORDER_3;
                case 4:
                    return WISH_ORDER_4;
                case 5:
                    return WISH_ORDER_5;
                case 6:
                    return WISH_ORDER_6;
                case 7:
                    return WISH_ORDER_7;
                case 8:
                    return WISH_ORDER_8;
                case 9:
                    return WISH_ORDER_9;
            }
            return WISH_ORDER_11;
        }
    }

    private Batch batch;

    private StudentEnroll enroll;

    private Student student;

    @Column(length = 10)
    private String grade;
    //成绩总分
    private Float scoreTotal;
    //成绩明细
    @OneToOne(cascade = CascadeType.ALL)
    private StudentScore score;
    //志愿顺序
    private WishOrder wishOrder;
    //是否服从调剂
    private Boolean alterable = true;

    private Major major;
    @OneToMany(mappedBy = "studentMajor", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sort")
    private List<StudentMajorDetail> details = new ArrayList<StudentMajorDetail>();

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

    public WishOrder getWishOrder() {
        return wishOrder;
    }

    public void setWishOrder(WishOrder wishOrder) {
        this.wishOrder = wishOrder;
    }

    public Boolean getAlterable() {
        return alterable;
    }

    public void setAlterable(Boolean alterable) {
        this.alterable = alterable;
    }

    public List<StudentMajorDetail> getDetails() {
        return details;
    }

    public void setDetails(List<StudentMajorDetail> details) {
        this.details = details;
    }

    public Major getMajor1() {
        return getMajorBySort(1);
    }

    public Major getMajor2() {
        return getMajorBySort(2);
    }

    public Major getMajor3() {
        return getMajorBySort(3);
    }

    public Major getMajor4() {
        return getMajorBySort(4);
    }

    public Major getMajor5() {
        return getMajorBySort(5);
    }

    public Major getMajor6() {
        return getMajorBySort(6);
    }

    private Major getMajorBySort(int sort) {
        StudentMajorDetail detail = getDetail(sort);
        if (detail != null) {
            return detail.getMajor();
        }
        return null;
    }

    public StudentMajorDetail getDetail(Integer sort) {
        for (StudentMajorDetail detail : details) {
            if (detail.getSort() != null && detail.getSort().equals(sort)) {
                return detail;
            }
        }
        StudentMajorDetail detail = new StudentMajorDetail();
        detail.setStudentMajor(this);
        detail.setSort(sort);
        details.add(detail);
        return detail;
    }

    public StudentMajorDetail getDetailByMajor(Major major) {
        for (StudentMajorDetail detail : details) {
            if (detail.getMajor() != null && detail.getMajor().equals(major)) {
                return detail;
            }
        }
        return null;
    }

    public Float getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Float scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public StudentScore getScore() {
        return score;
    }

    public void setScore(StudentScore score) {
        this.score = score;
    }
}
