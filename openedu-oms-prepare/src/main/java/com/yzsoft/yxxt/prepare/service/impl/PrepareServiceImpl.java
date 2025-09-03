package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.PrepareConfig;
import com.yzsoft.yxxt.prepare.service.PrepareService;
import com.yzsoft.yxxt.prepare.service.StudentPasswordProducer;
import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.CategoryBean;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.model.UserBean;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.product.core.service.StudentService;
import org.beangle.security.codec.EncryptUtil;
import org.beangle.spring.service.SpringService;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class PrepareServiceImpl implements PrepareService {

    @Resource
    private EntityDao entityDao;
    @Resource
    private YxxtService yxxtService;
    @Resource
    private StudentService studentService;
    @Resource
    private SpringService springService;
    @Resource
    private DictDataService dictDataService;

    @Override
    public String getCode() {
        Long nextCode = getNextCode();
        return String.format(Calendar.getInstance().get(Calendar.YEAR) + "%06d", nextCode);
    }

    private Long getNextCode() {
        try {
            String sql = "select SEQ_STUDENT_ENROLL_CODE.nextval from dual";
            List<BigDecimal> list = (List<BigDecimal>) entityDao.searchSqlQuery(sql);
            return list.get(0).longValue();
        } catch (Exception e) {
            String sql = "create sequence SEQ_STUDENT_ENROLL_CODE start with 1 increment by 1";
            entityDao.executeUpdateSql(sql);
            return getNextCode();
        }
    }

    @Override
    public List<String> findGrade() {
        OqlBuilder query = OqlBuilder.from(StudentEnroll.class);
        query.select("grade");
        query.groupBy("grade");
        return entityDao.search(query);
    }

    @Override
    public void createStudent() {
        List<StudentEnroll> students = findEnrollStudent();
        Group group = getStudentGroup();
        for (StudentEnroll student : students) {
            createStudent(student, group);
        }
    }

    private Group getStudentGroup() {
        OqlBuilder query = OqlBuilder.from(Group.class);
        query.where("name = '学生'");
        query.cacheable();
        List<Group> groups = entityDao.search(query);
        return groups.isEmpty() ? null : groups.get(0);
    }

    @Override
    public void createStudent(StudentEnroll student) {
        if (student.getUser() == null) {
            Group group = getStudentGroup();
            createStudent(student, group);
        }
    }

    @Override
    public void removeStudent(StudentEnroll prepareStudent) {
        studentService.remove(prepareStudent.getStudent().getCode());
    }

    @Override
    public StudentEnroll getStudentEnroll(String username) {
        return entityDao.getEntity(StudentEnroll.class, "code", username);
    }

    @Override
    public StudentEnroll getStudentEnroll(Student student) {
        StudentEnroll studentEnroll = entityDao.getEntity(StudentEnroll.class, "student", student);
        if (studentEnroll == null) {
            studentEnroll = new StudentEnroll();
            studentEnroll.setStudent(student);
        }
        return studentEnroll;
    }

    @Override
    public PrepareConfig getConfig() {
        OqlBuilder query = OqlBuilder.from(PrepareConfig.class);
        query.limit(1, 1);
        query.cacheable();
        List<PrepareConfig> configs = entityDao.search(query);
        return configs.isEmpty() ? new PrepareConfig() : configs.get(0);
    }

    @Override
    public List<DictData> findPasswordType() {
        String code = "STUDENT_PASSWORD_PRODUCER";
        List<DictData> dataList = dictDataService.findDictData(code);
        if (dataList.isEmpty()) {
            String name = "学生密码生成方式";
            String[] names = new String[]{"身份证后六位", "学号后四位", "学号后六位"};
            dataList = dictDataService.init(code, name, names);
        }
        return dataList;
    }

    private void createStudent(StudentEnroll studentEnroll, Group group) {
        try {
            String code = studentEnroll.getStudent().getCardcode().toUpperCase();
            User user = entityDao.getEntity(User.class, "name", code);
            if (user == null) {
                user = new UserBean();
                user.setName(code);
                StudentPasswordProducer studentPasswordProducer = getStudentPasswordProducer();
                Assert.notNull(studentPasswordProducer, "学生密码生成规则为空");
                String password = studentPasswordProducer.getPassword(studentEnroll);
//				if (studentPrepare.getCardcode() != null && studentPrepare.getCardcode().length() >= 6) {
//					password = StringUtils.substring(studentPrepare.getCardcode(), -6, 999);
//				} else {
//					password = "30ebce8ad98648f8983b11746b38ea3b";
//				}
                user.setPassword(EncryptUtil.encode(password));
                user.setFullname(studentEnroll.getStudent().getName());
                user.setFullname(StringUtils.substring(user.getFullname(), 0, 50));
                user.setCreatedAt(new Date());
                user.setEffectiveAt(new Date());
                user.setUpdatedAt(new Date());
                user.setDefaultCategory(new CategoryBean(1L));
                user.getCategories().add(user.getDefaultCategory());
                user.getGroups().add(new GroupMemberBean(group, user, GroupMember.Ship.MEMBER));
                user.setEnabled(true);
                entityDao.saveOrUpdate(user);
            }
            if (!user.isEnabled()) {
                user.setEnabled(true);
                entityDao.saveOrUpdate(user);
            }
            Student student = entityDao.getEntity(Student.class, "cardcode", studentEnroll.getStudent().getCardcode());
            if (student == null) {
                student = new Student();
                student.setUser(user);
                BeanUtils.copyProperties(studentEnroll, student, "id");
                Assert.notNull(student.getEducation(), "学历不能为空");
                Assert.notNull(student.getEnrollType(), "招生类别不能为空");
//                entityDao.saveOrUpdate(student);
                StudentInfo studentInfo = new StudentInfo();
//                studentInfo.setStudent(student);
//                studentInfo.setUser(user);
                entityDao.saveOrUpdate(studentInfo);
				student.setInfo(studentInfo);
//                student.getInfos().add(studentInfo);

                student.setGraduateSchool(studentEnroll.getSchoolName());
                if (studentEnroll.getContact() != null) {
                    student.setPhone(studentEnroll.getContact().getPhone());
                }
            }
            if (!student.isInSchooled() || !student.isRegisted()) {
                student.setInSchooled(true);
                student.setRegisted(true);
            }
            student.setUser(user);
            entityDao.saveOrUpdate(student);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private StudentPasswordProducer getStudentPasswordProducer() {
        List<StudentPasswordProducer> studentPasswordProducers = springService.getAll(StudentPasswordProducer.class);
        PrepareConfig config = getConfig();
        String code = config.getPasswordType() == null ? "STUDENT_PASSWORD_PRODUCER_1" : config.getPasswordType().getCode();
        for (StudentPasswordProducer studentPasswordProducer : studentPasswordProducers) {
            if (code.equals(studentPasswordProducer.getCode())) {
                return studentPasswordProducer;
            }
        }
        return studentPasswordProducers.get(0);
    }

    private List<StudentEnroll> findEnrollStudent() {
        Integer year = yxxtService.getYear();
        OqlBuilder<StudentEnroll> query = OqlBuilder.from(StudentEnroll.class);
        query.where("year = :year", year);
        query.where("enrolled = true");
        return entityDao.search(query);
    }
}
