package com.yzsoft.yxxt.welcome.process.action;

import java.util.ArrayList;
import java.util.List;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.FinanceService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.website.common.model.UploadFile;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceAction extends ProcessLinkActionSupport {

	@Resource
	private FinanceStudentService financeStudentService;

	@Override
	protected void editSetting(Long batchId, Student student) {
		super.editSetting(batchId, student);
		getFinanceStatus(batchId, student);
	}

	public FinanceStudent getFinanceStatus(Long batchId, Student student) {
		//收费模板
//		FinanceTemplate financeTemplate = financeService.findTempalte(student);
//		FinanceStudent financeStudent = financeService.getFinanceStatus(student);
		FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
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
					double total = 0;
					for(int i=0;i<financeGreen.size();i++){
						total = total + financeGreen.get(i).getMoney();
					}
					if(financeStudent.getFeeLeft() - total >= 0){
						total = financeStudent.getFeeLeft() - total;
						put("total",total);
					}else{
						total = 0;
						put("total",total);
					}
				}
			}
		}
		String iftongguo = financeStudent.getIftongguo();
		if(iftongguo!=null && iftongguo.equals("true")){
			put("iftongguo",iftongguo);
		}
		//绿色通道
//		List<FinanceGreenStudent> financeGreenStudents = financeGreenService.find(batchId, student.getId());
//		int greenMoney = 0;
//		for (FinanceGreenStudent fgs : financeGreenStudents) {
//			greenMoney += fgs.getMoney();
//		}
//		if (financeStudent == null && financeTemplate != null) {
//			financeStudent = new FinanceStudent();
//			financeStudent.setFeeTotal(financeTemplate.getMoney());
//			financeStudent.setGreenMoney((double) greenMoney);
//			financeStudent.setFeePaid((double) greenMoney);
//			financeStudent.setFeeOdd(financeStudent.getFeeTotal() - financeStudent.getFeePaid());
//			financeStudent.setStudent(student);
//			financeStudent.setYear(yxxtService.getYear());
//		}
		
//		put("financeTemplate", financeTemplate);
		put("financeStudent", financeStudent);
//		put("financeGreenStudents", financeGreenStudents);
		return financeStudent;
	}

	@Override
	protected void save(Long batchId, Student student) {
		Double feePaid = get("feePaid", Double.class);
		FinanceStudent financeStudent = getFinanceStatus(batchId, student);
//		financeStudent.setFeePaid(financeStudent.getFeePaid() + financeStudent.getFeeOdd());
//		financeStudent.setFeeOdd(0D);
		financeStudent.setFeePaid(feePaid);
		financeStudent.countFeeOdd();
		Assert.isTrue(financeStudent.getFeeOdd() >= 0, "实缴金额有误，操作失败！");
		String remark = get("remark");
		if (remark != null) {
			remark = remark.replace("\n", "<br/>");
		}
		financeStudent.setRemark(remark);
		entityDao.saveOrUpdate(financeStudent);
	}

	@Override
	protected void cancel(Long batchId, Student student) {
		super.cancel(batchId, student);
		FinanceStudent financeStudent = financeStudentService.getFinanceStudent(student);
		if (financeStudent != null) {
//			entityDao.remove(financeStudent);
			financeStudent.setFeePaid(0D);
			financeStudent.countFeeOdd();
			entityDao.saveOrUpdate(financeStudent);
		}
	}
}
