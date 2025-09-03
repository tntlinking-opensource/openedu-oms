package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import com.yzsoft.yxxt.web.collect.service.StudentInfoCollectService;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentEducation;
import org.beangle.product.core.model.StudentFamily;
import org.beangle.product.student.property.model.StdPropertyConfig;
import org.beangle.product.student.property.model.StdPropertyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentInfoAction extends com.yzsoft.yxxt.web.collect.action.StudentInfoAction {

    @Resource
    protected StudentInfoCollectService studentInfoCollectService;
    @Autowired
    protected YxxtConfig yxxtConfig;

    @Override
    public String index() {
        Student student = studentService.getStudentByCode(getUsername());
        Long educationId = student.getEducation().getId();
        List<StdPropertyType> types = stdPropertyService.findType(educationId);
        List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
        put("types", types);
        put("switch", getSwitch());
        put("configs", configs);
        put("educationEnabled", yxxtConfig.isStudentInfoEducationEnabled());
        return forward();
    }

    @Override
    public String info() {
        indexSetting();
        StdPropertyType type = entityDao.get(StdPropertyType.class, getLong("id"));
        put("type", type);
        return forward();
    }

    @Override
    public String edit() {
        indexSetting();
        StdPropertyType type = entityDao.get(StdPropertyType.class, getLong("id"));
        put("type", type);
        return super.edit();
    }

    @Override
    public String save() {
        super.save();
        return redirect("info", null, "id=" + get("typeId"));
    }

    public String family() {
        Student student = getStudent();
        put("families", studentInfoService.findFamily(student.getId()));
        put("switch", getSwitch());
        return forward();
    }

    public String familyEdit() {
        StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
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
        afterSave();
        return redirect("family");
    }

    @Override
    protected void afterSave() {
        String code = get("code");
        Assert.notNull(code, "code is null");
        Student student = studentService.getStudentByCode(getUsername());
        studentInfoCollectService.saveState(student, code);
        super.afterSave();
    }

    public String familyInfo() {
        StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
        put("studentFamily", studentFamily);
        return forward();
    }

    public String familyRemove() {
        Student student = getStudent();
        StudentFamily studentFamily = entityDao.get(StudentFamily.class, getLong("id"));
        Assert.isTrue(student.equals(studentFamily.getStudent()));
        entityDao.remove(studentFamily);
        return redirect("family");
    }

    public String education() {
        Student student = getStudent();
        put("educations", studentInfoService.findEducation(student.getId()));
        put("switch", getSwitch());
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

    public String educationRemove() {
        Student student = getStudent();
        StudentEducation studentEducation = entityDao.get(StudentEducation.class, getLong("id"));
        Assert.isTrue(student.equals(studentEducation.getStudent()));
        entityDao.remove(studentEducation);
        return redirect("education");
    }

}
