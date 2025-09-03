package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import com.yzsoft.yxxt.prepare.model.*;
import com.yzsoft.yxxt.prepare.model.StudentContact;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.util.List;

public class StudentImportListener extends ItemImporterListener {

    private BatchService batchService;
    private Batch batch;
	protected UserService userService;
	protected BspBaseService bspBaseService;

    public StudentImportListener() {
        super();
        this.entityDao = getEntityDao();
        this.dictDataService = getBean(DictDataService.class);
        this.batchService = getBean(BatchService.class);
        batch = batchService.getLatestBatch();

        this.userService = getBean(UserService.class);
        this.bspBaseService = getBean(BspBaseService.class);
    }

    /**
     * 导入模版字段：
     * 年份	学号	姓名	身份证号	性别	出生年月	毕业学校	省份	地级市	区县
     * 父亲电话	母亲电话	学生手机	通讯电话	通讯地址	通讯地址邮编	邮箱
     * 语文	数学	英语	物理	化学	生物	政治	地理	历史	体育	艺术	加分     *
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        Assert.notNull(batch, "招生批次不能为空");
        StudentEnrollImp source = (StudentEnrollImp) importer.getCurrent();
        Student sourceStudent = source.getStudent();
//        Assert.notNull(source.getStudent().getCode(), "学号不能为空");
        Assert.notNull(source.getStudent().getGender(), "性别不能为空");
        Assert.notNull(source.getStudent().getEnrollType(), "招生类别不能为空");
        Assert.notNull(source.getStudent().getEducation(), "学历层次不能为空");
        Assert.notNull(source.getStudent().getDepartment(), "院系不能为空");
        StudentEnroll studentEnroll = getObject(source);
        if (studentEnroll == null) {
            studentEnroll = new StudentEnroll();
            studentEnroll.setContact(new StudentContact());
            studentEnroll.setScore(new StudentScore());
            studentEnroll.setStudent(new Student());
            studentEnroll.getStudent().setRegisted(false);
        }
        if (source.getStudent() != null) {
//            copyPropertiesIgnoreNull(source.getStudent(), studentEnroll.getStudent());
            BeanUtils.copyProperties(source.getStudent(), studentEnroll.getStudent(),
                    ArrayUtils.addAll(getNullPropertyNames(source.getStudent()),
                            new String[]{"families", "contacts", "homes", "infos", "others"}));
        }
        if (source.getContact() != null) {
            copyPropertiesIgnoreNull(source.getContact(), studentEnroll.getContact());
        }
        if (source.getScore() != null) {
            copyPropertiesIgnoreNull(source.getScore(), studentEnroll.getScore());
        }
        Student student = studentEnroll.getStudent();
        source.setContact(null);
        source.setScore(null);
        copyPropertiesIgnoreNull(source, studentEnroll);
        student.setGraduateSchool(sourceStudent.getGraduateSchool());

        studentEnroll.setBatch(batch);
        studentEnroll.setCode(studentEnroll.getStudent().getCode());

        //student.setGraduateSchool(studentEnroll.getSchoolName());
        student.setGender(getDictData(sourceStudent.getGender(), StudentService.GENDER, student.getGender()));
        student.setEnrollType(getDictData(sourceStudent.getEnrollType(), StudentService.ENROLL_TYPE, student.getEnrollType()));
        student.setEnrollSourceType(getDictData(sourceStudent.getEnrollSourceType(), "STUDENT_ENROLL_SOURCE_TYPE", student.getEnrollSourceType()));
        student.setEducation(getDictData(sourceStudent.getEducation(), StudentService.XLCC, student.getEducation()));
        student.setEnrollProvince(getDictData(sourceStudent.getEnrollProvince(), StudentService.PROVINCE, student.getEnrollProvince()));
        student.setEnrollCity(getDictData(sourceStudent.getEnrollCity(), StudentService.CITY, student.getEnrollCity()));
        student.setEnrollCounty(getDictData(sourceStudent.getEnrollCounty(), StudentService.COUNTY, student.getEnrollCounty()));
        student.setTrainType(getDictData(sourceStudent.getTrainType(), "TRAIN_TYPE", student.getTrainType()));//培养类型

        if (sourceStudent.getDepartment() != null && sourceStudent.getDepartment().getName() != null) {
            Department department = get(Department.class, sourceStudent.getDepartment(), "name", null);
            Assert.notNull(department, "院系“" + sourceStudent.getDepartment().getName() + "”不存在");
            sourceStudent.setDepartment(null);
            student.setDepartment(department);
        }
        if (sourceStudent.getMajor() != null && sourceStudent.getMajor().getName() != null) {
            Major major = get(Major.class, sourceStudent.getMajor(), "name", null);
            Assert.notNull(major, "专业“" + sourceStudent.getMajor().getName() + "”不存在");
            sourceStudent.setMajor(null);
            student.setMajor(major);
        }
        if (sourceStudent.getDirection() != null && sourceStudent.getDirection().getName() != null) {
            Direction direction = get(Direction.class, sourceStudent.getDirection(), "name", null);
            Assert.notNull(direction, "专业方向“" + sourceStudent.getDirection().getName() + "”不存在");
            sourceStudent.setDirection(null);
            student.setDirection(direction);
        }
        Assert.isTrue(student.getGender() != null, "性别“" + sourceStudent.getGender().getName() + "”不存在");
        if (sourceStudent.getEnrollProvince() != null) {
            Assert.isTrue(student.getEnrollProvince() != null, "省份“" + sourceStudent.getEnrollProvince().getName() + "”不存在");
        }
        if (sourceStudent.getEnrollCity() != null) {
            Assert.isTrue(student.getEnrollCity() != null, "城市“" + sourceStudent.getEnrollCity().getName() + "”不存在");
        }
        if (sourceStudent.getEnrollCounty() != null) {
            Assert.isTrue(student.getEnrollCounty() != null, "区县“" + sourceStudent.getEnrollCounty().getName() + "”不存在");
        }
        if (sourceStudent.getCampus() != null && sourceStudent.getCampus().getName() != null) {
            Campus campus = get(Campus.class, sourceStudent.getCampus(), "name", null);
            Assert.notNull(campus, "校区" + sourceStudent.getCampus().getName() + "”不存在");
            sourceStudent.setDepartment(null);
            student.setCampus(campus);
        }
        Assert.notNull(student.getEnrollType(), "招生类别“" + sourceStudent.getEnrollType().getName() + "”有误");
        Assert.notNull(student.getEducation(), "学历层次“" + sourceStudent.getEducation().getName() + "”有误");
        Assert.isTrue(!existStudentCode(student), "身份证号重复：" + student.getCardcode());
        
        String code = student.getCardcode().toUpperCase();
		User userBean = userService.get(code);
		User cur = entityDao.get(User.class, SecurityUtils.getUserId());
		//创建学生用户
		User user = bspBaseService.createNewUser(userBean,code, student.getName(), cur, 1L, "学生");
		student.setUser(user);
		studentEnroll.setUser(user);
//        student.setRegisted(false);

        StudentInfo info = student.getInfo();
        if (studentEnroll.getScoreTotal() != null) {
            info.setMidExamScore(studentEnroll.getScoreTotal().doubleValue());
        }
        //20201105新增字段民族 begin
        if (source.getStudentInfo().getNation() != null && 
        		source.getStudentInfo().getNation().getName() != null) {
        	DictData dictData = get(DictData.class, source.getStudentInfo().getNation(), "name", null);
            Assert.notNull(dictData, "民族" + source.getStudentInfo().getNation().getName() + "”不存在");
            info.setNation(dictData);
        }
        //20201105新增字段民族 end
        
        StudentOther other = student.getOther();
        StudentHome home = student.getHome();
        org.beangle.product.core.model.StudentContact con = student.getContact();

        entityDao.saveOrUpdate(info,other,home,con, student);
        student.setInfo(info);
        student.setOther(other);
        student.setContact(con);
        student.setHome(home);
        entityDao.saveOrUpdate(student);
        entityDao.saveOrUpdate(studentEnroll.getContact());
        entityDao.saveOrUpdate(studentEnroll.getScore());
        saveOrUpdate(studentEnroll);
    }

    private StudentEnroll getObject(StudentEnroll source) {
        Assert.isTrue(StringUtils.isNotBlank(source.getStudent().getCardcode()), "身份证号不能为空");
        OqlBuilder query = OqlBuilder.from(StudentEnroll.class);
        query.where("batch = :batch", batch);
        query.where("student.cardcode = :cardcode", source.getStudent().getCardcode());
        List<StudentEnroll> studentEnrolls = entityDao.search(query);
        return studentEnrolls.isEmpty() ? null : studentEnrolls.get(0);
    }

    private boolean existStudentCode(Student student) {
        OqlBuilder query = OqlBuilder.from(Student.class);
        query.select("count(*)");
        query.where("cardcode = :cardcode1", student.getCardcode());
        if (student.getId() != null) {
            query.where("id <> :id", student.getId());
        }
        Long count = (Long) entityDao.search(query).get(0);
        return count > 0;
    }
}