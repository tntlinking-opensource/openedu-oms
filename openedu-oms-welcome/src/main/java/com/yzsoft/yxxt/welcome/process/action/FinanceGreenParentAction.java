package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.yxxt.finance.model.FinanceGreenStudent;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudentItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.product.core.model.Student;

import javax.annotation.Resource;
import java.util.List;

public class FinanceGreenParentAction extends ProcessLinkActionSupport {

	@Resource
	private FinanceStudentService financeStudentService;
	@Resource
	private FinanceGreenService financeGreenService;

	protected void editSetting(Long batchId, Student student) {
		super.editSetting(student);
		FinanceTemplate financeTemplate = financeStudentService.findTempalte(student);
		FinanceStudent financeStudent = financeStudentService.getFinanceStudent(student);
		put("financeTemplate", financeTemplate);
		put("financeStudent", financeStudent);
		put("financeGreens", financeGreenService.find());
		put("financeGreenStudent", financeGreenService.getFinanceGreenStudent(student.getId()));
	}

	@Override
	protected void save(Long batchId, Student student) {
		super.save(batchId, student);
		List<FinanceGreenStudentItem> financeGreenStudentItems = getAllEntity(FinanceGreenStudentItem.class, "fgs");
		String remark = get("remark");
		if (remark != null) {
			remark = remark.replace("\n", "<br/>");
		}
//		financeGreenService.save(batchId, student.getId(), financeGreenStudentItems, remark);
	}

	@Override
	protected void cancel(Long batchId, Student student) {
		super.cancel(batchId, student);
		FinanceGreenStudent financeGreenStudent = financeGreenService.getFinanceGreenStudent(student.getId());
		FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
		financeStudent.setGreenMoney(0D);
		financeStudent.countFeeOdd();
		entityDao.saveOrUpdate(financeStudent);
		entityDao.remove(financeGreenStudent);
	}
}
