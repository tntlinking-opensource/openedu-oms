package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.statistics.dto.CountData;
import com.yzsoft.yxxt.statistics.dto.CountItem;
import com.yzsoft.yxxt.statistics.service.DormStatisticsService;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.service.TeachCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DormStatisticsServiceImpl extends BaseStatisticsService implements DormStatisticsService {

	@Autowired
	private TeachCalendarService teachCalendarService;

	@Override
	public CountData studentCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();

		Long studentNum = getStudentNum(teachCalendar.getYear());
		String sql = "SELECT" +
				"    COUNT(*)" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        s.id" +
				"    FROM" +
				"        SS_DORM_BED_LOGS LOG" +
				"    JOIN CP_STUDENTS s ON" +
				"        s.id = log.STUDENT_ID" +
				"    WHERE" +
				"        s.grade = ?" +
				"    GROUP BY" +
				"        s.id ) t";
		Long bedNum = jdbcTemplate.queryForObject(sql, new Object[]{teachCalendar.getYear()}, Long.class);
		List<CountItem> items = new ArrayList<CountItem>();
		items.add(new CountItem("已安排住宿", bedNum));
		items.add(new CountItem("未安排住宿", studentNum - bedNum));
		return new CountData(items);
	}

	@Override
	public CountData studentHistory() {
		String sql = "SELECT" +
				"    s.grade year," +
				"    '已安排住宿' name," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        s.grade," +
				"        s.id" +
				"    FROM" +
				"        SS_DORM_BED_LOGS LOG" +
				"    JOIN CP_STUDENTS s ON" +
				"        s.id = log.STUDENT_ID" +
				"    GROUP BY" +
				"        s.grade," +
				"        s.id ) s" +
				" GROUP BY" +
				"    s.grade";
		List<CountItem> items = query(sql);
		addOtherItem(items, "未安排住宿");
		return new CountData(items);
	}

	@Override
	public CountData roomCurrent() {
		String sql = "SELECT" +
				"    t.name," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        id," +
				"        CASE" +
				"            WHEN t.ROOM_ID IS NULL THEN '空闲'" +
				"            ELSE '有人'" +
				"        END AS name" +
				"    FROM" +
				"        SS_DORM_ROOMS r" +
				"    LEFT JOIN (" +
				"        SELECT" +
				"            ROOM_ID" +
				"        FROM" +
				"            SS_DORM_BEDS" +
				"        WHERE " +
				"            STUDENT_ID IS NOT NULL" +
				"        GROUP BY" +
				"            ROOM_ID ) t ON" +
				"        t.ROOM_ID = r.id) t" +
				" GROUP BY" +
				"    t.name";
		List<CountItem> items = query(sql);
		return new CountData(items);
	}

	@Override
	public CountData roomHistory() {
		return null;
	}

	@Override
	public CountData bedCurrent() {
		String sql = "SELECT" +
				"    t.name," +
				"    COUNT(*) num" +
				" FROM" +
				"    (" +
				"    SELECT" +
				"        id," +
				"        CASE" +
				"            WHEN STUDENT_ID IS NULL THEN '空闲'" +
				"            ELSE '有人'" +
				"        END AS name" +
				"    FROM" +
				"        SS_DORM_BEDS b) t" +
				" GROUP BY" +
				"    t.name";
		List<CountItem> items = query(sql);
		return new CountData(items);
	}

	@Override
	public CountData bedHistory() {
		return null;
	}

}
