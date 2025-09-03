package com.yzsoft.yxxt.core.service.impl;

import com.yzsoft.yxxt.core.model.SystemConfig;
import com.yzsoft.yxxt.core.service.YxxtService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.model.DictData;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class YxxtServiceImpl implements YxxtService {

    private static String SYSTEM_YEAR = "system.year";

    @Resource
    private EntityDao entityDao;
    @Resource
    private PropertyConfigFactory configFactory;
    @Resource
    protected StudentService studentService;
    @Resource
    protected UserService userService;

    @Override
    public Integer getYear() {
//        PropertyConfigItemBean systemYear = configFactory.getPropertyConfigItemBean(SYSTEM_YEAR);
//        if (null == systemYear) {
//            Integer year = Calendar.getInstance().get(Calendar.YEAR);
//            systemYear = createPropertyConfigItemBean(SYSTEM_YEAR, year.toString(), "java.lang.Integer", "系统年份");
//        }
//        return Integer.parseInt(systemYear.getValue());
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    @Override
    public String getGrade() {
        return getYear().toString();
    }

    @Override
    public void saveYear(Integer systemYear) {
        PropertyConfigItemBean xtnfPcib = configFactory.getPropertyConfigItemBean(SYSTEM_YEAR);
        xtnfPcib.setValue(systemYear.toString());
        entityDao.saveOrUpdate(xtnfPcib);
        configFactory.reload();//更新完后重新加载系统参数
    }

    @Override
    public User getUser(Student student) {
        User user = student.getUser();
        if (user != null) {
            return user;
        }
        user = entityDao.getEntity(User.class, "name", student.getCode());
        if (user == null && student.getCardcode() != null) {
            user = entityDao.getEntity(User.class, "name", student.getCardcode().toUpperCase());
        }
        student.setUser(user);
        entityDao.saveOrUpdate(student);
        return user;
    }

    @Override
    public SystemConfig getSystemConfig() {
        OqlBuilder query = OqlBuilder.from(SystemConfig.class);
        query.cacheable();
        List<SystemConfig> list = entityDao.search(query);
        if (list.isEmpty()) {
            SystemConfig systemConfig = new SystemConfig();
            systemConfig.setYear(Calendar.getInstance().get(Calendar.YEAR));
            systemConfig.setDepartmentName("院系");
            return systemConfig;
        }
        return list.get(0);
    }

    @Override
    public Student getStudent() {
        return getStudent(SecurityUtils.getUserId(), SecurityUtils.getUsername());
    }

    @Override
    public Student getStudent(Long userId, String username) {
        Student student = getStudentByUserId(userId);
        if (student == null) {
            student = getStudentByCardcode(username);
        }
        return student;
    }

    public Student getStudentByUserId(Long userId) {
        OqlBuilder query = OqlBuilder.from(Student.class, "o");
        query.where("o.user.id = :userId", userId);
        query.cacheable();
        List<Student> students = entityDao.search(query);
        return students.isEmpty() ? null : students.get(0);
    }

    public Student getStudentByCardcode(String cardcode) {
        OqlBuilder query = OqlBuilder.from(Student.class, "o");
        query.where("o.cardcode = :cardcode", cardcode);
        query.cacheable();
        List<Student> students = entityDao.search(query);
        return students.isEmpty() ? null : students.get(0);
    }

    @Override
    public List<Student> findStudentByCity(Student student) {
        OqlBuilder query = OqlBuilder.from(Student.class, "o");
        query.where("o.id != :id", student.getId());
        query.where("o.grade = :grade", student.getGrade());
        query.where("o.enrollCity = :enrollCity", student.getEnrollCity());
        query.orderBy("o.name");
        return entityDao.search(query);
    }

    private PropertyConfigItemBean createPropertyConfigItemBean(String name, String value, String type, String description) {
        PropertyConfigItemBean pcib = new PropertyConfigItemBean();
        pcib.setName(name);
        pcib.setType(type);
        pcib.setValue(value);
        pcib.setDescription(description);
        entityDao.saveOrUpdate(pcib);
        return pcib;
    }

    @Override
    public String createXh(Student student) {
        if (student == null) {
            return null;
        }
        if (StringUtils.isNotEmpty(student.getCode())) {
            return student.getCode();
        }
//    	String sql = "select max(substr(serial,-2)) from cp_students where grade = " + student.getGrade();
//    	List list = entityDao.searchSqlQuery(sql);
//    	//
//    	String year = student.getGrade().substring(2);
//    	StringBuffer serial = new StringBuffer();
//    	serial.append(year);
//    	int i = 0;
//    	if(!CollectionUtils.isEmpty(list)){
//    		i = NumberUtils.toInt((String)list.get(0));
//		}
//    	i++;
//    	serial.append(StringUtils.repeat("0", 4 - String.valueOf(i).length()) + i);
//    	student.setSerial(serial.toString());

        //生成学号
        AdminClass bj = entityDao.get(AdminClass.class, student.getAdminClass().getId());

        //通过年份、院系、专业、班级，获取最大的个人序号
        String sql = "select max(substr(code,-2)) from cp_students where admin_class_id = " + bj.getId();
        List list = entityDao.searchSqlQuery(sql);
        StringBuffer xh = new StringBuffer();
        long j = 0;
        if (!CollectionUtils.isEmpty(list)) {
            j = NumberUtils.toInt((String) list.get(0));
        }
        j++;
        xh.append(bj.getCode());
        xh.append(StringUtils.repeat("0", 2 - String.valueOf(j).length()) + j);
        student.setCode(xh.toString());
        entityDao.saveOrUpdate(student);

        return xh.toString();

    }

}
