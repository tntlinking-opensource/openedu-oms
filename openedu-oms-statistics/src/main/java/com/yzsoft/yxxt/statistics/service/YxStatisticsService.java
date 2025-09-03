package com.yzsoft.yxxt.statistics.service;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;

import java.util.List;

public interface YxStatisticsService {

	/**
	 * 和我同名的学生数量统计
	 *
	 * @param student
	 * @return
	 */
	List<Object[]> countName(Student student);

	/**
	 * 学校重名最高的前5个名字
	 *
	 * @param student
	 * @return
	 */
	List<String> countName5(Student student);

	/**
	 * 和我同一个学院的学生统计
	 *
	 * @param student
	 * @return
	 */
	List<Object[]> countGenderByDepartment(Student student);

	/**
	 * 和我同一个专业的学生统计
	 *
	 * @param student
	 * @return
	 */
	List<Object[]> countGenderByMajor(Student student);

	/**
	 * 和我同一天生日学生统计
	 *
	 * @param student
	 * @return
	 */
	List<Object[]> countBirthday(Student student);

	/**
	 * 和我同一个星座的数量统计
	 *
	 * @param student
	 * @return
	 */
	List<Object[]> countConstellation(Student student);


	String getConstellationTop(Student student);

	/**
	 * 学校同一个星座最多的数量统计
	 * @param student
	 * @param constellation
	 * @return
	 */
	List<Object[]> countConstellationTop(Student student, String constellation);

	/**
	 * 和我同一个民族的数量统计
	 * @param student
	 * @return
	 */
	List<Object[]> countNation(Student student);

	/**
	 * 我的报道顺序排名
	 *
	 * @param student
	 * @param batch
	 * @return
	 */
	Long countEnrollRank(Student student, ProcessBatch batch);

	List<Student> findStudentByName(String gender, PageLimit pageLimit);

	List<Student> findStudentByDepartment(String gender, String department, PageLimit pageLimit);

	List<Student> findStudentByMajor(String gender, String major, PageLimit pageLimit);

	List<Student> findStudentByBirthday(String gender, String birthday, PageLimit pageLimit);
}
