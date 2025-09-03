package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.AdminClassTemp;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.*;
import com.yzsoft.yxxt.prepare.service.AdminClassPlanService;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.CategoryBean;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.model.UserBean;
import org.beangle.ems.security.service.GroupService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.*;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;

@Service
public class AdminClassPlanServiceImpl implements AdminClassPlanService {

    @Resource
    private EntityDao entityDao;
    @Resource
    private YxxtService yxxtService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    private GroupService groupService;
    @Resource
    protected BatchService batchService;

    @Override
    public List<Object[]> countStudent(Department department, Long educationId) {
        OqlBuilder query = OqlBuilder.from(Student.class, "s");
        query.select("s.major.id, s.major.name, count(*)");
//		query.join("left", "s.direction", "d");
        query.where("s.major.department = :department", department);
        query.where("s.education.id = :educationId", educationId);
        query.where("s.grade = :grade", yxxtService.getGrade());
        query.groupBy("s.major.id, s.major.code, s.major.name");
        query.orderBy("s.major.code");
        return entityDao.search(query);
    }
    
    /**
	 * 根据 年级、学生类别  进行分班
	 * @param enrollTypeCode
	 */
    @Override
	public void alloc(String enrollTypeCode, List<Department> departments) {
    	Long[] ids = new Long[departments.size()];
    	for(int i = 0 ; i < departments.size(); i++){
    		ids[i] = departments.get(i).getId();
    	}
    	//年级
        String grade = yxxtService.getGrade();
        String hql = "select distinct t.major from " + AdminClass.class.getName() 
				+ " t left join t.enrolls e where e.code = '"+enrollTypeCode+"' and grade = " + grade;
        if(departments.size() == 1){
        	hql = hql + " and t.department.id = " +departments.get(0).getId();
        }
		List<Major> majors = entityDao.searchHQLQuery(hql);
		
		List<StudentClass> studentClasses = new ArrayList<StudentClass>();
		List<AdminClass> adminClasses = null;
		List<Student> students = null;
		for(Major major : majors){
			students = findStudent(grade,major,enrollTypeCode);
			adminClasses = findAdminClasses(grade,major,enrollTypeCode);
			createStudentClass(adminClasses,students);
		}
	}

    //根据年级、专业、招生类别 查找对应的班级
    private List<AdminClass> findAdminClasses(String grade, Major major,
			String enrollTypeCode) {
    	StringBuffer hql = new StringBuffer();
		hql.append("select ac from "+AdminClass.class.getName() +" ac\n");
		hql.append("left join ac.enrolls e where e.code = '"+enrollTypeCode+"' \n");
		hql.append("and ac.grade = '"+grade+"' and ac.major.id = "+major.getId());
		hql.append(" and ac.num > ac.stdNum");
		return entityDao.searchHQLQuery(hql.toString());
	}

    //根据年级、专业、招生类别 查找对应的未分配的学生
	private List<Student> findStudent(String grade, Major major,
			String enrollTypeCode) {
		StringBuffer hql = new StringBuffer();
		hql.append("select s from " +Student.class.getName() +" s\n");
		hql.append("where s.grade = '" + grade + "' and s.major.id = " 
				+ major.getId() + " and s.enrollType.code = '" + enrollTypeCode +"'\n");
		hql.append("and s.adminClass is null");
		return entityDao.searchHQLQuery(hql.toString());
	}
	
	/**
	 * 根据班级人数进行分班
	 * @param adminClasses
	 * @param studentClasses
	 * @param students
	 * @param studentClassMap
	 * @param 是否匹配成绩 
	 */
	private void createStudentClass(List<AdminClass> adminClasses,List<Student> students) {
		int studentNum = 0;
		for(Iterator it = adminClasses.iterator(); it.hasNext();){
			AdminClass adminClass = (AdminClass)it.next();
			for (int i = adminClass.getStdNum()+1; studentNum < students.size(); i++, studentNum++) {
				Student student = students.get(studentNum);
				if( i > adminClass.getNum()){
					break;
				}
				student.setAdminClass(adminClass);
			}
		}
		entityDao.saveOrUpdate(students);
	}

	/**
     * 学生分班
     *
     * @param adminClassPlen 分班计划
     * @param rules          分班规则
     */
    @Override
    public void alloc(List<AdminClassPlan> adminClassPlen, List<AdminClassRule> rules) {
        //年级
        String grade = yxxtService.getGrade();
        for (AdminClassPlan plan : adminClassPlen) {
            plan.setGrade(grade);
//			deleteStudentClass(plan);
            deleteAdminClass(plan);
            List<AdminClassTemp> adminClasses = createAdminClassTemp(plan, rules);
            entityDao.saveOrUpdate(adminClasses);
            createStudentClass(adminClasses, plan, rules);
        }
    }

    @Override
    public List<AdminClassRule> findRule() {
        OqlBuilder query = OqlBuilder.from(AdminClassRule.class);
        query.orderBy("code");
        return entityDao.search(query);
    }

    @Override
    public void generateCode(Department department, AdminClassRule rule) {
        String grade = yxxtService.getGrade();
        List<AdminClassTemp> adminClasses = findAdminClass(grade, department);
        for (AdminClassTemp adminClass : adminClasses) {
            List<StudentEnroll> students = findStudent(adminClass);
            String codeFormat = "%0" + ((int) (Math.ceil(students.size() / 10.0)) + "d");
            sortStudent(students, null);
            for (int i = 0; i < students.size(); i++) {
                String code = null;
                if (AdminClassRule.STUDENT_CODE_1.equals(rule.getCode())) {
                    code = adminClass.getCode() + String.format(codeFormat, i + 1);
                } else {

                }
                StudentEnroll student = students.get(i);
                student.setCode(code);
            }
            entityDao.saveOrUpdate(students);
        }
    }

    @Override
    public void publish(Department department) {
        String grade = yxxtService.getGrade();
        List<AdminClassTemp> adminClassTemps = findAdminClass(grade, department);
//        Group studentGroup = groupService.getGroup("学生");
        for (AdminClassTemp adminClassTemp : adminClassTemps) {
            AdminClass adminClass = createAdminClass(adminClassTemp);
            entityDao.saveOrUpdate(adminClass);
            String sql = "update cp_students s" +
                    "   set s.admin_class_id = ?" +
                    " where s.code in (select s.code" +
                    "                    from yxp_student_classes sc" +
                    "                    join yxp_student_prepares s" +
                    "                      on sc.student_id = s.id" +
                    "                   where sc.admin_class_temp_id = ?)";
            entityDao.executeUpdateSql(sql, adminClass.getId(), adminClassTemp.getId());
//            List<StudentEnroll> StudentEnrolls = findStudent(adminClassTemp);
//            List<User> users = new ArrayList<User>();
//            List<Student> students = new ArrayList<Student>();
//            for (StudentEnroll StudentEnroll : StudentEnrolls) {
//                Student student = createStudent(StudentEnroll, adminClass);
//                User user = createUser(student, studentGroup);
//                students.add(student);
//                users.add(user);
//            }
//            entityDao.saveOrUpdate(students);
//            entityDao.saveOrUpdate(users);
        }
    }

    @Override
    public void init() {
        Batch batch = batchService.getLatestBatch();
        String sql = "insert into yxp_student_classes " +
                "  (id, batch_id, enroll_id, student_id) " +
                "  select SEQ_YXP_STUDENT_CLASSES.nextval id, s.batch_id, s.id, s.student_id " +
                "    from yxp_student_enrolls s " +
                "    left join yxp_student_classes sc " +
                "      on s.id = sc.enroll_id " +
                "   where sc.enroll_id is null" +
                "     and s.enrolled = 1" +
                "     and s.batch_id = ?";
        entityDao.executeUpdateSql(sql, batch.getId());
    }

    @Override
    @Deprecated
    public void init(String grade, Long educationId, Department department) {
        if (grade == null) {
            grade = yxxtService.getGrade();
        }
        String sql = "insert into yxp_student_classes" +
                "  (id, student_id)" +
                "  select SEQ_YXP_STUDENT_CLASSES.nextval id, s.id student_id" +
                "    from yxp_student_prepares s" +
                "    left join yxp_student_classes sc" +
                "      on s.id = sc.student_id" +
                "   where s.grade = ?" +
                "     and sc.student_id is null" +
                (educationId == null ? "" : " and s.education_id = " + educationId) +
                (department == null ? "" : " and s.department_id = " + department.getId());
        entityDao.executeUpdateSql(sql, grade);
    }

    private AdminClass createAdminClass(AdminClassTemp adminClassTemp) {
        AdminClass adminClass = entityDao.getEntity(AdminClass.class, "code", adminClassTemp.getCode());
        if (adminClass != null) {
            return adminClass;
        }
        adminClass = new AdminClass();
        adminClass.setCode(adminClassTemp.getCode());
        adminClass.setName(adminClassTemp.getName());
        adminClass.setGrade(adminClassTemp.getGrade() + "");
        adminClass.setDepartment(adminClassTemp.getMajor().getDepartment());
        adminClass.setMajor(adminClassTemp.getMajor());
        adminClass.setDirection(adminClassTemp.getDirection());
        return adminClass;
    }

    private Student createStudent(StudentEnroll studentEnroll, AdminClass adminClass) {
//		Student student = entityDao.getEntity(Student.class, "code", studentEnroll.getCode());
//		if (student != null) {
//			return student;
//		}
//		student = new Student();
//		student.setCode(studentEnroll.getCode());
//		student.setName(studentEnroll.getName());
//		student.setGender(studentEnroll.getGender());
//		student.setEducation(studentEnroll.getEducation());
//		student.setCampus(studentEnroll.getCampus());
//		student.setDepartment(studentEnroll.getDepartment());
//		student.setMajor(studentEnroll.getMajor());
//		student.setDirection(studentEnroll.getDirection());
//		student.setGrade(studentEnroll.getGrade());
//		student.setAdminClass(adminClass);
//		return student;
        return studentEnroll.getStudent();
    }

    private User createUser(Student student, Group studentGroup) {
        User user = entityDao.getEntity(User.class, "name", student.getCode());
        if (user != null) {
            return user;
        }
        user = new UserBean();
        user.setName(student.getCode());
        user.setFullname(student.getName());
        String password = student.getCardcode();
        if (StringUtils.isNotBlank(password)) {
            password = StringUtils.substring(password, -6, password.length());
        } else {
            password = System.currentTimeMillis() + "";
        }
        user.setPassword(EncryptUtil.encode(password));
        user.setCreatedAt(new Date());
        user.setEffectiveAt(new Date());
        user.setDefaultCategory(new CategoryBean(1L));
        user.getCategories().add(user.getDefaultCategory());
        user.setUpdatedAt(new Date());
        user.setEnabled(true);
        if (studentGroup != null) {
            user.getGroups().add(new GroupMemberBean(studentGroup, user, GroupMember.Ship.MEMBER));
        }
        return user;
    }


    private List<StudentEnroll> findStudent(AdminClassTemp adminClass) {
        OqlBuilder query = OqlBuilder.from(StudentClass.class);
        query.select("studentClass.student");
        query.where("studentClass.adminClass = :adminClass", adminClass);
        return entityDao.search(query);
    }

    private List<AdminClassTemp> findAdminClass(String grade, Department department) {
        OqlBuilder<AdminClassTemp> query = OqlBuilder.from(AdminClassTemp.class);
        query.where("grade = :grade", grade);
        query.where("major.department = :department", department);
        return entityDao.search(query);
    }

    private void deleteStudentClass(AdminClassPlan plan) {
        String hql = "delete " + StudentClass.class.getName()
                + " sc where adminClass.id in ( select id from "
                + AdminClassTemp.class.getName() + " where grade = ? and ";
        if (plan.getDirectionId() == null) {
            hql += " major.id = " + plan.getMajorId();
        } else {
            hql += " direction.id = " + plan.getDirectionId();
        }
        hql += ")";
        entityDao.executeUpdateHql(hql, plan.getGrade());
    }

    /**
     * 删除班级
     *
     * @param plan
     */
    private void deleteAdminClass(AdminClassPlan plan) {
        deleteStudentClass(plan);
        String hql = "delete " + AdminClassTemp.class.getName() + " ac where grade = ? and ";
        if (plan.getDirectionId() == null) {
            hql += " major.id = " + plan.getMajorId();
        } else {
            hql += " direction.id = " + plan.getDirectionId();
        }
        entityDao.executeUpdateHql(hql, plan.getGrade());
    }

    /**
     * 创建班级
     *
     * @param plan
     * @param rules
     * @return
     */
    private List<AdminClassTemp> createAdminClassTemp(AdminClassPlan plan, List<AdminClassRule> rules) {
        List<AdminClassTemp> adminClassTemps = new ArrayList<AdminClassTemp>();
        Major major = entityDao.get(Major.class, plan.getMajorId());
        Direction direction = entityDao.get(Direction.class, plan.getDirectionId());
        AdminClassRule codeRule = AdminClassRule.getRule(rules, AdminClassRule.CODE);
        AdminClassRule nameRule = AdminClassRule.getRule(rules, AdminClassRule.NAME);
        for (int i = 1; i <= plan.getNum(); i++) {
            AdminClassTemp adminClassTemp = new AdminClassTemp();
            String num = null;
            if (plan.getNum() < 10) {
                num = i + "";
            } else {
                num = String.format("%02d", i);
            }
            if (AdminClassRule.CODE_1.equals(codeRule.getCode())) {
                adminClassTemp.setCode(plan.getGrade() + major.getCode() + num);
            }
            if (AdminClassRule.NAME_1.equals(nameRule.getCode())) {
                adminClassTemp.setName(plan.getGrade() + major.getName() + num);
            } else if (AdminClassRule.NAME_2.equals(nameRule.getCode())) {
                adminClassTemp.setName(plan.getGrade() + major.getCode() + num);
            }
            adminClassTemp.setGrade(plan.getGrade());
            adminClassTemp.setMajor(major);
            adminClassTemp.setDirection(direction);
            adminClassTemps.add(adminClassTemp);
        }
        return adminClassTemps;
    }

    /**
     * 将学生分配到班级
     *
     * @param adminClasses
     * @param plan
     * @param rules
     */
    private void createStudentClass(List<AdminClassTemp> adminClasses, AdminClassPlan plan, List<AdminClassRule> rules) {
        List<StudentEnroll> students = findStudent(plan);
        sortStudent(students, rules);
        List<StudentClass> studentClasses = new ArrayList<StudentClass>();
        AdminClassRule genderRule = AdminClassRule.getRule(rules, AdminClassRule.GENDER);
        if (AdminClassRule.GENDER_1.equals(genderRule.getCode())) {
            List<DictData> genders = dictDataService.findDictData("GENDER");
            for (DictData gender : genders) {
                createStudentClass(adminClasses, studentClasses, students, gender, rules);
            }
        } else {
            createStudentClass(adminClasses, studentClasses, students);
        }
        entityDao.saveOrUpdate(studentClasses);
    }

    private void createStudentClass(List<AdminClassTemp> adminClasses, List<StudentClass> studentClasses, List<StudentEnroll> allStudents, DictData gender, List<AdminClassRule> rules) {
        List<StudentEnroll> students = new ArrayList<StudentEnroll>();
        for (Iterator<StudentEnroll> itor = allStudents.iterator(); itor.hasNext(); ) {
            StudentEnroll studentEnroll = itor.next();
            if (gender.equals(studentEnroll.getStudent().getGender())) {
                students.add(studentEnroll);
                itor.remove();
            }
        }
        sortStudent(students, rules);
        createStudentClass(adminClasses, studentClasses, students);
    }

    private void createStudentClass(List<AdminClassTemp> adminClasses, List<StudentClass> studentClasses, List<StudentEnroll> students) {
        for (int i = 0; i < students.size(); i++) {
            StudentEnroll student = students.get(i);
            StudentClass studentClass = new StudentClass();
            studentClass.setEnroll(student);
            studentClass.setAdminClass(adminClasses.get(i % adminClasses.size()));
            studentClasses.add(studentClass);
        }
    }

    /**
     * 查询学生
     *
     * @param plan
     * @return
     */
    private List<StudentEnroll> findStudent(AdminClassPlan plan) {
        OqlBuilder query = OqlBuilder.from(StudentEnroll.class);
        query.where("grade = :grade", plan.getGrade());
        query.where("major.id = :majorId", plan.getMajorId());
        if (plan.getDirectionId() == null) {
            query.where("direction is null");
        } else {
            query.where("direction.id = :directionId", plan.getDirectionId());
        }
        return entityDao.search(query);
    }

    private void sortStudent(List<StudentEnroll> students, List<AdminClassRule> rules) {
        final Random random = new Random();
        final AdminClassRule scoreRule = AdminClassRule.getRule(rules, AdminClassRule.SCORE);
        Collections.sort(students, new Comparator<StudentEnroll>() {
            @Override
            public int compare(StudentEnroll o1, StudentEnroll o2) {
                if (scoreRule == null || AdminClassRule.SCORE_1.equals(scoreRule.getCode())) {
                    if (o1.getScoreTotal() == null) {
                        return -1;
                    }
                    if (o2.getScoreTotal() == null) {
                        return 1;
                    }
                    return o1.getScoreTotal().compareTo(o2.getScoreTotal());
                }
                return random.nextInt(3) - 1;
            }
        });
    }

}
