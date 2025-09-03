package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;

import javax.persistence.Entity;

/**
 * 学生缴费状态明细
 */
@Entity
public class FinanceStudentItem extends LongIdObject {

    private FinanceStudent financeStudent;

    private FinanceItem item;
    //应缴金额
    private Double feeTotal = 0D;
    //已缴金额
    private Double feePaid = 0D;
    //未缴金额
    private Double feeOdd = 0D;
    //绿色通道
    private Double greenMoney = 0D;

    public FinanceStudent getFinanceStudent() {
        return financeStudent;
    }

    public void setFinanceStudent(FinanceStudent financeStudent) {
        this.financeStudent = financeStudent;
    }

    public FinanceItem getItem() {
        return item;
    }

    public void setItem(FinanceItem item) {
        this.item = item;
    }

    public Double getFeeTotal() {
        return feeTotal;
    }

    public void setFeeTotal(Double feeTotal) {
        this.feeTotal = feeTotal;
    }

    public Double getFeePaid() {
        return feePaid;
    }

    public void setFeePaid(Double feePaid) {
        this.feePaid = feePaid;
    }

    public Double getFeeOdd() {
        return feeOdd;
    }

    public void setFeeOdd(Double feeOdd) {
        this.feeOdd = feeOdd;
    }

    public Double getGreenMoney() {
        return greenMoney;
    }

    public void setGreenMoney(Double greenMoney) {
        this.greenMoney = greenMoney;
    }
}
