package com.yzsoft.yxxt.welcome.process.action;

import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.model.ProcessLinkItemLog;
import org.beangle.process.service.ProcessLinkLogService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProcessLogAction extends BaseAction {

	@Resource
	private ProcessLinkLogService processLinkLogService;

	public String index() {
		Long studentId = getLong("studentId");
		Long batchId = getLong("batchId");
		List<ProcessLinkItemLog> logs = processLinkLogService.findLog(batchId, studentId);

		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<ProcessLinkItem> items = new ArrayList<ProcessLinkItem>(batch.getProcess().getProcessLinkItems());
		Collections.sort(items, new MultiPropertyComparator<ProcessLinkItem>("step, px"));
		put("items", items);
		put("logs", logs);
		return forward();
	}
}
