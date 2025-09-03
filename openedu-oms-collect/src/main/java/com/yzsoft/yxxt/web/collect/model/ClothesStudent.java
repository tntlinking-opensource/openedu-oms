package com.yzsoft.yxxt.web.collect.model;

import org.beangle.product.core.model.StudentObject;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

/**
 * 军训服装
 */
@Entity
public class ClothesStudent extends StudentObject {
	//服装尺码
	@NotNull
	private String clothesSize;
	//鞋子尺码
	@NotNull
	private String shoesSize;

	public String getClothesSize() {
		return clothesSize;
	}

	public void setClothesSize(String clothesSize) {
		this.clothesSize = clothesSize;
	}

	public String getShoesSize() {
		return shoesSize;
	}

	public void setShoesSize(String shoesSize) {
		this.shoesSize = shoesSize;
	}
}
