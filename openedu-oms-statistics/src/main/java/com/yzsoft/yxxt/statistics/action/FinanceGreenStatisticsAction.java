package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.statistics.service.FinanceGreenStatisticsService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 绿色通道统计
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceGreenStatisticsAction extends BaseAction {

	@Autowired
	private FinanceGreenStatisticsService financeGreenStatisticsService;


	public String index() {
		return forward();
	}


	/**
	 * 申请人数统计
	 * 当前年份绿色通道申请人数与学生总数对比图
	 *
	 * @return
	 */
	public String applyNum() {
		return forward();
	}
	public String applyNumCurrent() {
		List<Object[]> datas = financeGreenStatisticsService.applyNumCurrent();
		put("datas", datas);
		return forward();
	}
	public String applyNumHistory() {
		List<Object[]> datas = financeGreenStatisticsService.applyNumHistory();
		List<String> items = getItems(datas, 0);
		List<String> years = getItems(datas, 1);
		Collections.sort(items);
		Collections.reverse(items);
		Collections.sort(years);
		put("items", items);
		put("years", years);
		put("datas", datas);
		return forward();
	}

	public String applyItemNum() {
		return forward();
	}

	/**
	 * 申请项目统计
	 * 统计申请绿色通道学生：当年绿色通道申请人数统计（饼图上需要显示人数）
	 *
	 * @return
	 */
	public String applyItemNumCurrent() {
		List<Object[]> datas = financeGreenStatisticsService.applyItemNumCurrent();
		put("datas", datas);
		return forward();
	}

	/**
	 * 历年申请绿色通道学生趋势图（横轴年份，纵轴人数）及数据展示
	 *
	 * @return
	 */
	public String applyItemNumHistory() {
		List<Object[]> datas = financeGreenStatisticsService.applyItemNumHistory();
		putItemAndYear(datas);
		return forward();
	}

	/**
	 * 申请项目统计
	 * 当前年份通过申请的各个绿色通道项目与总人数对比图
	 *
	 * @return
	 */
	public String applyItemNumOfAll() {
		List<Object[]> datas = financeGreenStatisticsService.applyItemNumOfAll();
		put("datas", datas);
		return forward();
	}

	/**
	 * 申请比例，通过申请占申请人数比例（申请通过率）
	 *
	 * @return
	 */
	public String enabledNum() {
		return forward();
	}

	public String enabledNumCurrent() {
		List<Object[]> datas = financeGreenStatisticsService.enabledNumCurrent();
		put("datas", datas);
		return forward();
	}

	/**
	 * 批准通过（迎新流程的绿色通道环节）绿色通道学生趋势图（横轴年份，纵轴人数）及数据展示
	 *
	 * @return
	 */
	public String enabledNumHistory() {
		List<Object[]> datas = financeGreenStatisticsService.enabledNumHistory();
		List<String> items = getItems(datas, 0);
		List<String> years = getItems(datas, 1);
		Collections.sort(items);
		Collections.reverse(items);
		Collections.sort(years);
		put("items", items);
		put("years", years);
		put("datas", datas);
		return forward();
	}

	/**
	 * 通过项目统计
	 * @return
	 */
	public String enabledItemNum() {
		return forward();
	}

	public String enabledItemNumCurrent() {
		List<Object[]> datas = financeGreenStatisticsService.enabledItemNumCurrent();
		put("datas", datas);
		return forward();
	}

	/**
	 * 批准通过（迎新流程的绿色通道环节）绿色通道学生趋势图（横轴年份，纵轴人数）及数据展示
	 *
	 * @return
	 */
	public String enabledItemNumHistory() {
		List<Object[]> datas = financeGreenStatisticsService.enabledItemNumHistory();
		putItemAndYear(datas);
		return forward();
	}

	/**
	 * 批准申请占总学生数比例
	 *
	 * @return
	 */
	public String greenStudentEnabledNum() {
		List<Object[]> datas = financeGreenStatisticsService.greenStudentEnabledNum();
		put("datas", datas);
		return forward();
	}

	private void putItemAndYear(List<Object[]> datas) {
		List<String> items = getItems(datas, 0);
		List<String> years = getItems(datas, 1);
		Collections.sort(items);
		Collections.sort(years);
		put("items", items);
		put("years", years);
		put("datas", datas);
	}

	private List<String> getItems(List<Object[]> datas, int index) {
		List<String> items = new ArrayList<String>();
		for (Object[] oo : datas) {
			String item = (String) oo[index];
			if (items.indexOf(item) < 0) {
				items.add(item);
			}
		}
		Collections.sort(items);
		return items;
	}

}
