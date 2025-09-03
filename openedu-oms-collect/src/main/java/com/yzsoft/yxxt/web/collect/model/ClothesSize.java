package com.yzsoft.yxxt.web.collect.model;

import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 服装尺码
 */
@Entity
@Cacheable
@Cache(region = "yxxt.collect", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ClothesSize extends LongIdObject {

	//代码
	private String code;
	//尺码
	private String name;
	//身高（cm）
	private String height;
	//胸围（cm）
	private String bust;
	//腰围（cm）
	private String waist;
	//臀围（cm）
	private String hip;
	//裤长（cm）
	private String outseam;

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

	public String getBust() {
		return bust;
	}

	public void setBust(String bust) {
		this.bust = bust;
	}

	public String getWaist() {
		return waist;
	}

	public void setWaist(String waist) {
		this.waist = waist;
	}

	public String getHip() {
		return hip;
	}

	public void setHip(String hip) {
		this.hip = hip;
	}

	public String getOutseam() {
		return outseam;
	}

	public void setOutseam(String outseam) {
		this.outseam = outseam;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}
}
