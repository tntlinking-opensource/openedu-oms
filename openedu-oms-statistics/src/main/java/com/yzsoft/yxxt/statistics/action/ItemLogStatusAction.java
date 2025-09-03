package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.statistics.importer.StudentItemImportListener;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.process.model.ItemLogStatus;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.util.DateUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

/**
 * 业务项办理查询
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ItemLogStatusAction extends EntityDrivenAction {

	@Resource
	private ProcessBatchService processBatchService;

	@Override
	protected String getEntityName() {
		return ItemLogStatus.class.getName();
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		List<ProcessBatch> batches = processBatchService.findBatch();
		if (!batches.isEmpty()) {
			put("batchId", batches.get(0).getId());
		}
		put("batchs", processBatchService.findBatch());
	}

	@Override
	protected void querySetting(OqlBuilder query) {
		super.querySetting(query);
		Boolean status = getBoolean("status");
		if (status != null) {
			if (status) {
				query.where("enabled = true");
			} else {
				query.where("enabled is null or enabled <> 1");
			}
		}
		Date startDate = getDateTime("startDate");
		if (startDate != null) {
			startDate = DateUtil.getDayStart(startDate);
			query.where("log.createTime >= :startDate", startDate);
		}
		Date endDate = getDateTime("endDate");
		if (endDate != null) {
			endDate = DateUtil.getDayEnd(endDate);
			query.where("log.createTime <= :endDate", endDate);
		}
		String orderBy = get("orderBy");
		if (orderBy != null && orderBy.indexOf("log.") >= 0) {
			query.where("log is not null");
		}
	}

	public String findItem() {
		Long id = getLong("id");
		ProcessBatch batch = entityDao.get(ProcessBatch.class, id);
		Collections.sort(batch.getProcess().getProcessLinkItems(), new MultiPropertyComparator<ProcessLinkItem>("step, px"));
		put("datas", batch.getProcess().getProcessLinkItems());
		return forward();
	}

	/**
	 * 业务项办理导入
	 *
	 * @return
	 */
	@Override
	public ItemImporterListener getImporterListener() {
		return new StudentItemImportListener();
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("批次", "batch.name");
		map.put("流程环节", "item.name");
		map.put("学号", "student.code");
		map.put("姓名", "student.name");
		map.put("性别", "student.gender.name");
		map.put("院系", "student.department.name");
		map.put("专业", "student.major.name");
		map.put("班级", "student.adminClass.name");
		map.put("办理状态", "log.enabledStr");
		return map;
	}

}
