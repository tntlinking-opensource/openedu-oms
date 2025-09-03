package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;

/**
 * 成绩信息
 */
@Entity
public class StudentScore extends LongIdObject {
    //语文
    private Float chinese;
    //数学
    private Float math;
    //英语
    private Float english;
    //物理
    private Float physics;
    //化学
    private Float chemistry;
    //生物
    private Float biology;
    //政治
    private Float politics;
    //地理
    private Float geography;
    //历史
    private Float history;
    //体育
    private Float sports;
    //艺术
    private Float art;
    //加分
    private Float plus;

    public Float getChinese() {
        return chinese;
    }

    public void setChinese(Float chinese) {
        this.chinese = chinese;
    }

    public Float getMath() {
        return math;
    }

    public void setMath(Float math) {
        this.math = math;
    }

    public Float getEnglish() {
        return english;
    }

    public void setEnglish(Float english) {
        this.english = english;
    }

    public Float getPhysics() {
        return physics;
    }

    public void setPhysics(Float physics) {
        this.physics = physics;
    }

    public Float getChemistry() {
        return chemistry;
    }

    public void setChemistry(Float chemistry) {
        this.chemistry = chemistry;
    }

    public Float getBiology() {
        return biology;
    }

    public void setBiology(Float biology) {
        this.biology = biology;
    }

    public Float getPolitics() {
        return politics;
    }

    public void setPolitics(Float politics) {
        this.politics = politics;
    }

    public Float getGeography() {
        return geography;
    }

    public void setGeography(Float geography) {
        this.geography = geography;
    }

    public Float getHistory() {
        return history;
    }

    public void setHistory(Float history) {
        this.history = history;
    }

    public Float getSports() {
        return sports;
    }

    public void setSports(Float sports) {
        this.sports = sports;
    }

    public Float getArt() {
        return art;
    }

    public void setArt(Float art) {
        this.art = art;
    }

    public Float getPlus() {
        return plus;
    }

    public void setPlus(Float plus) {
        this.plus = plus;
    }
}
