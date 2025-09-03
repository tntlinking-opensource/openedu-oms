package com.yzsoft.yxxt.welcome.process.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.util.HSSFColor.GREEN;
import org.beangle.commons.comparators.MultiPropertyComparator;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.model.ProcessLinkItemLog;
import org.beangle.process.service.ProcessLinkLogService;

import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.BaseAction;
import org.beangle.website.common.model.UploadFile;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.FinanceGreenCollectService;


@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller("ProcessLogAction")
public class ProcessLogAction extends BaseAction {

	@Resource
	private ProcessLinkLogService processLinkLogService;
	@Resource
	private StudentService studentService;
	@Resource
	private FinanceGreenCollectService financeGreenCollectService;
	@Resource
	private FinanceStudentService financeStudentService;

	public String index() {
		Long studentId = getLong("studentId");
		Long batchId = getLong("batchId");
		Student stu = entityDao.get(Student.class, studentId);
		List<UploadFile> uploadfile = new ArrayList<UploadFile>(stu.getStdInfoFile());
		put("uploadfile",uploadfile);
		List<ProcessLinkItemLog> logs = processLinkLogService.findLog(batchId, studentId);
		ProcessBatch batch = entityDao.get(ProcessBatch.class, batchId);
		List<ProcessLinkItem> items = new ArrayList<ProcessLinkItem>(batch.getProcess().getProcessLinkItems());
		Collections.sort(items, new MultiPropertyComparator<ProcessLinkItem>("step, px"));
		List<UploadFile> stdInfoFile = new ArrayList<UploadFile>(entityDao.get(Student.class, studentId).getStdInfoFile());
		put("items", items);
		put("logs", logs);
		put("batchId", batchId);
		put("student", entityDao.get(Student.class, studentId));
		put("stdInfoFiles",stdInfoFile);
		return forward();
	}
	
}
