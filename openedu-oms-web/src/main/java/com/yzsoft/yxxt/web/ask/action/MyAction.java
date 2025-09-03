package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.service.AskService;
import com.yzsoft.yxxt.web.ask.model.AskStudent;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MyAction extends SecurityActionSupport {

	@Resource
	private StudentService studentService;
	@Resource
	private AskService askService;

	@Override
	protected String getEntityName() {
		return AskStudent.class.getName();
	}

	@Override
	public String search() {
		String key = get("key");
		PageLimit limit = getPageLimit();
		List<AskStudent> asks = askService.findAskByStudentId(getUserId(), key, limit);
		put("asks", asks);
		return forward();
	}

	public String edit() {
		setToken();
//		put("plates", askService.findPlate());
		return forward();
	}

	public String save() {
		if (!checkToken()) {
			return redirect("index");
		}
		Student student = studentService.getStudentByCode(getUsername());
		AskStudent askStudent = populate(AskStudent.class, "ask");
		askStudent.setStudent(getCurrentUser());
		askStudent.setCreateTime(new Date());
		askStudent.setDepartment(student.getDepartment());
		askStudent.setStatus(AskStudent.STATUS_NOT_REPLY);
		entityDao.saveOrUpdate(askStudent);
		return redirect("index");
	}

	@Override
	public String remove() throws Exception {
		return null;
	}

}
