package org.beangle.product.core.model;

import java.util.List;

import javax.persistence.*;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.website.system.model.DictData;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;

/**
 * 班级
 * 
 * @作者：周建明
 * @创建日期：2016年9月13日 下午5:09:00
 */
@Entity
@Cacheable
@Cache(region = "product.core", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class AdminClass extends LongIdObject{

	private static final long serialVersionUID = -7753680080116858524L;

	/**
	 * 班级代码
	 */
    @Column(unique = true,length = 32)
	private String code;
    
    /** 
	 * 班级名称
	 */
	@Column(length = 100)
	private String name;

	/** 
	 * 年级
	 */
	@Column(length = 30)
	private String grade;
	
	/**
	 * 学院
	 */
	private Department department;
	
	/**
	 * 所属专业
	 */
	private Major major;

	/**
	 * 专业方向
	 */
	private Direction direction;
	
	/**
	 * 辅导员
	 */
	private Teacher instructor;
	
	/** 辅导员 */
	@ManyToMany
	private List<Teacher> instructors = CollectUtils.newArrayList();
	
	/** 招生类型 */
	@ManyToMany
	private List<DictData> enrolls = CollectUtils.newArrayList();
	
	/**
	 * 班级人数
	 */
	private int num = 0;
	
	/**
	 * 班级学生数
	 */
	@Formula("(select count(*) from cp_students t where t.admin_class_id = id)")
	private Integer stdNum = 0;
	
	/** 
	 * 是否可用
	 */
	private boolean enabled = true;

	/**
	 * 无用字段
	 */
	@Transient
	private Student student;

	
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public Major getMajor() {
		return major;
	}

	public void setMajor(Major major) {
		this.major = major;
	}

	public Teacher getInstructor() {
		return instructor;
	}

	public void setInstructor(Teacher instructor) {
		this.instructor = instructor;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getEnabledStr(){
		return this.enabled ? "是" : "否";
	}

	public List<Teacher> getInstructors() {
		return instructors;
	}

	public void setInstructors(List<Teacher> instructors) {
		this.instructors = instructors;
	}

	public Direction getDirection() {
		return direction;
	}

	public void setDirection(Direction direction) {
		this.direction = direction;
	}

	public List<DictData> getEnrolls() {
		return enrolls;
	}

	public void setEnrolls(List<DictData> enrolls) {
		this.enrolls = enrolls;
	}
	
	public String getEnrollName(){
		StringBuffer b = new StringBuffer();
		for(DictData d : this.enrolls){
			b.append(d.getName() + " ");
		}
		return b.toString();
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
	
	public Integer getStdNum() {
		return stdNum;
	}

	public void setStdNum(Integer stdNum) {
		this.stdNum = stdNum;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}
}
