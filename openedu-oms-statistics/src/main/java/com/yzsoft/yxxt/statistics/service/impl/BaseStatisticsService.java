package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.statistics.dto.CountItem;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.service.TeachCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class BaseStatisticsService extends EntityDaoSupport {

	@Autowired
	protected TeachCalendarService teachCalendarService;
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public Long getStudentNum(String grade) {
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("count(*)");
		query.where("registed = true");
		query.where("grade = :grade", grade);
		List<Long> datas = entityDao.search(query);
		return datas.get(0);
	}

	protected List<Object[]> findStudentNum() {
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.select("o.grade, count(*)");
		query.where("o.registed = true");
		query.groupBy("o.grade");
		return entityDao.search(query);
	}

	protected void addOtherItem(List<CountItem> items, String name) {
		List<Object[]> studentNums = findStudentNum();
		for (Object[] studentNum : studentNums) {
			Long num = ((Number) studentNum[1]).longValue();
			for (CountItem item : items) {
				if (item.getName().equals(studentNum[0])) {
					num -= item.getNum();
				}
			}
			items.add(new CountItem((String) studentNum[0], name, num));
		}
	}

	protected List<CountItem> query(String sql) {
		return jdbcTemplate.query(sql, new BeanPropertyRowMapper(CountItem.class));
	}

	protected List<CountItem> query(String sql, TeachCalendar teachCalendar) {
		Object[] args = new Object[]{teachCalendar.getYear()};
		return query(sql, args);
	}

	protected List<CountItem> query(String sql, Department department) {
		if (department == null) {
			return query(sql);
		}
		return query(sql, new Object[]{department.getId()});
	}

	protected List<CountItem> query(String sql, TeachCalendar teachCalendar, Department department) {
		if (department == null) {
			return query(sql, new Object[]{teachCalendar.getYear()});
		}
		Object[] args = new Object[]{teachCalendar.getYear(), department.getId()};
		return query(sql, args);
	}

	protected List<CountItem> query(String sql, Object[] args) {
		return jdbcTemplate.query(sql, args,
				new BeanPropertyRowMapper(CountItem.class));
	}
}
