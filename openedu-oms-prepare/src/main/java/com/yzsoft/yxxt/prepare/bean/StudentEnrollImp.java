package com.yzsoft.yxxt.prepare.bean;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.beangle.product.core.model.StudentHome;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.website.common.util.DateUtil;

public class StudentEnrollImp extends StudentEnroll {

    private StudentInfo studentInfo;

    private StudentHome studentHome;

    private String noticedStr;

    private String enrolledStr;

    private String registedStr;

    private String birthdayStr;

    public StudentInfo getStudentInfo() {
        return studentInfo;
    }

    public void setStudentInfo(StudentInfo studentInfo) {
        this.studentInfo = studentInfo;
    }

    public StudentHome getStudentHome() {
        return studentHome;
    }

    public void setStudentHome(StudentHome studentHome) {
        this.studentHome = studentHome;
    }

    @Override
    public String getEnrolledStr() {
        return enrolledStr;
    }

    @Override
    public String getNoticedStr() {
        return noticedStr;
    }

    public void setNoticedStr(String noticedStr) {
        this.noticedStr = noticedStr;
        setNoticed("是".equals(noticedStr));
    }

    public void setEnrolledStr(String enrolledStr) {
        this.enrolledStr = enrolledStr;
        setEnrolled("是".equals(enrolledStr));
    }

    public String getRegistedStr() {
        return registedStr;
    }

    public void setRegistedStr(String registedStr) {
        this.registedStr = registedStr;
    }

    @Override
    public String getBirthdayStr() {
        return birthdayStr;
    }

    public void setBirthdayStr(String birthdayStr) {
        this.birthdayStr = birthdayStr;
        getStudent().setBirthday(DateUtil.getYearMonthDate(birthdayStr));
    }
}
