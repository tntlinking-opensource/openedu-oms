package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.FinanceGreenCollectService;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.common.model.UploadFile;
import org.beangle.website.common.util.SeqStringUtil;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import javax.annotation.Resource;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceGreenStdInfoAction extends CollectAction {

	@Resource
	private FinanceGreenService financeGreenService;
	@Resource
	private FinanceGreenCollectService financeGreenCollectService;
	@Resource
	private StudentService studentService;
	@Resource
	private FinanceStudentService financeStudentService;

	@Override
	public String getCode() {
		return "05";
	}

	@Override
	public String index() {
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(getUserId());
		Student student = entityDao.getEntity(Student.class, "user.id",getUserId());
		if (financeGreenStd == null) {
			if (getSwitch().isEditable()) {
				return redirect("edit");
			}
		}
		put("financeGreenStd", financeGreenStd);
		put("student",student);
		return super.index();
	}

	@Override
	public String edit() {
		Long id = getUserId();
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(id);
		Student student = getStudent();
		if (financeGreenStd == null) {
			financeGreenStd = new FinanceGreenStd();
		}
		put("financeGreenStd", financeGreenStd);
		put("financeGreens", financeGreenService.find());
		put("student",student);
		put("studentId",student.getId());
		return super.edit();
	}

	@Override
	public String save() {
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(getUserId());
//		Student student = entityDao.getEntity(Student.class, "user.id",getUserId());
		Student student = getStudent();
		if (financeGreenStd == null) {
			financeGreenStd = new FinanceGreenStd();
		}
		beforeSave(financeGreenStd);
		populate(financeGreenStd, "financeGreenStd");
		financeGreenStd.getFinanceGreens().clear();
		if (financeGreenStd.getHandle()) {
			List<FinanceGreen> financeGreens = entityDao.get(FinanceGreen.class, getAll("financeGreenId", Long.class));
			financeGreenStd.getFinanceGreens().addAll(financeGreens);
		}
		List<UploadFile> fils = CollectUtils.newArrayList();
		if(StringUtils.isNotEmpty(get("uuids"))){
			fils = entityDao.get(UploadFile.class,"uuid",Arrays.asList(get("uuids").split(",")));
		}
		student.getStdInfoFile().clear();
		student.getStdInfoFile().addAll(fils);
		entityDao.saveOrUpdate(financeGreenStd,student);
		afterSave();
		return redirect("index","提交成功！");
	}
	
	public String saveFj(){
		Long studentId = getLong("studentId");
		Student stu = entityDao.get(Student.class, getLong("studentId"));
		try {
			String uuids = get("uuids");
			if(StringUtils.isNotEmpty(uuids)) {
				Set<UploadFile> uploadFiles = new HashSet<UploadFile>(entityDao.get(UploadFile.class,"uuid",Arrays.asList(uuids.split(","))));
				stu.getStdInfoFile().removeAll(stu.getStdInfoFile());
				stu.getStdInfoFile().addAll(uploadFiles);
				entityDao.saveOrUpdate(stu);
			}
        } catch (Exception e) {
        	logger.error(e.getMessage());
        }
		return redirect("index","提交成功！","&studentId="+studentId+"&batchId="+getLong("batchId"));
	}
}
