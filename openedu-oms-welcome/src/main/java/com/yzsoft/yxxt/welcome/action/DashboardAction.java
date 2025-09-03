package com.yzsoft.yxxt.welcome.action;

import com.yzsoft.yxxt.process.service.YxxtProcessService;
import com.yzsoft.yxxt.welcome.dto.BaseCount;
import com.yzsoft.yxxt.welcome.dto.NationCount;
import com.yzsoft.yxxt.welcome.service.WelcomeCountService;
import org.apache.commons.collections.ComparatorUtils;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.process.bean.ProcessBatchCount;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DashboardAction extends BaseAction {

	@Resource
	private ProcessBatchService processBatchService;
	@Resource
	private YxxtProcessService yxxtProcessService;
	@Resource
	private WelcomeCountService welcomeCountService;

	public String index() {
		Long batchId = getLong("batchId");
		put("batchId", batchId);
		return forward();
	}

	public String num() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		Long completeNum = processBatchService.countCompleteNum(batch);
		put("completeNum", completeNum);
		put("unCompleteNum", studentNum - completeNum);
		return forward();
	}

	public String department() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<BaseCount> datas = welcomeCountService.countByDepartment(batch);
//		Collections.sort(datas, new BeanComparator("completeNum"));
//		Collections.sort(datas, ComparatorUtils.reversedComparator(new BeanComparator("name")));
		Collections.sort(datas, ComparatorUtils.reversedComparator(new MultiPropertyComparator<BaseCount>("name")));
		put("datas", datas);
		return forward();
	}

	public String major() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<BaseCount> datas = welcomeCountService.countByMajor(batch);
		Collections.sort(datas, new MultiPropertyComparator<BaseCount>("department,name"));
		put("datas", datas);
		return forward();
	}

	public String nation() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<NationCount> datas = welcomeCountService.countByNation(batch);
		put("datas", datas);
		return forward();
	}

	public String china() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<BaseCount> datas = welcomeCountService.countByProvince(batch);
		Long completeNum = 0L;
		for (BaseCount data : datas) {
			completeNum += data.getCompleteNum();
		}
		if (completeNum > 0) {
			for (BaseCount data : datas) {
				data.setPercent(data.getCompleteNum() * 1.0 / completeNum);
			}
		}
		put("datas", datas);
		return forward();
	}

	public String province() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<BaseCount> datas = welcomeCountService.countByProvince(batch);
		if (datas.size() > 18) {
			datas = datas.subList(0, 18);
		}
		put("datas", datas);
		return forward();
	}

	public String city() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<BaseCount> datas = welcomeCountService.countByCity(batch);
//		if (datas.size() > 10) {
//			datas = datas.subList(0, 10);
//		}
		put("datas", datas);
		return forward();
	}

	public String item() {
		Long batchId = getLong("batchId");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<ProcessBatchCount> datas = processBatchService.countItem(batch);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		put("datas", datas);
		put("studentNum", studentNum);
		return forward();
	}

}
