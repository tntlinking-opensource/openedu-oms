package com.yzsoft.yxxt.finance.model;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity(name = "com.yzsoft.yxxt.finance.model.FinanceGreen")
public class FinanceGreen extends LongIdObject {
    private String code;
    //名称
    private String name;
    //学历层次
    @ManyToMany
    private Set<DictData> educations = CollectUtils.newHashSet();
    @Column(length = 500)
    private String remark;
    private boolean manual;
    private double money;
    private boolean enabled = true;
    @OneToMany(mappedBy = "green", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FinanceGreenItem> items = new ArrayList<FinanceGreenItem>();

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

    public Set<DictData> getEducations() {
        return educations;
    }

    public void setEducations(Set<DictData> educations) {
        this.educations = educations;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public boolean isManual() {
        return manual;
    }

    public void setManual(boolean manual) {
        this.manual = manual;
    }

    public List<FinanceGreenItem> getItems() {
        return items;
    }

    public void setItems(List<FinanceGreenItem> items) {
        this.items = items;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getMoney(FinanceItem fitem) {
        for (FinanceGreenItem item : items) {
            if (item.getItem().equals(fitem)) {
                return item.getMoney();
            }
        }
        return 0;
    }
}
