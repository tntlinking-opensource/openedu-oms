package com.yzsoft.yxxt.web.collect.clothes.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ClothesStudentSize extends LongIdObject {
	//学生
	private ClothesStudent student;
	//服装类别
	private ClothesType type;
	//服装尺码
	private ClothesSize size;

	public ClothesStudent getStudent() {
		return student;
	}

	public void setStudent(ClothesStudent student) {
		this.student = student;
	}

	public ClothesType getType() {
		return type;
	}

	public void setType(ClothesType type) {
		this.type = type;
	}

	public ClothesSize getSize() {
		return size;
	}

	public void setSize(ClothesSize size) {
		this.size = size;
	}
}
