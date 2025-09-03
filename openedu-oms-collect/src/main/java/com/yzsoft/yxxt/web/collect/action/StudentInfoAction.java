package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import com.yzsoft.yxxt.web.collect.service.StudentInfoCollectService;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.student.property.model.StdPropertyConfig;
import org.beangle.product.student.property.model.StdPropertyType;
import org.beangle.product.student.property.service.StdPropertyService;
import org.beangle.product.student.service.StudentInfoService;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentInfoAction extends CollectAction {

    @Resource
    private CollectService collectService;
    @Resource
    protected StdPropertyService stdPropertyService;
    @Resource
    protected StudentService studentService;
    @Resource
    protected StudentInfoService studentInfoService;
    @Resource
    protected DictDataService dictDataService;
    @Autowired
    protected StudentInfoCollectService studentInfoCollectService;
    @Autowired
    protected YxxtConfig yxxtConfig;

    @Override
    public String getCode() {
        return "01";
    }

    public String index() {
        return super.index();
    }

    @Override
    protected void indexSetting() {
        //学号会变
//        Student student = studentService.getStudentByCode(getUsername());
        Student student = getStudent();
        put("families", studentInfoService.findFamily(student.getId()));
        put("educations", studentInfoService.findEducation(student.getId()));
        Long educationId = student.getEducation() == null ? null : student.getEducation().getId();
        List<StdPropertyType> types = stdPropertyService.findType(educationId);
        List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
        put("types", types);
        put("configs", configs);
        put("student", student);
        put("studentInfo", student.getInfo());
//        put("studentContact", studentInfoService.getStudentContact(student.getId()));
        put("studentContact", student.getContact());
//        put("studentHome", studentInfoService.getStudentHome(student.getId()));
        put("studentHome", student.getHome());
        put("studentEnroll", entityDao.getEntity(StudentEnroll.class, "student.id", student.getId()));
//        put("studentOther", entityDao.getEntity(StudentOther.class, "student.id", student.getId()));
        put("studentOther", student.getOther());
        put("switch", collectService.getSwitch("01"));
        put("studentInfoStates", studentInfoCollectService.findState(student));
        put("educationEnabled", yxxtConfig.isStudentInfoEducationEnabled());
    }

    public String edit() {
        indexSetting();
        put("genders", dictDataService.findDictData("GENDER"));
        put("politicals", dictDataService.findDictData("ZZMM"));
        put("hyzks", dictDataService.findDictData("HYZK"));
        put("nations", dictDataService.findDictData("MZ"));
        put("healths", dictDataService.findDictData("JKZK"));
        put("enrollSourceTypes", dictDataService.findDictData("STUDENT_ENROLL_SOURCE_TYPE"));
        put("typeId", getLong("typeId"));
        //宗教信仰
        put("faiths", dictDataService.findDictData("FAITH"));
        return super.edit();
    }

    @Override
    public String save() {
//        Student student = populateEntity(Student.class, "student");
//        Assert.isTrue(student.getCode().equals(getUsername()));
        Student student = getStudent();
        populate(student, "student");
        populate(student.getInfo(), "studentInfo");
        populate(student.getContact(), "studentContact");
        populate(student.getHome(), "studentHome");
        populate(student.getOther(), "studentOther");

        StudentEnroll studentEnroll = populateEntity(StudentEnroll.class, "studentEnroll");

        checkStudent(studentEnroll, student);

        beforeSave(null);
        entityDao.saveOrUpdate(student.getInfo());
        entityDao.saveOrUpdate(student.getContact());
        entityDao.saveOrUpdate(student.getHome());
        entityDao.saveOrUpdate(student.getOther());
        entityDao.saveOrUpdate(studentEnroll);
        entityDao.saveOrUpdate(student);
        afterSave();
        return redirect("index");
//        return redirect("editFamily");
    }

    @Override
    protected void afterSave() {
        String code = get("code");
        Assert.notNull(code, "code is null");
        Student student = studentService.getStudentByCode(getUsername());
        studentInfoCollectService.saveState(student, code);
        super.afterSave();
    }

    @Override
    protected boolean finished() {
        Student student = studentService.getStudentByCode(getUsername());
        return studentInfoCollectService.finished(student);
    }

    public String editFamily() {
        Student student = studentService.getStudentByCode(getUsername());
        List<StudentFamily> families = studentInfoService.findFamily(student.getId());
//        if (families.size() < 4) {
//            for (int i = families.size(); i < 4; i++) {
//                families.add(new StudentFamily());
//            }
//        }
        if (families.isEmpty()) {
            families.add(new StudentFamily());
        }
        put("families", families);
        put("familyEmpty", new StudentFamily());
        put("politicals", dictDataService.findDictData("ZZMM"));
        return forward();
    }

    public String saveFamily() {
        Student student = studentService.getStudentByCode(getUsername());
        List<StudentFamily> families = populateList(StudentFamily.class, "family");
        for (Iterator<StudentFamily> iter = families.iterator(); iter.hasNext(); ) {
            StudentFamily family = iter.next();
            if (StringUtils.isEmpty(family.getName())) {
                iter.remove();
            } else {
                if (family.getStudent() == null) {
                    family.setStudent(student);
                }
                studentInfoCollectService.initFamily(family);
                Assert.isTrue(student.equals(family.getStudent()));
            }
        }
        entityDao.remove(studentInfoService.findFamily(student.getId()));
        entityDao.saveOrUpdate(families);
        afterSave();
        return redirect("index");
//        return redirect("editEducation");
    }

    public String editEducation() {
        Student student = studentService.getStudentByCode(getUsername());
        List<StudentEducation> educations = studentInfoService.findEducation(student.getId());
//        if (educations.size() < 3) {
//            for (int i = educations.size(); i < 3; i++) {
//                educations.add(new StudentEducation());
//            }
//        }
        if (educations.isEmpty()) {
            educations.add(new StudentEducation());
        }
        put("educations", educations);
        put("educationEmpty", new StudentEducation());
        return forward();
    }

    public String saveEducation() {
        Student student = studentService.getStudentByCode(getUsername());
        List<StudentEducation> educations = populateList(StudentEducation.class, "education");
        for (Iterator<StudentEducation> iter = educations.iterator(); iter.hasNext(); ) {
            StudentEducation education = iter.next();
            if (StringUtils.isEmpty(education.getUnit())) {
                iter.remove();
            } else {
                if (education.getStudent() == null) {
                    education.setStudent(student);
                }
                Assert.isTrue(student.equals(education.getStudent()));
            }
        }
        entityDao.remove(studentInfoService.findEducation(student.getId()));
        entityDao.saveOrUpdate(educations);
        afterSave();
        return redirect("index");
//      return nextAction();
    }
}
