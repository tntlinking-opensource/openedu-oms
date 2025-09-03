package com.yzsoft.yxxt.mobile.collect.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.model.UserBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.emsapp.portal.action.LoginAction;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentContact;
import org.beangle.product.core.model.StudentEducation;
import org.beangle.product.core.model.StudentEntity;
import org.beangle.product.core.model.StudentFamily;
import org.beangle.product.core.model.StudentHome;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.product.core.model.StudentOther;
import org.beangle.product.student.property.model.StdPropertyConfig;
import org.beangle.product.student.property.model.StdPropertyType;
import org.beangle.product.student.property.service.StdPropertyService;
import org.beangle.product.student.service.StudentInfoService;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.struts2.convention.route.Action;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.Batch;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import com.yzsoft.yxxt.web.collect.service.StudentInfoCollectService;
import com.yzsoft.yxxt.welcome.process.action.ErrorAction;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisterAction extends SyncAction {

	@Resource
	protected StudentInfoCollectService studentInfoCollectService;
	@Autowired
	protected YxxtConfig yxxtConfig;
	@Resource
	protected StdPropertyService stdPropertyService;
	@Resource
	private CollectService collectService;
	@Resource
	protected StudentInfoService studentInfoService;
	@Resource
	protected YxxtService yxxtService;

	/**
	 * 用户
	 */
	@Autowired
	protected UserService userService;

	public String getCode() {
		return "01";
	}

	@Override
	public String index() {
		Student student = getStudent();
		Long educationId = (student == null || student.getEducation() == null ? 13423L : student.getEducation().getId());
		List<StdPropertyType> types = stdPropertyService.findType(educationId);
		List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
		put("types", types);
		put("configs", configs);
		put("switch", collectService.getSwitch(getCode()));
		put("educationEnabled", yxxtConfig.isStudentInfoEducationEnabled());
		put("type", types.get(0));
		put("student", student);
		return forward();
	}

	@Override
	protected void indexSetting() {
		if (StringUtils.isNotBlank(get("cardcode"))) {
			ServletActionContext.getRequest().getSession().setAttribute("cardcode", get("cardcode"));
		}
		//通过身份证号查询学生
		Student student = getStudent();
//        Assert.isNull(student.getId(),"不可重复注册，请使用身份证后6位登录系统填写信息！");
		if (student.getEducation() == null) {
			student.setEducation(new DictData(13423L));
		}
		Long educationId = student.getEducation().getId();
		List<StdPropertyType> types = stdPropertyService.findType(educationId);
		List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
		put("types", types);
		put("configs", configs);

		put("student", student);
		put("studentInfo", student.getInfo());
		put("studentContact", student.getContact());
		put("studentHome", student.getHome());
		put("studentEnroll", entityDao.getEntity(StudentEnroll.class, "student.id", student.getId()));
		put("studentOther", student.getOther());
		put("educationEnabled", yxxtConfig.isStudentInfoEducationEnabled());
	}

	@Override
	public String info() {
		indexSetting();
		StdPropertyType type = entityDao.get(StdPropertyType.class, getLong("id"));
		put("type", type);
		return forward();
	}

	public void putDatas() {
		put("genders", dictDataService.findDictData("GENDER"));
		put("politicals", dictDataService.findDictData("ZZMM"));
		put("hyzks", dictDataService.findDictData("HYZK"));
		put("nations", dictDataService.findDictData("MZ"));
		put("healths", dictDataService.findDictData("JKZK"));
		put("enrollSourceTypes", dictDataService.findDictData("STUDENT_ENROLL_SOURCE_TYPE"));
		put("typeId", getLong("typeId"));
	}

	@Override
	public String edit() {
		String cardcode = get("cardcode");
		if (StringUtils.isNotBlank(cardcode)) {
			Student student = entityDao.getEntity(Student.class, "cardcode", cardcode);
			if (student != null) {
				return redirect(new Action(LoginAction.class));
			}
		}

		indexSetting();
		StdPropertyType type = entityDao.get(StdPropertyType.class, getLong("id"));
		put("type", type);
		putDatas();
		return forward();
	}

	@Override
	public String save() {
		Student student = getStudent();
		StudentInfo info = student.getInfo();
		StudentContact contact = student.getContact();
		StudentHome home = student.getHome();
		StudentOther other = student.getOther();
		populate(student, "student");
		populate(info, "studentInfo");
		populate(contact, "studentContact");
		populate(home, "studentHome");
		populate(other, "studentOther");
		entityDao.saveOrUpdate(student);

		StudentEnroll studentEnroll = populateEntity(StudentEnroll.class, "studentEnroll");
		if (null == studentEnroll.getId()) {
			Batch batch = entityDao.getEntity(Batch.class, "enabled", true);
			Assert.notNull(batch, "招生批次为空");
			studentEnroll.setBatch(batch);
			studentEnroll.setRegisted(true);
		}
		checkStudent(studentEnroll, student);
		if (StringUtils.isEmpty(student.getGrade())) {
			student.setGrade(yxxtService.getGrade());
		}

		String code = student.getCardcode().toUpperCase();
		User userBean = userService.get(code);
		if (userBean == null) {
			//创建学生用户
			User user = createNewUser(userBean, code, student.getName(), getCurrentUser(), 1L, "学生");
			student.setUser(user);
			studentEnroll.setUser(user);
		}
		entityDao.saveOrUpdate(info);
		entityDao.saveOrUpdate(contact);
		entityDao.saveOrUpdate(home);
		entityDao.saveOrUpdate(other);
		entityDao.saveOrUpdate(studentEnroll);
		student.setInfo(info);
		student.setContact(contact);
		student.setHome(home);
		student.setOther(other);

		entityDao.saveOrUpdate(student);
		studentInfoCollectService.saveState(student, get("code"));
		if (studentInfoCollectService.finished(student)) {
			collectService.finish(getUserId(), code, getCode());
		}

		return redirect("info", null, "id=" + get("typeId") + "&cardcode=" + student.getCardcode());
	}

	public User createNewUser(User user, String code, String name, User creator, Long categoryId, String groupName) {
		//创建用户
		if (user == null) {
			user = new UserBean();
			user.setName(code);
			user.setFullname(name);
			user.setEnabled(true);

			//设置身份
			Category c = entityDao.get(Category.class, categoryId);
			user.setDefaultCategory(c);
			user.getCategories().add(c);

			//不可为空
			user.setEffectiveAt(new Date());
			user.setEnabled(true);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			user.setCreator(creator);
			user.setPassword(EncryptUtil.encode(StringUtils.substring(code, -6).toUpperCase()));//设置默认密码
			entityDao.saveOrUpdate(user);
			setUserGroupByGroupName(user, groupName);//设置用户组
		} else {
			entityDao.executeUpdateHql("update " + User.class.getName() + " set enabled = true where id = ?", user.getId());
		}
		return user;
	}

	public void setUserGroupByGroupName(User user, String groupName) {
		if (!isdqGroupByGroupName(user, groupName)) {
			Group group = entityDao.getEntity(Group.class, "name", groupName);
			if (group != null) {
				GroupMember gm = new GroupMemberBean(group, user, GroupMember.Ship.MEMBER);
				user.getGroups().add(gm);
				entityDao.saveOrUpdate(user);
			}
		}
	}

	public String family() {
		Student student = getStudent();
		put("families", studentInfoService.findFamily(student.getId()));
		put("switch", collectService.getSwitch(getCode()));
		put("student", student);
		return forward();
	}

	public String familyEdit() {
		Student student = getStudent();
		StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
		put("student", student);
		put("studentFamily", studentFamily);
		put("politicals", dictDataService.findDictData("ZZMM"));
		return forward();
	}

	public String familySave() {
		checkSwitch();
		Student student = getStudent();
		List<StudentFamily> studentFamilies = studentInfoService.findFamily(student.getId());
		StudentFamily family = populateEntity(StudentFamily.class, "studentFamily");
		if (studentFamilies.size() > 0) {
			StudentFamily studentFamily = studentFamilies.get(studentFamilies.size() - 1);
			if (studentFamily.getSort() == null) {
				studentFamily.setSort(studentFamilies.size());
			}
			family.setSort(studentFamily.getSort() + 1);
		}
		if (family.getStudent() == null) {
			family.setStudent(student);
		}
		studentInfoCollectService.initFamily(family);
		Assert.isTrue(student.equals(family.getStudent()));
		entityDao.saveOrUpdate(family);
		return redirect("family");
	}

	public String familyInfo() {
		Student student = getStudent();
		StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
		put("student", student);
		put("studentFamily", studentFamily);
		return forward();
	}

	public String familyRemove() {
		Student student = getStudent();
		StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
		Assert.isTrue(student.equals(studentFamily.getStudent()));
		entityDao.remove(studentFamily);
		return redirect("family", "", "cardcode=" + student.getCardcode());
	}

	public String education() {
		Student student = getStudent();
		put("educations", studentInfoService.findEducation(student.getId()));
		put("switch", collectService.getSwitch(getCode()));
		return forward();
	}

	public String educationEdit() {
		StudentEducation studentEducation = entityDao.get(StudentEducation.class, getLong("id"));
		put("studentEducation", studentEducation);
		return forward();
	}

	public String educationSave() {
		checkSwitch();
		Student student = getStudent();
		List<StudentEducation> studentEducations = studentInfoService.findEducation(student.getId());
		StudentEducation education = populateEntity(StudentEducation.class, "studentEducation");
		if (studentEducations.size() > 0) {
			StudentEducation studentEducation = studentEducations.get(studentEducations.size() - 1);
			education.setSort(studentEducation.getSort() + 1);
		}
		if (education.getStudent() == null) {
			education.setStudent(student);
		}
		Assert.isTrue(student.equals(education.getStudent()));
		entityDao.saveOrUpdate(education);
		return redirect("education");
	}

	public String educationInfo() {
		StudentEducation studentEducation = entityDao.get(StudentEducation.class, getLong("id"));
		put("studentEducation", studentEducation);
		return forward();
	}

	protected Student getStudent() {
		String cardcode = (String) ServletActionContext.getRequest().getSession().getAttribute("cardcode");
		Student student = entityDao.getEntity(Student.class, "cardcode", cardcode);
		if (null == student) {
			student = new Student();
			student.setCardcode(cardcode);
		}
		return student;
	}

	protected void checkStudent(StudentEntity obj, Student std) {
		if (obj.getStudent() == null) {
			obj.setStudent(std);
		}
		Assert.isTrue(std.equals(obj.getStudent()));
	}

	protected void checkSwitch() {
		CollectSwitch collectSwitch = collectService.getSwitch(getCode());
		Assert.notNull(collectSwitch);
		Assert.isTrue(collectSwitch.isEditable());
		put("switch", collectSwitch);
		put("collectSwitch", collectSwitch);
	}

	public String educationRemove() {
		Student student = getStudent();
		StudentEducation studentEducation = entityDao.get(StudentEducation.class, getLong("id"));
		Assert.isTrue(student.equals(studentEducation.getStudent()));
		entityDao.remove(studentEducation);
		return redirect("education");
	}

}
