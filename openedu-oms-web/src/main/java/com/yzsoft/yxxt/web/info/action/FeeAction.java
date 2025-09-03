package com.yzsoft.yxxt.web.info.action;

import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.finance.service.FinanceService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeeAction extends SecurityActionSupport {

	@Resource
	private StudentService studentService;
	@Resource
	private FinanceStudentService financeStudentService;
	@Resource
	private FinanceGreenService financeGreenService;

	public String index() {
		Student student = studentService.getStudentByCode(getUsername());
		FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
		put("financeStudent", financeStudent);
		put("financeGreenStudents", financeGreenService.find(null, student.getId()));
		return forward();
	}

}
