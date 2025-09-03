package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.statistics.service.ConstellationUtil;
import com.yzsoft.yxxt.statistics.service.YxStatisticsService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class
YxStatisticsServiceImpl extends EntityDaoSupport implements YxStatisticsService {

	@Autowired
	private YxxtService yxxtService;
	/**
	 * 和我同名的学生数量统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countName(Student student) {
		String grade = student.getGrade();
		String name = student.getName();
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and s.id <> ?" +
				" and s.name = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, student.getId(), name);
	}

	/**
	 * 学校重名最高的前5个名字
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<String> countName5(Student student) {
		String grade = student.getGrade();
		String sql = "select name" +
				"  from (select name, rownum num" +
				"          from (select s.name" +
				"                  from cp_students s" +
				"                 where s.grade = ?" +
				"                 group by s.name" +
				"                 order by count(*) desc))" +
				" where num <= 5";
		return (List<String>) entityDao.searchSqlQuery(sql, grade);
	}

	/**
	 * 和我同一个学院的学生统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countGenderByDepartment(Student student) {
		if (student.getDepartment() == null) {
			return new ArrayList<Object[]>();
		}
		String grade = student.getGrade();
		Long departmentId = student.getDepartment().getId();
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and s.department_id = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, departmentId);
	}

	/**
	 * 和我同一个专业的学生统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countGenderByMajor(Student student) {
		if (student.getMajor() == null) {
			return new ArrayList<Object[]>();
		}
		String grade = student.getGrade();
		Long majorId = student.getMajor().getId();
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and s.major_id = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, majorId);
	}

	/**
	 * 和我同一天生日学生统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countBirthday(Student student) {
		String grade = student.getGrade();
		String birthday = student.getCardcode().substring(10, 14);
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and substr(s.cardcode, 11, 4) = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, birthday);
	}

	/**
	 * 和我同一个星座的数量统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countConstellation(Student student) {
		String grade = student.getGrade();
		String birthday = student.getCardcode().substring(10, 14);
		String constellation = ConstellationUtil.getConstellationByBirthday(birthday);
		String[] startAndEnd = ConstellationUtil.getStartAndEndByConstellation(constellation);
		if (startAndEnd == null) {
			return new ArrayList<Object[]>();
		}
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and (substr(s.cardcode, 11, 4) >= ?" +
				("摩羯座".equals(constellation) ? " or" : " and") +
				" substr(s.cardcode, 11, 4) <= ?)" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, startAndEnd[0], startAndEnd[1]);
	}

	@Override
	public String getConstellationTop(Student student) {
		String grade = student.getGrade();
		updateConstellationByGrade(grade);
		String sql = "   select name" +
				"          from (select constellation name, rownum num" +
				"                  from (select s.constellation" +
				"                          from cp_students s" +
				"                         where s.grade = ?" +
				"                         group by s.constellation" +
				"                         order by count(*) desc))" +
				"         where num <= 1";
		List<String> names = (List<String>) entityDao.searchSqlQuery(sql, grade);
		return names.isEmpty() ? null : names.get(0);
	}

	@Override
	public List<Object[]> countConstellationTop(Student student, String constellation) {
		String grade = student.getGrade();
		String sql = "select g.name, count(*) n" +
				"  from cp_students s" +
				"  join sys_dict_datas g" +
				"    on g.id = s.gender_id" +
				" where s.grade = ?" +
				" and s.constellation = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, constellation);
	}

	private void updateConstellationByGrade(String grade) {
		String sql = "update cp_students" +
				"   set constellation = get_constellation_by_card_code(cardcode)" +
				" where constellation is null" +
				"   and cardcode is not null" +
				"   and grade = ?";
		entityDao.executeUpdateSql(sql, grade);
	}

	/**
	 * 和我同一个民族的数量统计
	 *
	 * @param student
	 * @return
	 */
	@Override
	public List<Object[]> countNation(Student student) {
		String grade = student.getGrade();
		String nation = student.getInfo().getNation().getName();
		String sql = "select g.name, count(*)" +
				" from cp_students s" +
				" join cp_student_infoes si on si.id = s.info_id" +
				" join sys_dict_datas g on g.id = s.gender_id" +
				" join sys_dict_datas n on n.id = si.nation_id" +
				" where s.grade = ?" +
				" and n.name = ?" +
				" group by g.name";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, grade, nation);
	}

	@Override
	public Long countEnrollRank(Student student, ProcessBatch batch) {
		ProcessLinkItem enrollItem = getEnrollItem(batch);
		if (enrollItem == null) {
			return null;
		}
		String sql = "select num from (select s.student_id, rownum num" +
				" from lc_process_link_item_logs s" +
				" where s.item_id = ?" +
				" order by s.create_time)" +
				" where student_id = ?";
		List<BigDecimal> datas = (List<BigDecimal>) entityDao.searchSqlQuery(sql, enrollItem.getId(), student.getId());
		return datas.isEmpty() ? null : datas.get(0).longValue();
	}

	private ProcessLinkItem getEnrollItem(ProcessBatch batch) {
		OqlBuilder query = OqlBuilder.from(ProcessLinkItem.class, "o");
		query.where("o.keyLinked = true");
		query.where("o.process = :process", batch.getProcess());
		query.cacheable();
		List<ProcessLinkItem> items = entityDao.search(query);
		return items.isEmpty() ? null : items.get(0);
	}

	@Override
	public List<Student> findStudentByName(String gender, PageLimit pageLimit) {
		Student student = yxxtService.getStudent();
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.where("o.gender.name = :gender", gender);
		query.where("o.grade = :grade", student.getGrade());
		query.where("o.name = :name", student.getName());
		query.where("o.id <> :id", student.getId());
		query.limit(pageLimit);
		return entityDao.search(query);
	}

	@Override
	public List<Student> findStudentByDepartment(String gender, String department, PageLimit pageLimit) {
		Student student = yxxtService.getStudent();
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.where("o.gender.name = :gender", gender);
		query.where("o.grade = :grade", student.getGrade());
		query.where("o.department.name = :department", department);
		query.limit(pageLimit);
		return entityDao.search(query);
	}

	@Override
	public List<Student> findStudentByMajor(String gender, String major, PageLimit pageLimit) {
		Student student = yxxtService.getStudent();
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.where("o.gender.name = :gender", gender);
		query.where("o.grade = :grade", student.getGrade());
		query.where("o.major.name = :major", major);
		query.limit(pageLimit);
		return entityDao.search(query);
	}

	@Override
	public List<Student> findStudentByBirthday(String gender, String birthday, PageLimit pageLimit) {
		Student student = yxxtService.getStudent();
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.where("o.gender.name = :gender", gender);
		query.where("o.grade = :grade", student.getGrade());
		query.where("substr(o.cardcode, 11, 4) = :birthday", birthday);
		query.limit(pageLimit);
		return entityDao.search(query);
	}

}
