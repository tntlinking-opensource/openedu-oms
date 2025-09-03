package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.statistics.dto.*;
import com.yzsoft.yxxt.statistics.service.ItemLogStatusCountService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.process.model.ProcessBatch;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class ItemLogStatusCountServiceImpl extends EntityDaoSupport implements ItemLogStatusCountService {
	@Override
	public List<ItemLogStatusCountItem> countItem(ProcessBatch batch) {
		String sql = "SELECT" +
				"    name," +
				"    gender," +
				"    ENABLED," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        li.name," +
				"        GENDER.name gender," +
				"        CASE" +
				"            WHEN lil.ENABLED IS NULL THEN 0" +
				"            ELSE lil.ENABLED" +
				"        END ENABLED" +
				"    FROM" +
				"        lc_item_log_statuses ils" +
				"    JOIN lc_process_link_items li ON" +
				"        li.id = ils.ITEM_ID" +
				"    JOIN CP_STUDENTS s ON" +
				"        s.id = ils.STUDENT_ID" +
				"    JOIN SYS_DICT_DATAS gender ON" +
				"        GENDER.id = s.GENDER_ID" +
				"    LEFT JOIN lc_process_link_item_logs lil ON" +
				"        ils.log_id = lil.id" +
				"    WHERE" +
				"        ils.BATCH_ID = ?)" +
				" GROUP BY" +
				"    name," +
				"    gender," +
				"    enabled" +
				" ORDER BY" +
				"    name," +
				"    gender," +
				"    enabled";
		List<Object[]> datas = (List<Object[]>) entityDao.searchSqlQuery(sql, batch.getId());
		List<ItemLogStatusCountItem> items = new ArrayList<ItemLogStatusCountItem>();
		for (Object[] data : datas) {
			ItemLogStatusCountItem item = getItem(items, (String) data[0], ItemLogStatusCountItem.class);
			Long num = ((Number) data[3]).longValue();
			if (((Number) data[2]).intValue() == 1) {
				if ("男".equalsIgnoreCase((String) data[1])) {
					item.setEnabledMaleNum(num);
				} else {
					item.setEnabledFemaleNum(num);
				}
				item.setEnabledNum(item.getEnabledNum() + num);
			} else {
				if ("男".equalsIgnoreCase((String) data[1])) {
					item.setDisabledMaleNum(num);
				} else {
					item.setDisabledFemaleNum(num);
				}
				item.setDisabledNum(item.getDisabledNum() + num);
			}
		}
		return items;
	}

	private <T extends ItemLogStatusCountParent> T getItem(List<T> items, String itemName, Class<T> clazz) {
		for (T item : items) {
			if (item.getName().equalsIgnoreCase(itemName)) {
				return item;
			}
		}
		T item = null;
		try {
			item = clazz.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		item.setName(itemName);
		items.add(item);
		return item;
	}

	@Override
	public List<ItemLogStatusCountDepartment> countMajor(ProcessBatch batch) {
		String sql = "SELECT\n" +
				"    department,\n" +
				"    major,\n" +
				"    name,\n" +
				"    gender,\n" +
				"    ENABLED,\n" +
				"    COUNT(*) num\n" +
				"FROM\n" +
				"    (\n" +
				"    SELECT\n" +
				"        li.name,\n" +
				"        d.NAME department,\n" +
				"        m.NAME major,\n" +
				"        GENDER.name gender,\n" +
				"        CASE\n" +
				"            WHEN lil.ENABLED IS NULL THEN 0\n" +
				"            ELSE lil.ENABLED\n" +
				"        END ENABLED\n" +
				"    FROM\n" +
				"        lc_item_log_statuses ils\n" +
				"    JOIN lc_process_link_items li ON\n" +
				"        li.id = ils.ITEM_ID\n" +
				"    JOIN CP_STUDENTS s ON\n" +
				"        s.id = ils.STUDENT_ID\n" +
				"    JOIN SYS_DICT_DATAS gender ON\n" +
				"        GENDER.id = s.GENDER_ID\n" +
				"    JOIN CP_MAJORS m ON\n" +
				"        m.id = s.MAJOR_ID\n" +
				"    JOIN CP_DEPARTMENTS d ON\n" +
				"        d.id = m.DEPARTMENT_ID\n" +
				"    LEFT JOIN lc_process_link_item_logs lil ON\n" +
				"        ils.log_id = lil.id\n" +
				"    WHERE\n" +
				"        ils.BATCH_ID = ?)\n" +
				"GROUP BY\n" +
				"    department,\n" +
				"    major,\n" +
				"    name,\n" +
				"    gender,\n" +
				"    enabled\n" +
				"ORDER BY\n" +
				"    department,\n" +
				"    major,\n" +
				"    name,\n" +
				"    gender,\n" +
				"    enabled";
		List<Object[]> datas = (List<Object[]>) entityDao.searchSqlQuery(sql, batch.getId());
		List<ItemLogStatusCountDepartment> items = new ArrayList<ItemLogStatusCountDepartment>();
		for (Object[] data : datas) {
			ItemLogStatusCountDepartment department = getItem(items, (String) data[0], ItemLogStatusCountDepartment.class);
			ItemLogStatusCountMajor major = getItem(department.getMajors(), (String) data[1], ItemLogStatusCountMajor.class);
			ItemLogStatusCountItem item = getItem(major.getItems(), (String) data[2], ItemLogStatusCountItem.class);
			Long num = ((Number) data[5]).longValue();
			if (((Number) data[4]).intValue() == 1) {
				if ("男".equalsIgnoreCase((String) data[3])) {
					item.setEnabledMaleNum(num);
				} else {
					item.setEnabledFemaleNum(num);
				}
				item.setEnabledNum(item.getEnabledNum() + num);
			} else {
				if ("男".equalsIgnoreCase((String) data[3])) {
					item.setDisabledMaleNum(num);
				} else {
					item.setDisabledFemaleNum(num);
				}
				item.setDisabledNum(item.getDisabledNum() + num);
			}
		}
		countMajorStudentNum(items, batch);
		return items;
	}

	private void countMajorStudentNum(List<ItemLogStatusCountDepartment> items, ProcessBatch batch) {
		String sql = "SELECT\n" +
				"    department,\n" +
				"    major,\n" +
				"    gender,\n" +
				"    COUNT(*) num\n" +
				"FROM\n" +
				"    (\n" +
				"    SELECT\n" +
				"        DEPARTMENT.name DEPARTMENT,\n" +
				"        MAJOR.name major,\n" +
				"        GENDER.name gender\n" +
				"    FROM\n" +
				"        lc_process_batches b\n" +
				"    JOIN lc_process_batches_educations be ON\n" +
				"        be.PROCESS_BATCH_ID = b.id\n" +
				"    JOIN CP_STUDENTS s ON\n" +
				"        s.EDUCATION_ID = be.DICT_DATA_ID\n" +
				"        AND s.GRADE = b.\"YEAR\"\n" +
				"    JOIN SYS_DICT_DATAS gender ON\n" +
				"        GENDER.id = s.GENDER_ID\n" +
				"    JOIN CP_MAJORS major ON\n" +
				"        MAJOR.id = s.MAJOR_ID\n" +
				"    JOIN CP_DEPARTMENTS department ON\n" +
				"        DEPARTMENT.id = MAJOR.DEPARTMENT_ID\n" +
				"    WHERE\n" +
				"        b.id = ?)\n" +
				"GROUP BY\n" +
				"    department,\n" +
				"    major,\n" +
				"    gender\n" +
				"ORDER BY\n" +
				"    department,\n" +
				"    major,\n" +
				"    gender";
		List<Object[]> datas = (List<Object[]>) entityDao.searchSqlQuery(sql, batch.getId());
		for (Object[] data : datas) {
			ItemLogStatusCountDepartment department = getItem(items, (String) data[0], ItemLogStatusCountDepartment.class);
			ItemLogStatusCountMajor major = getItem(department.getMajors(), (String) data[1], ItemLogStatusCountMajor.class);
			Long num = ((Number) data[3]).longValue();
			if ("男".equalsIgnoreCase((String) data[2])) {
				System.out.println(data[1] + ":" + num);
				major.setMaleNum(major.getMaleNum() + num);
			} else {
				major.setFemaleNum(major.getFemaleNum() + num);
			}
		}
	}

	@Override
	public List<ItemLogStatusCountProvince> countProvince(ProcessBatch batch) {
		String sql = "SELECT\n" +
				"    province,\n" +
				"    gender,\n" +
				"    COUNT(*) num\n" +
				"FROM\n" +
				"    (\n" +
				"    SELECT\n" +
				"        CASE\n" +
				"            WHEN province.name IS NULL THEN '未知'\n" +
				"            ELSE province.name\n" +
				"        END province,\n" +
				"        gender.name gender\n" +
				"    FROM\n" +
				"        lc_process_batches b\n" +
				"    JOIN lc_process_batches_educations be ON\n" +
				"        be.PROCESS_BATCH_ID = b.id\n" +
				"    JOIN CP_STUDENTS s ON\n" +
				"        s.EDUCATION_ID = be.DICT_DATA_ID\n" +
				"        AND s.GRADE = b.\"YEAR\"\n" +
				"    JOIN SYS_DICT_DATAS gender ON\n" +
				"        GENDER.id = s.GENDER_ID\n" +
				"    LEFT JOIN SYS_DICT_DATAS province ON\n" +
				"        province.id = s.ENROLL_PROVINCE_ID\n" +
				"    WHERE\n" +
				"        b.id = ?)\n" +
				"GROUP BY\n" +
				"    province,\n" +
				"    gender\n" +
				"ORDER BY\n" +
				"    province,\n" +
				"    gender";
		List<Object[]> datas = (List<Object[]>) entityDao.searchSqlQuery(sql, batch.getId());
		List<ItemLogStatusCountProvince> items = new ArrayList<ItemLogStatusCountProvince>();
		for (Object[] data : datas) {
			ItemLogStatusCountProvince item = getItem(items, (String) data[0], ItemLogStatusCountProvince.class);
			Long num = ((Number) data[2]).longValue();

			if ("男".equalsIgnoreCase((String) data[1])) {
				item.setMaleNum(num);
			} else {
				item.setFemaleNum(num);
			}
			item.setNum(item.getNum() + num);
		}
		Collections.sort(items, new BeanComparator("num"));
		return items;
	}
}
