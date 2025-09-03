package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

/**
 * 学生迎新
 */
@Entity
@Cacheable
@Cache(region = "yxxt.prepare", usage = CacheConcurrencyStrategy.READ_WRITE)
public class PrepareConfig extends LongIdObject {

	//学生密码生成规则
	private DictData passwordType;

	public DictData getPasswordType() {
		return passwordType;
	}

	public void setPasswordType(DictData passwordType) {
		this.passwordType = passwordType;
	}
}
