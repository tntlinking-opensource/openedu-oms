package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 学生扩展信息
 */
@Entity
public class StudentContact extends LongIdObject {

    //手机
    @Column(length = 20)
    private String phone;
    //电话
    @Column(length = 20)
    private String telephone;
    //母亲电话
    @Column(length = 20)
    private String motherPhone;
    //父亲电话
    @Column(length = 20)
    private String fatherPhone;
    //地址
    @Column(length = 100)
    private String address;
    //邮编
    @Column(length = 6)
    private String zipcode;
    //户籍地址
    @Column(length = 100)
    private String householdAddress;
    //电子邮箱
    @Column(length = 100)
    private String email;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMotherPhone() {
        return motherPhone;
    }

    public void setMotherPhone(String motherPhone) {
        this.motherPhone = motherPhone;
    }

    public String getFatherPhone() {
        return fatherPhone;
    }

    public void setFatherPhone(String fatherPhone) {
        this.fatherPhone = fatherPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getHouseholdAddress() {
        return householdAddress;
    }

    public void setHouseholdAddress(String householdAddress) {
        this.householdAddress = householdAddress;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
