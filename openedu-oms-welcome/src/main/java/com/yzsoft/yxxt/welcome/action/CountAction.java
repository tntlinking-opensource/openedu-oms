package com.yzsoft.yxxt.welcome.action;

import com.yzsoft.yxxt.process.service.YxxtProcessService;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.process.bean.ProcessBatchCount;
import org.beangle.process.bean.ProcessBatchDepartmentCount;
import org.beangle.process.bean.ProcessBatchDepartmentCountDetail;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
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
public class CountAction extends BaseAction {

	@Resource
	private ProcessBatchService processBatchService;

	@Resource
	private YxxtProcessService yxxtProcessService;

	public String index() {
		Long id = getLong("id");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, id);
		List<ProcessBatchCount> processBatchCounts = processBatchService.countItem(batch);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		put("processBatchCounts", processBatchCounts);
		put("studentNum", studentNum);
		put("id", id);
		return forward();
	}

	public String info() {
		Long id = getLong("id");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, id);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		Long completeNum = processBatchService.countCompleteNum(batch);
		if (studentNum == 0) {
			put("unCompletePercent", 100);
		} else if (studentNum != null && studentNum > 0) {
			put("completePercent", completeNum * 100.0 / studentNum);
			put("unCompletePercent", Math.max(0, (studentNum - completeNum) * 100.0 / studentNum));
		}
		put("studentNum", studentNum);
		put("completeNum", completeNum);
		put("batch", batch);
		return forward();
	}

	public String department() {
		Long id = getLong("id");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, id);
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		Long completeNum = processBatchService.countCompleteNum(batch);
		put("studentNum", studentNum);
		put("completeNum", completeNum);
		if (studentNum != null && studentNum > 0) {
			put("unCompleteNum", Math.max(0, studentNum - completeNum));
			put("completePercent", completeNum * 1.0 / studentNum);
			put("unCompletePercent", Math.max(0, (studentNum - completeNum) * 1.0 / studentNum));
		}
		List<ProcessBatchDepartmentCount> processBatchCounts = processBatchService.countItemByDepartment(batch);
		Collections.sort(batch.getProcess().getProcessLinkItems(), new MultiPropertyComparator<ProcessLinkItem>("step, px"));
		put("items", batch.getProcess().getProcessLinkItems());
		put("processBatchCounts", processBatchCounts);
		put("batch", batch);
		put("emptyDetail", new ProcessBatchDepartmentCountDetail());
		return forward();
	}

}
