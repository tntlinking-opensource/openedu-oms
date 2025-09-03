package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity
@Cacheable
@Cache(region = "yxxt.core", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DormSetting extends LongIdObject {

    private FinanceItem item;
    private Double money;

    public FinanceItem getItem() {
        return item;
    }

    public void setItem(FinanceItem item) {
        this.item = item;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }
}
