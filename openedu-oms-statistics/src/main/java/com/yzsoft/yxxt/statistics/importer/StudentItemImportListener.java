package com.yzsoft.yxxt.statistics.importer;

import com.yzsoft.yxxt.welcome.service.WelcomeService;
import org.apache.commons.lang.StringUtils;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.process.model.ItemLogStatus;
import org.beangle.process.service.ProcessLinkLogService;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

/**
 * 业务项办理导入
 */
public class StudentItemImportListener extends ItemImporterListener {

	protected ProcessLinkLogService processLinkLogService;
	protected WelcomeService welcomeService;
	protected Map<String, Long> batchMap;
	protected Map<String, Long> itemMap;
	protected Map<String, Long> studentMap;

	public StudentItemImportListener() {
		super();
		processLinkLogService = getBean(ProcessLinkLogService.class);
		welcomeService = getBean(WelcomeService.class);
		batchMap = getNameAndIdMap("select id, name from LC_PROCESS_BATCHES");
		itemMap = getNameAndIdMap("select id, name from LC_PROCESS_LINK_ITEMS");
		studentMap = getNameAndIdMap("select id, code from cp_students");
	}

	private Map<String, Long> getNameAndIdMap(String sql) {
		Map<String, Long> map = new HashMap<String, Long>();
		for (Object o : entityDao.searchSqlQuery(sql)) {
			Object[] oo = (Object[]) o;
			map.put((String) oo[1], ((Number) oo[0]).longValue());
		}
		return map;
	}

	public void onItemFinish(TransferResult tr) {
		ItemLogStatus itemLogStatus = (ItemLogStatus) importer.getCurrent();
		Assert.notNull(itemLogStatus.getBatch(), "批次信息不能为空");
		Assert.isTrue(StringUtils.isNotBlank(itemLogStatus.getBatch().getName()), "批次名称不能为空");
		Assert.notNull(itemLogStatus.getItem(), "业务项信息不能为空");
		Assert.isTrue(StringUtils.isNotBlank(itemLogStatus.getItem().getName()), "业务项名称不能为空");
		Assert.notNull(itemLogStatus.getStudent(), "学生信息不能为空");
		Assert.isTrue(StringUtils.isNotBlank(itemLogStatus.getStudent().getCode()), "学号不能为空");
		Long batchId = batchMap.get(itemLogStatus.getBatch().getName());
		Long itemId = itemMap.get(itemLogStatus.getItem().getName());
		Long studentId = studentMap.get(itemLogStatus.getStudent().getCode());
		Assert.notNull(batchId, "批次名称有误");
		Assert.notNull(itemId, "业务项名称有误");
		Assert.notNull(studentId, "学号有误");
		processLinkLogService.saveLog(batchId, itemId, studentId);
		welcomeService.processLinkPassed(batchId, itemId, studentId);
		addUcount();
	}
}
