package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.yxxt.finance.model.*;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.FinanceGreenCollectService;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;

import javax.annotation.Resource;
import java.util.List;

public class FinanceGreenParentAction extends ProcessLinkActionSupport {

	@Resource
	private FinanceStudentService financeStudentService;
	@Resource
	private FinanceGreenService financeGreenService;
	@Resource
	private FinanceGreenCollectService financeGreenCollectService;

	protected void editSetting(Long batchId, Student student) {
		super.editSetting(student);
		FinanceTemplate financeTemplate = financeStudentService.findTempalte(student);
		FinanceStudent financeStudent = financeStudentService.getFinanceStudent(student);
		if(financeStudent != null){
			Long studentId = financeStudent.getStudent().getId();
			Student stu = entityDao.get(Student.class, studentId);
			OqlBuilder<FinanceGreenStd> builder = OqlBuilder.from(FinanceGreenStd.class,"t");
			builder.where("t.student.id =:tid",stu.getId());
			List<FinanceGreenStd> list = entityDao.search(builder);
			if(list.size()>0){
				FinanceGreenStd greenstu = list.get(0);
				if(greenstu != null){
					List<FinanceGreen> financeGreen = greenstu.getFinanceGreens();
					put("financeGreen",financeGreen);
				}
			}
		}
		put("financeTemplate", financeTemplate);
		put("financeStudent", financeStudent);
		put("financeGreens", financeGreenService.find());
		put("financeGreenStudent", financeGreenService.getFinanceGreenStudent(student.getId()));
	}

	@Override
	protected void save(Long batchId, Student student) {
		super.save(batchId, student);
		List<FinanceGreenStudentItem> financeGreenStudentItems = CollectUtils.newArrayList();
		String remark = get("remark");
		if (remark != null) {
			remark = remark.replace("\n", "<br/>");
		}
		Long stuId =student.getId();
		Student stu = entityDao.get(Student.class, stuId);
		Long studentId = stu.getUser().getId();
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(studentId);
		FinanceGreenStudent financeGreenStudent = financeGreenService.getFinanceGreenStudent(student.getId());
		if(financeGreenStd == null){
			financeGreenStudentItems = getAllEntity(FinanceGreenStudentItem.class, "fgs");
			financeGreenStd = new FinanceGreenStd();
			financeGreenStd.setStudent(stu);
			financeGreenStd.getFinanceGreens().clear();
			financeGreenStd.setHandle(true);
			if (financeGreenStd.getHandle()) {
				List<FinanceGreen> financeGreens = entityDao.get(FinanceGreen.class, getAll("fgs", Long.class));
				financeGreenStd.getFinanceGreens().addAll(financeGreens);
			}
			entityDao.saveOrUpdate(financeGreenStd);
		}else{
			for(FinanceGreen green : financeGreenStd.getFinanceGreens()){
				FinanceGreenStudentItem item = new FinanceGreenStudentItem();
				item.setGreen(green);
//				item.setStudent(stu);
				item.setMoney(green.getMoney());
				financeGreenStudentItems.add(item);
			}
		}
		
		FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
		financeStudent.setIftongguo("true");
		financeGreenService.save(batchId, student.getId(), financeGreenStudentItems, remark,financeStudent);
	}

	@Override
	protected void cancel(Long batchId, Student student) {
		super.cancel(batchId, student);
		FinanceGreenStudent financeGreenStudent = financeGreenService.getFinanceGreenStudent(student.getId());
		FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
		financeStudent.setGreenMoney(0D);
		financeStudent.setIftongguo("false");
		financeStudent.countFeeOdd();
		entityDao.saveOrUpdate(financeStudent);
		entityDao.remove(financeGreenStudent);
	}
}
