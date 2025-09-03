package com.yzsoft.yxxt.statistics.service;

import java.util.List;

public interface FinanceGreenStatisticsService {

	/**
	 * 当前年份绿色通道人数与学生总数对比图
	 * @return
	 */
	List<Object[]> applyNumCurrent();

	List<Object[]> applyNumHistory();

	/**
	 * 统计申请绿色通道学生：当年绿色通道申请人数统计（饼图上需要显示人数）
	 * @return
	 */
	List<Object[]> applyItemNumCurrent();

	/**
	 * 历年申请绿色通道学生趋势图（横轴年份，纵轴人数）及数据展示
	 * @return
	 */
	List<Object[]> applyItemNumHistory();

	/**
	 * 当前年份通过申请比例，通过申请占申请人数比例（申请通过率）
	 * @return
	 */
	List<Object[]> enabledNumCurrent();

	/**
	 * 批准通过（迎新流程的绿色通道环节）绿色通道学生趋势图（横轴年份，纵轴人数）及数据展示
	 * @return
	 */
	List<Object[]> enabledNumHistory();

	List<Object[]> enabledItemNumCurrent();

	List<Object[]> enabledItemNumHistory();

	/**
	 * 各个绿色通道项目与总人数对比图
	 * @return
	 */
	List<Object[]> applyItemNumOfAll();

	/**
	 * 批准申请占总学生数比例
	 * @return
	 */
	List<Object[]> greenStudentEnabledNum();

	/**
	 * 学生人数
	 * @return
	 */
	Long getStudentNum();
}
