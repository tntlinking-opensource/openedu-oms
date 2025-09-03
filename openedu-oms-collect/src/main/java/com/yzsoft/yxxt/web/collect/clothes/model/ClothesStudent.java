package com.yzsoft.yxxt.web.collect.clothes.model;

import org.beangle.product.core.model.StudentObject;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class ClothesStudent extends StudentObject {

	@Column(length = 900)
	private String remark;

	//尺码
	@OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ClothesStudentSize> sizes = new ArrayList<ClothesStudentSize>();

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ClothesStudentSize> getSizes() {
		return sizes;
	}

	public void setSizes(List<ClothesStudentSize> sizes) {
		this.sizes = sizes;
	}
}
