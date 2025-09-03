package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 专业介绍
 */
@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class MajorInfo extends LongIdObject {
    //专业
    private Major major;
    //排序
    private Integer sort;
    //图标
    private String logo;
    //介绍
    @Lob
    @Column(columnDefinition = "CLOB")
    private String content;
    //是否置顶
    private Boolean isTop = false;
    //是否启用
    private Boolean enabled = true;
    //限制性别
    private DictData gender;
    //招生类别
    @ManyToMany
    private List<DictData> enrollTypes = new ArrayList<DictData>(0);

    public Major getMajor() {
        return major;
    }

    public void setMajor(Major major) {
        this.major = major;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getTop() {
        return isTop;
    }

    public void setTop(Boolean top) {
        isTop = top;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public DictData getGender() {
        return gender;
    }

    public void setGender(DictData gender) {
        this.gender = gender;
    }

    public List<DictData> getEnrollTypes() {
        return enrollTypes;
    }

    public void setEnrollTypes(List<DictData> enrollTypes) {
        this.enrollTypes = enrollTypes;
    }
}
