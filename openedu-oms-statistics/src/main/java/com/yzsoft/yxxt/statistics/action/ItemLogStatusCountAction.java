package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.process.service.YxxtProcessService;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountDepartment;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountItem;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountMajor;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountProvince;
import com.yzsoft.yxxt.statistics.service.ItemLogStatusCountService;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * 统计
 * /statistics/item-log-status-count
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemLogStatusCountAction extends BaseAction {

	@Autowired
	private ProcessBatchService processBatchService;
	@Autowired
	private ItemLogStatusCountService itemLogStatusCountService;

	@Resource
	private YxxtProcessService yxxtProcessService;

	public String index() {
		List<ProcessBatch> batches = entityDao.getAll(ProcessBatch.class);
		Collections.sort(batches, ComparatorUtils.reversedComparator(new BeanComparator("startTime")));
		Long batchId = getLong("batchId");
		if (batchId == null) {
			batchId = batches.get(0).getId();
		}
		put("batches", batches);
		put("batchId", batchId);
		return forward();
	}

	private ProcessBatch getBatch() {
		ProcessBatch batch = entityDao.get(ProcessBatch.class, getLong("batchId"));
		put("batch", batch);
		return batch;
	}

	public String item() {
		ProcessBatch batch = getBatch();
		Long studentNum = yxxtProcessService.getStudentNum(batch);
		Long completeNum = processBatchService.countCompleteNum(batch);
		List<ItemLogStatusCountItem> datas = itemLogStatusCountService.countItem(batch);
		put("studentNum", studentNum);
		put("completeNum", completeNum);
		put("unCompleteNum", studentNum - completeNum);
		put("datas", datas);
		return forward();
	}

	public String major() {
		ProcessBatch batch = getBatch();
		List<ItemLogStatusCountDepartment> datas = itemLogStatusCountService.countMajor(batch);
		Collection<String> items = new HashSet<String>();
		for (ItemLogStatusCountDepartment department : datas) {
			for (ItemLogStatusCountMajor major : department.getMajors()) {
				for (ItemLogStatusCountItem item : major.getItems()) {
					items.add(item.getName());
				}
			}
		}
		items = new ArrayList<String>(items);
		Collections.sort((ArrayList) items);
		put("datas", datas);
		put("items", items);
		put("emptyItem", new ItemLogStatusCountItem());
		return forward();
	}

	public String province() {
		ProcessBatch batch = getBatch();

		List<ItemLogStatusCountProvince> datas = itemLogStatusCountService.countProvince(batch);
		put("datas", datas);
		return forward();
	}
}
