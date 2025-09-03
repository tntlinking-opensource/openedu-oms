package com.yzsoft.yxxt.prepare.model;

import org.beangle.model.pojo.LongIdObject;

import javax.persistence.Entity;
import java.util.List;

@Entity(name = "com.yzsoft.yxxt.prepare.model.AdminClassRule")
public class AdminClassRule extends LongIdObject{

	public static final String CODE = "CODE";
	public static final String CODE_1 = "CODE_1";
	public static final String NAME = "NAME";
	public static final String NAME_1 = "NAME_1";
	public static final String NAME_2 = "NAME_2";
	public static final String GENDER = "GENDER";
	public static final String GENDER_1 = "GENDER_1";
	public static final String SCORE = "SCORE";
	public static final String SCORE_1 = "SCORE_1";
	public static final String STUDENT_CODE = "STUDENT_CODE";
	public static final String STUDENT_CODE_1 = "STUDENT_CODE_1";

	private String code;
	private String name;
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public static AdminClassRule getRule(List<AdminClassRule> rules, String code) {
		if(rules == null){
			return null;
		}
		for (AdminClassRule rule : rules) {
			if (code.equals(rule.getType())) {
				return rule;
			}
		}
		return null;
	}
}
