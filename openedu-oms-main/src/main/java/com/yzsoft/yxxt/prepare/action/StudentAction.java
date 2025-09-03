package com.yzsoft.yxxt.prepare.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import com.yzsoft.yxxt.prepare.importer.StudentImportListener;
import com.yzsoft.yxxt.prepare.model.StudentContact;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.model.StudentScore;
import com.yzsoft.yxxt.prepare.service.BatchService;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentAction extends StudentActionParent {

    @Resource
    protected YxxtService yxxtService;
    @Resource
    protected DictDataService dictDataService;
    @Resource
    protected StudentService studentService;
    @Resource
    protected BatchService batchService;
    @Resource
    protected UserService userService;
    @Resource
    protected BspBaseService bspBaseService;
    @Resource
    private DepartmentService departmentService;

    @Override
    protected OqlBuilder<?> getOqlBuilder() {
        OqlBuilder<?> query = OqlBuilder.from(getEntityName(), getShortName());
        populateConditions(query);
        List<Department> departs = getDeparts();
        //是否为院系管理员
        if (departs.size() == 1) {
            query.where("student.department in (:depts)", departs);
            put("isDepartmentAdmin", true);
        }else {
            put("isDepartmentAdmin", false);
        }
        QueryHelper.populateIds(query, getShortName() + ".id");
        query.orderBy(getOrderString()).limit(getPageLimit());
        return query;
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
        StudentEnroll studentEnroll = (StudentEnroll) entity;
        if (studentEnroll.getStudent() == null) {
            studentEnroll.setStudent(new Student());
        }
        if (studentEnroll.getContact() == null) {
            studentEnroll.setContact(new StudentContact());
        }
        if (studentEnroll.getScore() == null) {
            studentEnroll.setScore(new StudentScore());
        }
        Student student = studentEnroll.getStudent();
//        if (student.getGrade() == null) {
//            student.setGrade(yxxtService.getGrade());
//        }
        put("provinces", getList(student.getEnrollProvince()));
        put("citys", getList(student.getEnrollCity()));
        put("countys", getList(student.getEnrollCounty()));
        put("contact", studentEnroll.getContact());
        put("score", studentEnroll.getScore());
        put("student", student);
        put("trainTypes", findDictData("TRAIN_TYPE"));

        if (isDeptAdmin()) {
            put("deptAdmin", true);
        }
    }

    private <T> List<T> getList(T t) {
        List<T> list = new ArrayList<T>();
        if (t != null) {
            list.add(t);
        }
        return list;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        StudentImportListener importListener = new StudentImportListener();
        return importListener;
    }

    @Override
    protected Class<?> getImportEntity() {
        return StudentEnrollImp.class;
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        StudentEnroll studentEnroll = (StudentEnroll) entity;
        if (studentEnroll.getBatch() == null) {
            studentEnroll.setBatch(batchService.getLatestBatch());
        }
        Student student = populateEntity(Student.class, "student");
        StudentContact contact = populateEntity(StudentContact.class, "contact");
        StudentScore score = populateEntity(StudentScore.class, "score");
        studentEnroll.setStudent(student);
        studentEnroll.setContact(contact);
        studentEnroll.setScore(score);

        if (student.getId() == null) {
//            student.setInSchooled(false);
            student.setRegisted(false);
        }

//        if (studentEnroll.getCode() == null) {
        studentEnroll.setCode(student.getCode());
//        }
        Assert.isTrue(!entityDao.duplicate(Student.class, student.getId(), "code", student.getCode()), "学号重复");
        Assert.isTrue(!existStudentCode(student), "证件号重复");
        if (null == student.getUser()) {
            String code = student.getCardcode().toUpperCase();
            User userBean = userService.get(code);
            //创建学生用户
            User user = bspBaseService.createNewUser(userBean, code, student.getName(), getCurrentUser(), 1L, "学生");
            student.setUser(user);
        }
        StudentInfo info = student.getInfo();
        StudentOther other = student.getOther();
        StudentHome home = student.getHome();
        org.beangle.product.core.model.StudentContact con = student.getContact();
        entityDao.saveOrUpdate(info, other, home, con);
        student.setInfo(info);
        student.setOther(other);
        student.setContact(con);
        student.setHome(home);

        entityDao.saveOrUpdate(student, contact, score);
        return super.saveAndForward(entity);
    }

    private boolean existStudentCode(Student student) {
        OqlBuilder query = OqlBuilder.from(Student.class);
        query.select("count(*)");
        query.where("cardcode = :cardcode", student.getCardcode());
        if (student.getId() != null) {
            query.where("id <> :id", student.getId());
        }
        Long count = (Long) entityDao.search(query).get(0);
        return count > 0;
    }
}
