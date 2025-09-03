package com.yzsoft.yxxt.mobile.msg.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.ems.security.User;
import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * 同城学生
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentOfSameCityAction extends BaseAction {

	@Autowired
	private YxxtService yxxtService;

	public String index() {
		Student student = yxxtService.getStudent();
		List<Student> students = yxxtService.findStudentByCity(student);
		put("students", students);
		return forward();
	}

	public String edit() {
		Long friendId = getLong("friendId");
		User friend = entityDao.get(User.class, friendId);
		put("friend", friend);
		return forward();
	}

}
