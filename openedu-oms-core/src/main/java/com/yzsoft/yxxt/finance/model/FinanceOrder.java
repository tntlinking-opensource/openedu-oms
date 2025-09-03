package com.yzsoft.yxxt.finance.model;

import org.beangle.ems.security.User;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 收费单
 */
@Entity
public class FinanceOrder extends LongIdObject {
	//订单号、流水号
	private String code;
	//用于区别不同业务的关键字
	private String key;
	//用户
	private User user;
	//学生
	private Student student;
	//订单内容
	private String name;
	//订单金额
	private Double money;
	//创建时间
	private Date createTime;
	//完成时间
	private Date finishTime;
	//是否完成
	private boolean finished;
	//是否有效
	private boolean enabled = true;
	//状态
	private DictData state;
	//备注
	private String remark;
	//订单项
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FinanceOrderItem> items = new ArrayList<FinanceOrderItem>();

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Double getMoney() {
		return money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public DictData getState() {
		return state;
	}

	public void setState(DictData state) {
		this.state = state;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<FinanceOrderItem> getItems() {
		return items;
	}

	public void setItems(List<FinanceOrderItem> items) {
		this.items = items;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
