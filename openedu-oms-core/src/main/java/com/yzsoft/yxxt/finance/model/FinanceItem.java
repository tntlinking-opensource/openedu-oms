package com.yzsoft.yxxt.finance.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

@Entity(name = "com.yzsoft.yxxt.finance.model.FinanceItem")
@Cacheable
@Cache(region = "yxxt.finance", usage = CacheConcurrencyStrategy.READ_WRITE)
public class FinanceItem extends LongIdObject {

	private String code;
	private String name;
	private boolean delayable;
	private boolean loanable;
	private boolean enabled = true;

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

	public boolean isDelayable() {
		return delayable;
	}

	public void setDelayable(boolean delayable) {
		this.delayable = delayable;
	}

	public boolean isLoanable() {
		return loanable;
	}

	public void setLoanable(boolean loanable) {
		this.loanable = loanable;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
