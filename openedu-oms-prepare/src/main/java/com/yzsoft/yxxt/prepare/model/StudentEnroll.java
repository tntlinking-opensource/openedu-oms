package com.yzsoft.yxxt.prepare.model;

import org.beangle.ems.security.User;
import org.beangle.product.core.model.StudentObject;
import org.beangle.website.common.util.DateUtil;
import org.beangle.website.system.model.DictData;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class StudentEnroll extends StudentObject {
    //用户
    private User user;
    //招生批次
    @NotNull
    private Batch batch;
    //录取号、报名号
    @Column(length = 32, unique = true)
    private String code;
    //录取号
    @Column(length = 32, unique = true)
    private String enrollCode;
    //考试号
    @Column(length = 30)
    private String examCode;
    //通知书号
    private String noticeCode;
    //录取时照片
    private String photo;
    //家长姓名
    @Column(length = 50)
    private String parentName;
    //家长电话
    @Column(length = 30)
    private String parentPhone;
    //联系方式
    @OneToOne(cascade = CascadeType.ALL)
    private StudentContact contact;
    //成绩
    @OneToOne(cascade = CascadeType.ALL)
    private StudentScore score;
    //成绩总分
    private Float scoreTotal;
    //毕业学校
    @Column(length = 60)
    private String schoolName;
    //最后来源班级
    @Column(length = 30)
    private String className;
    //应届还是往届
    private DictData graduate;
    //是否精准扶贫户
    @Column(length = 30)
    private String green;
    //推荐方式
    private DictData recommendType;
    //推荐人姓名
    @Column(length = 60)
    private String recommendName;
    //推荐人电话
    @Column(length = 30)
    private String recommendPhone;
    //是否通知
    private Boolean noticed = false;
    //是否录取
    private Boolean enrolled = false;
    //是否注册
    private Boolean registed = false;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Batch getBatch() {
        return batch;
    }

    public void setBatch(Batch batch) {
        this.batch = batch;
    }

    public String getEnrollCode() {
        return enrollCode;
    }

    public void setEnrollCode(String enrollCode) {
        this.enrollCode = enrollCode;
    }

    public String getNoticeCode() {
        return noticeCode;
    }

    public void setNoticeCode(String noticeCode) {
        this.noticeCode = noticeCode;
    }

    public String getExamCode() {
        return examCode;
    }

    public void setExamCode(String examCode) {
        this.examCode = examCode;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Boolean getNoticed() {
        return noticed;
    }

    public void setNoticed(Boolean noticed) {
        this.noticed = noticed;
    }

    public Boolean getEnrolled() {
        return enrolled;
    }

    public void setEnrolled(Boolean enrolled) {
        this.enrolled = enrolled;
    }

    public StudentScore getScore() {
        return score;
    }

    public void setScore(StudentScore score) {
        this.score = score;
    }

    public StudentContact getContact() {
        return contact;
    }

    public void setContact(StudentContact contact) {
        this.contact = contact;
    }

    public String getNoticedStr() {
        return noticed ? "是" : "否";
    }

    public String getEnrolledStr() {
        return enrolled ? "是" : "否";
    }

    public String getBirthdayStr() {
        return DateUtil.getYearMonthStr(getStudent().getBirthday());
    }

    public Float getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(Float scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentPhone() {
        return parentPhone;
    }

    public void setParentPhone(String parentPhone) {
        this.parentPhone = parentPhone;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public DictData getGraduate() {
        return graduate;
    }

    public void setGraduate(DictData graduate) {
        this.graduate = graduate;
    }

    public String getGreen() {
        return green;
    }

    public void setGreen(String green) {
        this.green = green;
    }

    public DictData getRecommendType() {
        return recommendType;
    }

    public void setRecommendType(DictData recommendType) {
        this.recommendType = recommendType;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getRecommendPhone() {
        return recommendPhone;
    }

    public void setRecommendPhone(String recommendPhone) {
        this.recommendPhone = recommendPhone;
    }

    public Boolean getRegisted() {
        return registed;
    }

    public void setRegisted(Boolean registed) {
        this.registed = registed;
    }
}
