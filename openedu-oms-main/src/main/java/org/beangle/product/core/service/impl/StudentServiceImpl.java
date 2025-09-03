package org.beangle.product.core.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.activiti.engine.impl.util.CollectionUtil;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.action.StudentAction;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.sync.service.SyncBaseImpl;
import org.beangle.product.utils.DictTypeUtils;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzsoft.bpcg.ws.service.StudentWsService;

import freemarker.template.utility.CollectionUtils;

/**
 * 学生接口实现
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年9月14日 下午1:35:27
 */
@Service
public class StudentServiceImpl extends SyncBaseImpl implements StudentService {

	@Autowired
	private StudentWsService studentWsService;
	@Autowired
	private BspBaseService bspBaseService;
	@Autowired
	private DictDataService dictDataService;

	public List<Student> findAllStudent() {
		return entityDao.getAll(Student.class);
	}

	public Student getStudentByCode(String code) {
		OqlBuilder<Student> builder = OqlBuilder.from(Student.class, "student");
		builder.where("student.cardcode = :code or student.code = :code", code);
		List<Student> list = entityDao.search(builder);
		if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
	}

	public List<Student> findStudentByCodes(String[] codes) {
		return entityDao.get(Student.class, "code", Arrays.asList(codes));
	}

	public List<Student> findStudentByDepartmentCode(String departmentCode) {
		return entityDao.get(Student.class, "department.code", departmentCode);
	}

	public List<Student> findStudentByAdminClassCode(String adminClassCode) {
		return entityDao.get(Student.class, "adminClass.code", adminClassCode);
	}

	public List<Student> findStudentByParams(String grade, String adminClassCode, String departmentCode, String majorCode, String directionCode) {
		OqlBuilder<Student> builder = OqlBuilder.from(Student.class, "student");
		if (StringUtils.isNotBlank(grade)) {
			builder.where("student.grade = :grade", grade);
		}
		if (StringUtils.isNotBlank(adminClassCode)) {
			builder.where("student.adminClass.code = :adminClassCode", adminClassCode);
		}
		if (StringUtils.isNotBlank(departmentCode)) {
			builder.where("student.department.code = :departmentCode", departmentCode);
		}
		if (StringUtils.isNotBlank(majorCode)) {
			builder.where("student.major.code = :majorCode", majorCode);
		}
		if (StringUtils.isNotBlank(directionCode)) {
			builder.where("student.direction.code = :directionCode", directionCode);
		}
		return entityDao.search(builder);
	}

	@SuppressWarnings("unchecked")
	public List<Student> findStudentByOqlBuilder(OqlBuilder<?> query) {
		return (List<Student>) entityDao.search(query);
	}

	public List<Student> findStudentByLike(String paramValue) {
		String value = "%" + paramValue + "%";
		OqlBuilder<Student> query = OqlBuilder.from(Student.class, "s");
		query.where("(s.code like (:key) or s.name like (:key))", value);
		query.limit(1, 200);
		return entityDao.search(query);
	}

	public void sync(HttpSession session) {
		try {
			List<com.yzsoft.bpcg.ws.model.Student> list = this.findAllWsStudent();//ws获取所有学生
			init(list.size());
			Map<String, Student> studentMap = getStudentMap();
			Map<String, User> userMap = getUserMap();
			Map<String, Department> departmentMap = getDepartmentMap();
			Map<String, Major> majorMap = getMajorMap();
			Map<String, Direction> directionMap = getDirectionMap();
			Map<String, AdminClass> adminClassMap = getAdminClassMap();

			//循环学校数据
			for (int i = 0; i < list.size(); i++) {
				com.yzsoft.bpcg.ws.model.Student ostudent = list.get(i);
				try {
					Student student = studentMap.get(ostudent.getCode());

					//判断学校数据与本地数据是否重复
					if (student == null) {//不重复，新建一条数据
						student = new Student();
					}

					//创建学生用户
					String code = ostudent.getCardcode().toUpperCase();
					User user = userMap.get(code);

					if (user == null) {
						user = bspBaseService.createNewUser(user, code, ostudent.getName(), currentUser, 1L, "学生");
					}

					Department department = null;
					Major major = null;
					Direction direction = null;
					AdminClass adminClass = null;

					//判断班级是否为空
					if (null != ostudent.getAdminClass()) {
						adminClass = adminClassMap.get(ostudent.getAdminClass().getCode());
					}

					//判断专业方向是否为空
					if (null != ostudent.getDirection()) {
						direction = directionMap.get(ostudent.getDirection().getCode());
					}

					//判断专业是否为空
					if (null != ostudent.getMajor()) {
						major = majorMap.get(ostudent.getMajor().getCode());
					}

					//判断部门是否为空
					if (null != ostudent.getDepartment()) {
						department = departmentMap.get(ostudent.getDepartment().getCode());
					}

					student.setUser(user);

					//保存
					student.setCode(ostudent.getCode());
					student.setName(ostudent.getName());

					//判断生日是否为空
					if (null != ostudent.getBirthday()) {
						student.setBirthday(DateUtils.parseDate(ostudent.getBirthday(), DATE_PATTERN));
					}

					student.setCardcode(ostudent.getCardcode());
					student.setCardType(ostudent.getCardType());
					student.setCountry(ostudent.getCountry());
//					student.setDomicilePlace(ostudent.getDomicilePlace());
					student.setEmail(ostudent.getEmail());

					//判断入学时间是否为空
					if (null != ostudent.getEnrollmentDate()) {
						student.setEnrollmentDate(DateUtils.parseDate(ostudent.getEnrollmentDate(), DATE_PATTERN));
					}

					if (StringUtils.isNotEmpty(ostudent.getGender())) {
						if (ostudent.getGender().trim().equals("男")) {
							student.setGender(new DictData(805L));
						} else if (ostudent.getGender().trim().equals("女")) {
							student.setGender(new DictData(806L));
						}
					}
					student.setGrade(ostudent.getGrade());
					student.setGraduateSchool(ostudent.getGraduateSchool());
//					student.setHomeAddress(ostudent.getHomeAddress());
//					student.setHomePhone(ostudent.getHomePhone());
					student.setInSchooled(ostudent.isInSchooled());

					//判断离校时间是否为空
					if (null != ostudent.getLeaveDate()) {
						student.setLeaveDate(DateUtils.parseDate(ostudent.getLeaveDate(), DATE_PATTERN));
					}

					student.setNameEn(ostudent.getNameEn());
//					student.setNativePlace(ostudent.getNativePlace());
					student.setPhone(ostudent.getPhone());
//					student.setPostcode(ostudent.getPostcode());
//					student.setStatus(ostudent.getStatus());
					student.setDepartment(department);
					student.setMajor(major);
					student.setDirection(direction);
					student.setAdminClass(adminClass);

					entityDao.saveOrUpdate(student);

					if (student.getHome() == null) {
						StudentHome home = new StudentHome();
//						home.setStudent(student);
						home.setDomicilePlace(ostudent.getDomicilePlace());
						home.setAddress(ostudent.getHomeAddress());
						home.setPhone(ostudent.getHomePhone());
						home.setNativePlace(ostudent.getNativePlace());
						home.setZipcode(ostudent.getPostcode());
						entityDao.saveOrUpdate(home);
						student.setHome(home);
					}

					if (null == student.getInfo()) {
						StudentInfo studentInfo = new StudentInfo();
//						studentInfo = new StudentInfo();
//						studentInfo.setStudent(student);
//						studentInfo.setUser(student.getUser());
						student.setInfo(studentInfo);
						entityDao.saveOrUpdate(studentInfo);
					}
//					entityDao.saveOrUpdate(student);
				} catch (Exception e) {
					System.out.println(e);
					//添加错误消息
					addError(ostudent.getCode(), e);
					//logger.error(e.getMessage(), e);
				}
				//用于计数
				setProgress(i + 1, list.size());
			}
		} catch (Exception e) {
			System.out.println(e);
			init(0);
		}
	}

	@Override
	public StudentHome getStudentHome(Long studentId) {
		return entityDao.getEntity(StudentHome.class, "student.id", studentId);
	}

	@Override
	public void remove(String code) {
		Student student = entityDao.getEntity(Student.class, "code", code);
		if (student != null) {
			try {
//                student.setContact(null);
//                student.setHome(null);
//                student.setInfo(null);
//                student.setOther(null);
//                entityDao.saveOrUpdate(student);
//            entityDao.executeUpdateHql("delete " + StudentContact.class.getName() + " where student.id = ?", student.getId());
//            entityDao.executeUpdateHql("delete " + StudentHome.class.getName() + " where student.id = ?", student.getId());
//            entityDao.executeUpdateHql("delete " + StudentInfo.class.getName() + " where student.id = ?", student.getId());
//            entityDao.executeUpdateHql("delete " + StudentOther.class.getName() + " where student.id = ?", student.getId());
				entityDao.remove(student);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				student = entityDao.getEntity(Student.class, "code", code);
				student.setRegisted(false);
				student.setInSchooled(false);
				entityDao.saveOrUpdate(student);
			}
		}
		User user = entityDao.getEntity(User.class, "name", code);
		if (user != null) {
			try {
				entityDao.remove(user);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				user = entityDao.getEntity(User.class, "name", code);
				user.setEnabled(false);
				entityDao.saveOrUpdate(user);
			}
		}
	}

	/**
	 * 获取所有学生，调用ws接口，分段获取
	 *
	 * @return 学生集合
	 */
	public List<com.yzsoft.bpcg.ws.model.Student> findAllWsStudent() {
		List<com.yzsoft.bpcg.ws.model.Student> list = CollectUtils.newArrayList();
		int pageNo = 1;
		boolean isContinue = true;
		do {
			List<com.yzsoft.bpcg.ws.model.Student> students = studentWsService.findStudent(pageNo, 10000);
			if (students != null && students.size() > 0) {
				list.addAll(students);
				pageNo++;
			} else {
				isContinue = false;
			}
			isContinue = false;
		} while (isContinue);
		return list;
	}

	/**
	 * 获取班级集合
	 *
	 * @return
	 * @author 周建明
	 * @createDate 2016年10月10日 上午9:59:36
	 */
	private Map<String, AdminClass> getAdminClassMap() {
		List<AdminClass> list = entityDao.getAll(AdminClass.class);
		Map<String, AdminClass> map = new HashMap<String, AdminClass>();
		for (AdminClass adminClass : list) {
			map.put(adminClass.getCode(), adminClass);
		}
		return map;
	}

	@Override
	public List<DictData> findGender() {
		List<DictData> genders = dictDataService.findDictData(GENDER);
		if (genders.isEmpty()) {
			String name = "性别";
			String[] names = new String[]{"男", "女"};
			genders = dictDataService.init(GENDER, name, names);
		}
		return genders;
	}

	@Override
	public List<DictData> findEnrollType() {
		List<DictData> genders = dictDataService.findDictData(ENROLL_TYPE);
		if (genders.isEmpty()) {
			String name = "招生类别";
			String[] names = new String[]{"统招", "自主招生"};
			genders = dictDataService.init(ENROLL_TYPE, name, names);
		}
		return genders;
	}

	@Override
	public List<DictData> findEducation() {
		List<DictData> educations = dictDataService.findDictData(XLCC);
		if (educations.isEmpty()) {
			String name = "学历层次";
			String[] names = new String[]{"本科", "专科"};
			educations = dictDataService.init(XLCC, name, names);
		}
		return educations;
	}

	@Override
	public List<String> findGrade() {
		OqlBuilder query = OqlBuilder.from(Student.class);
		query.select("grade");
		query.groupBy("grade");
		query.orderBy("grade");
		return entityDao.search(query);
	}
}
