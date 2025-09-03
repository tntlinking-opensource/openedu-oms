package com.yzsoft.yxxt.statistics.service.impl;

import com.yzsoft.yxxt.finance.model.FinanceGreenStudent;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudentItem;
import com.yzsoft.yxxt.statistics.service.FinanceGreenStatisticsService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.service.TeachCalendarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceGreenStatisticsServiceImpl extends BaseStatisticsService implements FinanceGreenStatisticsService {

	@Autowired
	private TeachCalendarService teachCalendarService;

	@Override
	public List<Object[]> applyNumCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		List<Object[]> datas = new ArrayList<Object[]>();
		Long studentNum = getStudentNum(teachCalendar.getYear());
		Long applyNum = getApplyNum(teachCalendar.getYear());
		datas.add(new Object[]{"申请人数", applyNum});
		datas.add(new Object[]{"其它", studentNum - applyNum});
		return datas;
	}

	@Override
	public List<Object[]> applyNumHistory() {
		List<Object[]> studentNums = findStudentNum();

		List<Object[]> applyNums = findApplyNum();

		List<Object[]> datas = new ArrayList<Object[]>();

		for (Object[] studentNum : studentNums) {
			Object[] data = new Object[]{"申请人数", studentNum[0], 0};
			for (Object[] applyNum : applyNums) {
				if (applyNum[0].equals(studentNum[0])) {
					data[2] = applyNum[1];
				}
			}
			datas.add(data);
			long num = ((Number) studentNum[1]).longValue() - ((Number) data[2]).longValue();
			datas.add(new Object[]{"其它", studentNum[0], num});
		}

		return datas;
	}

	private List<Object[]> findApplyNum() {
		OqlBuilder query = OqlBuilder.from(FinanceGreenStd.class, "o");
		query.select("o.student.grade, count(*)");
		query.where("o.handle = true");
		query.where("o.student.registed = true");
		query.where("exists (from o.financeGreens fg)");
		query.groupBy("o.student.grade");
		List<Object[]> applyNums = entityDao.search(query);
		return applyNums;
	}

	@Override
	public List<Object[]> applyItemNumCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		String sql = "SELECT" +
				"    fg.NAME," +
				"    COUNT(*) num" +
				" FROM" +
				"    YXW_FINANCE_GREEN_STDS fgs" +
				" JOIN CP_STUDENTS s ON" +
				"    s.id = fgs.STUDENT_ID" +
				" JOIN YXW_FINANCE_GREEN_STD_ITEMS fgsi ON" +
				"    FGSI.FINANCE_GREEN_STD_ID = fgs.id" +
				" JOIN YXF_FINANCE_GREENS fg ON" +
				"    fg.id = fgsi.FINANCE_GREEN_ID" +
				" WHERE" +
				"    s.grade = ?" +
				"    and fgs.handle = 1" +
				" GROUP BY" +
				"    fg.name" +
				" ORDER BY" +
				"    count(*) desc";
		return (List<Object[]>) entityDao.searchSqlQuery(sql, teachCalendar.getYear());
	}

	@Override
	public List<Object[]> applyItemNumHistory() {
		String sql = "SELECT" +
				"    fg.NAME," +
				"    s.grade," +
				"    COUNT(*) num" +
				" FROM" +
				"    YXW_FINANCE_GREEN_STDS fgs" +
				" JOIN CP_STUDENTS s ON" +
				"    s.id = fgs.STUDENT_ID" +
				" JOIN YXW_FINANCE_GREEN_STD_ITEMS fgsi ON" +
				"    FGSI.FINANCE_GREEN_STD_ID = fgs.id" +
				" JOIN YXF_FINANCE_GREENS fg ON" +
				"    fg.id = fgsi.FINANCE_GREEN_ID" +
				" WHERE" +
				"    s.registed = 1" +
				" GROUP BY" +
				"    fg.name," +
				"    s.grade";
		return (List<Object[]>) entityDao.searchSqlQuery(sql);
	}

	@Override
	public List<Object[]> enabledNumCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		List<Object[]> datas = new ArrayList<Object[]>();
		Long applyNum = getApplyNum(teachCalendar.getYear());
		Long enabledNum = getEnabledNum(teachCalendar.getYear());
		datas.add(new Object[]{"通过人数", enabledNum});
		datas.add(new Object[]{"未通过人数", applyNum - enabledNum});
		return datas;
	}

	@Override
	public List<Object[]> enabledNumHistory() {

		OqlBuilder query = OqlBuilder.from(FinanceGreenStudent.class, "o");
		query.select("o.batch.year, count(*)");
		query.groupBy("o.batch.year");
		List<Object[]> enabledNums = entityDao.search(query);

		List<Object[]> applyNums = findApplyNum();

		List<Object[]> datas = new ArrayList<Object[]>();

		for (Object[] applyNum : applyNums) {
			Object[] data = new Object[]{"通过人数", applyNum[0], 0};
			for (Object[] enabledNum : enabledNums) {
				if (applyNum[0].equals(enabledNum[0])) {
					data[2] = enabledNum[1];
				}
			}
			datas.add(data);
			long num = ((Number) applyNum[1]).longValue() - ((Number) data[2]).longValue();
			datas.add(new Object[]{"未通过人数", applyNum[0], num});
		}

		return datas;

//		String sql = "SELECT" +
//				"    fg.NAME," +
//				"    pb.YEAR," +
//				"    COUNT(*) num" +
//				" FROM" +
//				"    YXF_FINANCE_GREEN_STUDENTS fgs" +
//				" JOIN YXF_FINANCE_GREEN_SITEMS fgsi ON" +
//				"    fgsi.STUDENT_ID = fgs.id" +
//				" JOIN YXF_FINANCE_GREENS fg ON" +
//				"    fg.id = fgsi.GREEN_ID" +
//				" JOIN LC_PROCESS_BATCHES pb ON" +
//				"    pb.id = fgs.BATCH_ID" +
//				" WHERE" +
//				"    pb.YEAR = '2019'" +
//				" GROUP BY" +
//				"    fg.name," +
//				"    pb.YEAR";
//		return (List<Object[]>) entityDao.searchSqlQuery(sql);
	}

	@Override
	public List<Object[]> enabledItemNumCurrent() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		OqlBuilder query = OqlBuilder.from(FinanceGreenStudentItem.class);
		query.select("green.name, count(*)");
		query.where("student.batch.year = :year", teachCalendar.getYear());
		query.groupBy("green.name");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	@Override
	public List<Object[]> enabledItemNumHistory() {
		OqlBuilder query = OqlBuilder.from(FinanceGreenStudentItem.class);
		query.select("green.name, student.batch.year, count(*)");
		query.groupBy("green.name, student.batch.year");
		List<Object[]> datas = entityDao.search(query);
		return datas;
	}

	/**
	 * 学生总人数
	 *
	 * @return
	 */
	@Override
	public Long getStudentNum() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		return getStudentNum(teachCalendar.getYear());
	}

	/**
	 * 绿色通道申请总人数
	 *
	 * @return
	 */
	private Long getApplyNum(String year) {
		OqlBuilder query = OqlBuilder.from(FinanceGreenStd.class, "o");
		query.select("count(*)");
		query.where("o.handle = true");
		query.where("o.student.grade = :year", year);
		query.where("exists (from o.financeGreens fg)");
		List<Long> datas = entityDao.search(query);
		return datas.get(0);
	}

	/**
	 * 绿色通道通过总人数
	 *
	 * @return
	 */
	private Long getEnabledNum(String year) {
		OqlBuilder query = OqlBuilder.from(FinanceGreenStudent.class);
		query.select("count(*)");
		query.where("batch.year = :year", year);
		List<Long> datas = entityDao.search(query);
		return datas.get(0);
	}

	@Override
	public List<Object[]> applyItemNumOfAll() {
		List<Object[]> datas = applyItemNumCurrent();
		Long studentNum = getStudentNum();
		for (Object[] data : datas) {
			studentNum -= ((BigDecimal) data[1]).longValue();
		}
		datas.add(new Object[]{"其它", studentNum});
		return datas;
	}

	@Override
	public List<Object[]> greenStudentEnabledNum() {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		List<Object[]> datas = new ArrayList<Object[]>();
		Long studentNum = getStudentNum(teachCalendar.getYear());
		Long studentGreenEnabledNum = getEnabledNum(teachCalendar.getYear());
		datas.add(new Object[]{"通过人数", studentGreenEnabledNum});
		datas.add(new Object[]{"其它", studentNum - studentGreenEnabledNum});
		return datas;
	}
}
