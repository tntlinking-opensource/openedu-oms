package com.yzsoft.yxxt.welcome.service.impl;

import com.yzsoft.yxxt.welcome.dto.BaseCount;
import com.yzsoft.yxxt.welcome.dto.DepartmentCount;
import com.yzsoft.yxxt.welcome.dto.MajorCount;
import com.yzsoft.yxxt.welcome.dto.NationCount;
import com.yzsoft.yxxt.welcome.service.WelcomeCountService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.MajorService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WelcomeCountServiceImpl implements WelcomeCountService {

	@Resource
	private EntityDao entityDao;
	@Resource
	private DepartmentService departmentService;
	@Resource
	private MajorService majorService;
	@Resource
	private ProcessBatchService processBatchService;
	@Resource
	protected PropertyConfigFactory configFactory;

	@Override
	public List<BaseCount> countByDepartment(ProcessBatch batch) {
		List<Object[]> datas = countByDeaprtment(batch);
		List<Object[]> studentNums = countStudentNumByDeaprtment(batch);

//		List<DepartmentCount> counts = new ArrayList<DepartmentCount>();
//		for (Object[] studentNum : studentNums) {
//			DepartmentCount count = new DepartmentCount();
//			count.setDepartmentName(department.getName());
//			count.setStudentNum(0L);
//			count.setPercent(0D);
//			for (Object[] oo : studentNums) {
//				if (department.getId().equals(oo[0])) {
//					count.setStudentNum((Long) oo[1]);
//					break;
//				}
//			}
//			for (Object[] oo : datas) {
//				if (department.getId().equals(oo[0])) {
//					count.setCompleteNum((Long) oo[1]);
//					if (count.getStudentNum() > 0) {
//						count.setPercent(count.getCompleteNum() * 1.0 / count.getStudentNum());
//					}
//					break;
//				}
//			}
//			counts.add(count);
//		}
//		return counts;
		return toBaseCount(studentNums, datas);
	}

	private List<Object[]> countByDeaprtment(ProcessBatch batch) {
		Integer mainCount = getMainCount(batch);
		String sql = "SELECT dep.name, COUNT(*)\n" +
				"FROM cp_students s\n" +
				"JOIN CP_DEPARTMENTS dep ON dep.id = s.department_id\n" +
				"JOIN (\n" +
				"    SELECT log.STUDENT_ID, COUNT(*) num\n" +
				"    FROM lc_process_link_item_logs LOG\n" +
				"    JOIN cp_students s ON s.id = log.STUDENT_ID\n" +
				"    JOIN lc_process_link_items item ON item.id = log.ITEM_ID\n" +
				"    WHERE log.BATCH_ID = ? AND item.MUST_PASSED = 1\n" +
				"    GROUP BY log.STUDENT_ID) t ON t.student_id = s.id\n" +
				"WHERE t.num >= ?\n" +
				"GROUP BY dep.name";
		List count = entityDao.searchSqlQuery(sql, batch.getId(), mainCount);
		return count;
	}

	private List<Object[]> countStudentNumByDeaprtment(ProcessBatch batch) {
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("department.name, count(*)");
		querySetting(query, batch);
		query.groupBy("department.name");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	private Integer getMainCount(ProcessBatch batch) {
		Integer count = 0;
		for (ProcessLinkItem item : batch.getProcess().getProcessLinkItems()) {
			if (item.isMustPassed()) {
				count++;
			}
		}
		return count;
	}

	public List<BaseCount> countByMajor(ProcessBatch batch) {
		List<Object[]> datas = countDataByMajor(batch);
//		List<MajorCount> counts = new ArrayList<MajorCount>();
//		for (Object[] oo : datas) {
//			MajorCount count = new MajorCount();
//			count.setName((String) oo[0]);
//			count.setCompleteNum(((Number) oo[1]).longValue());
//			counts.add(count);
//		}
//		Collections.sort(counts, new BeanComparator("majorName"));
//		Collections.sort(counts, ComparatorUtils.reversedComparator(new BeanComparator("completeNum")));
//		if (counts.size() > 5) {
//			counts = counts.subList(0, 5);
//		}
//		return counts;

		List<Object[]> studentNums = countStudentNumByMajor(batch);
		return toBaseCount(studentNums, datas);
	}

	private List<Object[]> countDataByMajor(ProcessBatch batch) {
		Integer mainCount = getMainCount(batch);
		String sql = "SELECT m.abbreviation, COUNT(*)\n" +
				"FROM cp_students s\n" +
				"join cp_majors m on m.id = s.major_id " +
				"JOIN (\n" +
				"    SELECT log.STUDENT_ID, COUNT(*) num\n" +
				"    FROM lc_process_link_item_logs LOG\n" +
				"    JOIN cp_students s ON s.id = log.STUDENT_ID\n" +
				"    JOIN lc_process_link_items item ON item.id = log.ITEM_ID\n" +
				"    WHERE log.BATCH_ID = ? AND item.MUST_PASSED = 1\n" +
				"    GROUP BY log.STUDENT_ID) t ON t.student_id = s.id\n" +
				"WHERE t.num >= ? " +
				"and m.abbreviation is not null " +
				"GROUP BY m.abbreviation";
		List count = entityDao.searchSqlQuery(sql, batch.getId(), mainCount);
		return count;
	}

	private List<Object[]> countStudentNumByMajor(ProcessBatch batch) {
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("major.abbreviation, count(*), major.department.name");
		querySetting(query, batch);
		query.where("major.abbreviation is not null");
		query.orderBy("major.department.name, major.abbreviation");
		query.groupBy("major.department.name, major.abbreviation");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	public List<NationCount> countByNation(ProcessBatch batch) {
		List<Object[]> datas = countDataByNation(batch);
		List<NationCount> counts = new ArrayList<NationCount>();
		for (Object[] oo : datas) {
			NationCount count = new NationCount();
			count.setNationName((String) oo[0]);
			count.setCompleteNum(((Number) oo[1]).longValue());
			counts.add(count);
		}
		Collections.sort(counts, ComparatorUtils.reversedComparator(new BeanComparator("completeNum")));
		if (counts.size() > 5) {
			counts = counts.subList(0, 5);
		}
		return counts;
	}

	private List<Object[]> countDataByNation(ProcessBatch batch) {
		Integer mainCount = getMainCount(batch);
		String sql = "SELECT NATION.name, COUNT(*)\n" +
				"FROM cp_students s\n" +
				"JOIN CP_STUDENT_INFOES si ON (si.STUDENT_ID = s.id or si.id = s.info_id)\n" +
				"JOIN SYS_DICT_DATAS nation ON nation.id = si.NATION_ID\n" +
				"JOIN (\n" +
				"    SELECT log.STUDENT_ID, COUNT(*) num\n" +
				"    FROM lc_process_link_item_logs LOG\n" +
				"    JOIN cp_students s ON s.id = log.STUDENT_ID\n" +
				"    JOIN lc_process_link_items item ON item.id = log.ITEM_ID\n" +
				"    WHERE log.BATCH_ID = ? AND item.MUST_PASSED = 1\n" +
				"    GROUP BY log.STUDENT_ID) t ON t.student_id = s.id\n" +
				"WHERE t.num >= ?\n" +
				"GROUP BY NATION.name";
		List count = entityDao.searchSqlQuery(sql, batch.getId(), mainCount);
		return count;
	}

	@Override
	public List<BaseCount> countByProvince(ProcessBatch batch) {
		List<Object[]> datas = countDataByProvince(batch);
		List<Object[]> studentNums = countStudentNumByProvince(batch);

		List<BaseCount> counts = toBaseCount(studentNums, datas);
		initPercent(batch, counts);
		return counts;
	}

	private void initPercent(ProcessBatch batch, List<BaseCount> counts) {

		Long completeNum = processBatchService.countCompleteNum(batch);
		if (completeNum != null && completeNum > 0) {
			for (BaseCount baseCount : counts) {
				if (baseCount.getCompleteNum() != null) {
					baseCount.setPercent(baseCount.getCompleteNum() * 1.0 / completeNum);
				}
			}
		}
	}

	private List<Object[]> countDataByProvince(ProcessBatch batch) {
		Integer mainCount = getMainCount(batch);
		String sql = "SELECT province.name, COUNT(*)\n" +
				"FROM cp_students s\n" +
				"JOIN SYS_DICT_DATAS province ON province.id = s.ENROLL_PROVINCE_ID\n" +
				"JOIN (\n" +
				"    SELECT log.STUDENT_ID, COUNT(*) num\n" +
				"    FROM lc_process_link_item_logs LOG\n" +
				"    JOIN cp_students s ON s.id = log.STUDENT_ID\n" +
				"    JOIN lc_process_link_items item ON item.id = log.ITEM_ID\n" +
				"    WHERE log.BATCH_ID = ? AND item.MUST_PASSED = 1\n" +
				"    GROUP BY log.STUDENT_ID) t ON t.student_id = s.id\n" +
				"WHERE t.num >= ?\n" +
				"GROUP BY province.name";
		List count = entityDao.searchSqlQuery(sql, batch.getId(), mainCount);
		return count;
	}

	private List<Object[]> countStudentNumByProvince(ProcessBatch batch) {
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("enrollProvince.name, count(*)");
		querySetting(query, batch);
		query.groupBy("enrollProvince.name");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	private void querySetting(OqlBuilder query, ProcessBatch batch) {
		query.where("grade = :grade", batch.getYear());
		query.where("education in (:educations)", batch.getEducations());
	}

	private List<BaseCount> toBaseCount(List<Object[]> studentNums, List<Object[]> datas) {
		List<BaseCount> counts = new ArrayList<BaseCount>();
		for (Object[] studentNum : studentNums) {
			BaseCount count = new BaseCount();
			count.setName((String) studentNum[0]);
			count.setCompleteNum(0L);
			count.setStudentNum((Long) studentNum[1]);
			if (studentNum.length == 3 && studentNum[2] != null) {
				count.setDepartment(studentNum[2].toString());
			}
			for (Object[] data : datas) {
				if (studentNum[0].equals(data[0])) {
					Long completeNum = null;
					if (data[1] instanceof BigDecimal) {
						completeNum = ((BigDecimal) data[1]).longValue();
					} else {
						completeNum = (Long) data[1];
					}
					count.setCompleteNum(completeNum);
					if (count.getStudentNum() > 0) {
						count.setPercent(count.getCompleteNum() * 1.0 / count.getStudentNum());
					}
					break;
				}
			}
			counts.add(count);
		}
		Collections.sort(counts, ComparatorUtils.reversedComparator(new BeanComparator("completeNum")));
		return counts;
	}

	@Override
	public List<BaseCount> countByCity(ProcessBatch batch) {
		List<Object[]> datas = countDataByCity(batch);
		List<Object[]> studentNums = countStudentNumByCity(batch);
		List<BaseCount> counts = toBaseCount(studentNums, datas);
		initPercent(batch, counts);
		return counts;
	}

	private List<Object[]> countStudentNumByCity(ProcessBatch batch) {
		//河南省
		String provinceCode = (String) configFactory.getConfig("yxxt.enroll.province.code", "DISTRICT_410000");
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("enrollCity.name, count(*)");
		querySetting(query, batch);
		query.where("enrollProvince.code = :provinceCode", provinceCode);
		query.groupBy("enrollCity.name");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	private List<Object[]> countDataByCity(ProcessBatch batch) {
		Integer mainCount = getMainCount(batch);
		String sql = "SELECT city.name, COUNT(*)\n" +
				"FROM cp_students s\n" +
				"JOIN SYS_DICT_DATAS city ON city.id = s.ENROLL_CITY_ID\n" +
				"JOIN (\n" +
				"    SELECT log.STUDENT_ID, COUNT(*) num\n" +
				"    FROM lc_process_link_item_logs LOG\n" +
				"    JOIN cp_students s ON s.id = log.STUDENT_ID\n" +
				"    JOIN lc_process_link_items item ON item.id = log.ITEM_ID\n" +
				"    WHERE log.BATCH_ID = ? AND item.MUST_PASSED = 1\n" +
				"    GROUP BY log.STUDENT_ID) t ON t.student_id = s.id\n" +
				"WHERE t.num >= ?\n" +
				"GROUP BY city.name";
		List count = entityDao.searchSqlQuery(sql, batch.getId(), mainCount);
		return count;
	}

}
