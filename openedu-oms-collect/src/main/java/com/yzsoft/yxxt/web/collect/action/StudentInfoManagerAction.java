package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.service.StudentInfoCollectService;
import org.beangle.product.core.model.Student;
import org.beangle.product.student.property.model.StdPropertyConfig;
import org.beangle.product.student.property.model.StdPropertyType;
import org.beangle.product.student.property.service.StdPropertyService;
import org.beangle.product.student.service.StudentInfoService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentInfoManagerAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;
	@Resource
	protected StdPropertyService stdPropertyService;
	@Resource
	protected StudentInfoService studentInfoService;
	@Autowired
	protected StudentInfoCollectService studentInfoCollectService;
	@Autowired
	protected YxxtConfig yxxtConfig;

	@Override
	protected String getEntityName() {
		return Student.class.getName();
	}

	@Override
	public String index() {
		put("year", yxxtService.getYear());
		return super.index();
	}


	@Override
	public String info() {
		Long studentId = getLong("student.id");
		Student student = entityDao.get(Student.class, studentId);
		Long educationId = student.getEducation().getId();
		List<StdPropertyType> types = stdPropertyService.findType(educationId);
		List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
		put("types", types);
		put("configs", configs);
		put("student", student);
		put("families", studentInfoService.findFamily(student.getId()));
		put("educations", studentInfoService.findEducation(student.getId()));
		put("educationEnabled", yxxtConfig.isStudentInfoEducationEnabled());
		put("studentInfoStates", studentInfoCollectService.findState(student));
		return forward();
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new TreeMap<String, String>();
		map.put("考生号", "code");
		map.put("姓名", "name");
		map.put("招生类别", "enrollType.name");
		map.put("专业", "major.name");
		map.put("证件类型", "cardType");
		map.put("证件号码", "cardcode");
		map.put("出生日期", "birthday");
		map.put("性别", "gender.name");
		map.put("国家地区", "country");
		map.put("籍贯", "home.nativePlace");
		map.put("政治面貌", "info.politicalStatus.name");
		map.put("民族", "info.nation.name");
		map.put("港澳台侨胞", "info.hmto");
		map.put("健康状况", "info.health.name");
		map.put("户口所在地", "home.domicilePlace");
		map.put("生源地-省份/自治区", "enrollProvince.name");
		map.put("生源地-城市/地区", "enrollCity.name");
		map.put("生源地-区县", "enrollCounty.name");
		map.put("毕业学校", "graduateSchool");
		map.put("中考分数", "info.midExamScore");
		map.put("手机号码", "phone");
		map.put("联系电话", "telephone");
		map.put("联系电话", "telephone");
		map.put("通讯地址-省份/自治区", "contact.province.name");
		map.put("通讯地址-城市/地区", "contact.city.name");
		map.put("通讯地址-区县", "contact.county.name");
		map.put("通讯地址", "contact.address");
		map.put("邮政编码", "contact.zipcode");
		map.put("家庭电话", "home.phone");
		map.put("家庭联系人", "home.contact");
		map.put("家庭地址", "home.address");
		map.put("家庭-省份/自治区", "home.province.name");
		map.put("家庭-城市/地区", "home.city.name");
		map.put("家庭-区县", "home.county.name");
		map.put("中考分数", "info.midExamScore");
		map.put("曾用名", "info.nameUsed");
		map.put("QQ号", "contact.qq");
		map.put("微信号", "contact.wechat");
		map.put("常用信箱", "email");
		map.put("特长", "info.specialSkill");
		return map;
	}
}
