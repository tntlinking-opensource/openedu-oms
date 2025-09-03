package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.statistics.dto.CountData;
import com.yzsoft.yxxt.statistics.dto.CountItem;
import com.yzsoft.yxxt.statistics.service.StudentStatisticsService;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.TeachCalendar;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentStatisticsServiceImpl extends BaseStatisticsService implements StudentStatisticsService {

	@Override
	public CountData departmentCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_DEPARTMENTS m ON" +
				"    m.id = s.DEPARTMENT_ID" +
				" WHERE" +
				"    s.GRADE = ?" +
				"    AND s.REGISTED = 1" +
				" GROUP BY" +
				"    m.name";
		List<CountItem> items = query(sql, teachCalendar);
		return new CountData(items);
	}

	@Override
	public CountData departmentHistory() {
		String sql = "SELECT" +
				"    s.grade YEAR," +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_DEPARTMENTS m ON" +
				"    m.id = s.DEPARTMENT_ID" +
				" WHERE" +
				"    s.REGISTED = 1" +
				" GROUP BY" +
				"    s.grade," +
				"    m.name";
		List<CountItem> items = query(sql);
		return new CountData(items);
	}

	@Override
	public CountData majorCurrent() {
		return majorCurrent(null);
	}

	@Override
	public CountData majorCurrent(Department department) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_MAJORS m ON" +
				"    m.id = s.MAJOR_ID" +
				" WHERE" +
				"    s.GRADE = ?" +
				"    AND s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    m.NAME";
		List<CountItem> items = query(sql, teachCalendar, department);
		return new CountData(items);
	}

	@Override
	public CountData majorHistory() {
		return majorHistory(null);
	}


	@Override
	public CountData majorHistory(Department department) {
		String sql = "SELECT" +
				"    s.GRADE YEAR," +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_MAJORS m ON" +
				"    m.id = s.MAJOR_ID" +
				" WHERE" +
				"    s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    s.GRADE," +
				"    m.NAME";
		List<CountItem> items = query(sql, department);
		return new CountData(items);
	}

	@Override
	public CountData nationCurrent() {
		return nationCurrent(null);
	}

	@Override
	public CountData nationCurrent(Department department) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_STUDENT_INFOES si ON" +
				"    si.id = s.INFO_ID" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = si.NATION_ID" +
				" WHERE" +
				"    s.GRADE = ?" +
				"    AND s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    m.NAME";
		List<CountItem> items = query(sql, teachCalendar, department);
		return new CountData(items);
	}

	@Override
	public CountData nationHistory() {
		return nationHistory(null);
	}

	@Override
	public CountData nationHistory(Department department) {
		String sql = "SELECT" +
				"    s.GRADE YEAR," +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN CP_STUDENT_INFOES si ON" +
				"    si.id = s.INFO_ID" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = si.NATION_ID" +
				" WHERE" +
				"    s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    s.GRADE," +
				"    m.NAME";
		List<CountItem> items = query(sql, department);
		return new CountData(items);
	}

	@Override
	public CountData cityCurrent() {
		return cityCurrent(null);
	}

	@Override
	public CountData cityCurrent(Department department) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = s.ENROLL_CITY_ID" +
				" WHERE" +
				"    s.GRADE = ?" +
				"    AND s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    m.NAME";
		List<CountItem> items = query(sql, teachCalendar, department);
		return new CountData(items);
	}

	@Override
	public CountData cityHistory() {
		return cityHistory(null);
	}

	@Override
	public CountData cityHistory(Department department) {
		String sql = "SELECT" +
				"    s.GRADE YEAR," +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = s.ENROLL_CITY_ID" +
				" WHERE" +
				"    s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    s.GRADE," +
				"    m.NAME";
		List<CountItem> items = query(sql, department);
		return new CountData(items);
	}

	@Override
	public CountData genderCurrent() {
		return genderCurrent(null);
	}

	@Override
	public CountData genderCurrent(Department department) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = s.GENDER_ID" +
				" WHERE" +
				"    s.GRADE = ?" +
				"    AND s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    m.NAME";
		List<CountItem> items = query(sql, teachCalendar, department);
		return new CountData(items);
	}

	@Override
	public CountData genderHistory() {
		return genderHistory(null);
	}

	@Override
	public CountData genderHistory(Department department) {
		String sql = "SELECT" +
				"    s.GRADE YEAR," +
				"    m.NAME name," +
				"    COUNT(*) num" +
				" FROM" +
				"    CP_STUDENTS s" +
				" JOIN SYS_DICT_DATAS m ON" +
				"    m.id = s.GENDER_ID" +
				" WHERE" +
				"    s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" GROUP BY" +
				"    s.GRADE," +
				"    m.NAME";
		List<CountItem> items = query(sql, department);
		return new CountData(items);
	}

	@Override
	public CountData dormCurrent() {
		return dormCurrent(null);
	}

	@Override
	public CountData dormCurrent(Department department) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    m.name," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        s.code," +
				"        CASE" +
				"            WHEN ds.ID IS NOT NULL THEN '已安排住宿'" +
				"            ELSE '未安排住宿'" +
				"        END name" +
				"    FROM" +
				"        CP_STUDENTS s" +
				"    LEFT JOIN SS_DORM_STUDENTS ds ON" +
				"       ds.STUDENT_ID = s.ID" +
				"    WHERE" +
				"        s.GRADE = ?" +
				"        AND s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				" ) m" +
				" GROUP BY" +
				"    m.NAME";
		List<CountItem> items = query(sql, teachCalendar, department);
		return new CountData(items);
	}

	@Override
	public CountData dormHistory() {
		return dormHistory(null);
	}

	@Override
	public CountData dormHistory(Department department) {
		String sql = "SELECT" +
				"    m.grade YEAR," +
				"    m.name," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        s.GRADE," +
				"        s.code," +
				"        CASE" +
				"            WHEN ds.ID IS NOT NULL THEN '已安排住宿'" +
				"            ELSE '未安排住宿'" +
				"        END name" +
				"    FROM" +
				"        CP_STUDENTS s" +
				"    LEFT JOIN SS_DORM_STUDENTS ds ON" +
				"       ds.STUDENT_ID = s.ID" +
				"    WHERE" +
				"        s.REGISTED = 1" +
				(department != null ? " AND s.DEPARTMENT_ID = ?" : "") +
				") m" +
				" GROUP BY" +
				"    m.grade," +
				"    m.NAME";
		List<CountItem> items = query(sql, department);
		return new CountData(items);
	}

}
