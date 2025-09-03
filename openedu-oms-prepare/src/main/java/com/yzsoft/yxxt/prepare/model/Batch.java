package com.yzsoft.yxxt.prepare.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Major;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.Set;


/**
 * 招生批次
 */
@Entity
@Cacheable
@Cache(region = "yxxt.core", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Batch extends LongIdObject {
    //名称
    private String name;
    //学年
    private String year;
    //学期
    private DictData term;
    //学历层次
    @ManyToMany
    private Set<DictData> educations = CollectUtils.newHashSet();
    //开放专业
    @ManyToMany
    private Set<Major> majors = CollectUtils.newHashSet();
    //开始时间
    private Date startTime;
    //结束时间
    private Date endTime;
    //状态
    private boolean enabled = true;
    //备注
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public DictData getTerm() {
        return term;
    }

    public void setTerm(DictData term) {
        this.term = term;
    }

    public Set<DictData> getEducations() {
        return educations;
    }

    public void setEducations(Set<DictData> educations) {
        this.educations = educations;
    }

    public Set<Major> getMajors() {
        return majors;
    }

    public void setMajors(Set<Major> majors) {
        this.majors = majors;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        Date now = new Date();
        if (now.after(endTime)) {
            return "已结束";
        } else if (now.before(startTime)) {
            return "未开始";
        }
        return "进行中";
    }
}
