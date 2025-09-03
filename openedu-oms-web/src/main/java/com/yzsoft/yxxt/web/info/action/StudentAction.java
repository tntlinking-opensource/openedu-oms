package com.yzsoft.yxxt.web.info.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentHome;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class StudentAction extends SecurityActionSupport {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private StudentService studentService;

    public String index() {
        try {
            Student student = studentService.getStudentByCode(getUsername());
            StudentHome studentHome = studentService.getStudentHome(student.getId());
            put("student", student);
            put("studentHome", studentHome);
            put("systemConfig", yxxtService.getSystemConfig());
            count(student);
            count(studentHome);
            return forward();
        } catch (Exception e) {
            return redirect(new Action(InfoErrorAction.class));
        }
    }

    private void count(Student student) {
        countAdminClass(student);
        countMajor(student);
        countDepartment(student);
    }

    private void countAdminClass(Student student) {
        if(student.getAdminClass() == null){
            return;
        }
        OqlBuilder query = getStudentCountQuery(student);
        query.where("student.adminClass.id = :id", student.getAdminClass().getId());
        put("adminClassNum", entityDao.search(query).get(0));
    }

    private void countMajor(Student student) {
        OqlBuilder query = getStudentCountQuery(student);
        if (student.getMajor() != null) {
            query.where("student.major.id = :id", student.getMajor().getId());
            put("majorNum", entityDao.search(query).get(0));
        }
    }

    private void countDepartment(Student student) {
        OqlBuilder query = getStudentCountQuery(student);
        if (student.getDepartment() != null) {
            query.where("student.department.id = :id", student.getDepartment().getId());
            put("departmentNum", entityDao.search(query).get(0));
        }
    }

    private OqlBuilder getStudentCountQuery(Student student) {
        OqlBuilder query = OqlBuilder.from(Student.class);
        query.select("count(*)");
        query.cacheable();
        return query;
    }

    private void count(StudentHome studentHome) {
        countProvince(studentHome);
        countCity(studentHome);
        countCounty(studentHome);
    }

    private void countProvince(StudentHome studentHome) {
        if (studentHome == null || studentHome.getProvince() == null) {
            return;
        }
        OqlBuilder query = getStudentHomeCountQuery(studentHome);
        query.where("province.id = :id", studentHome.getProvince().getId());
        put("provinceNum", entityDao.search(query).get(0));
    }

    private void countCity(StudentHome studentHome) {
        if (studentHome == null || studentHome.getCity() == null) {
            return;
        }
        OqlBuilder query = getStudentHomeCountQuery(studentHome);
        query.where("city.id = :id", studentHome.getCity().getId());
        put("cityNum", entityDao.search(query).get(0));
    }

    private void countCounty(StudentHome studentHome) {
        if (studentHome == null || studentHome.getCity() == null) {
            return;
        }
        OqlBuilder query = getStudentHomeCountQuery(studentHome);
        query.where("county.id = :id", studentHome.getCounty().getId());
        put("countyNum", entityDao.search(query).get(0));
    }

    private OqlBuilder getStudentHomeCountQuery(StudentHome studentHome) {
        OqlBuilder query = OqlBuilder.from(StudentHome.class);
        query.select("count(*)");
        query.cacheable();
        return query;
    }

    public String students() {
        try{
            Student student = studentService.getStudentByCode(getUsername());
            String scope = get("scope");
            OqlBuilder query = OqlBuilder.from(Student.class, "student");
            if ("adminClass".equals(scope)) {
                query.where("adminClass = :adminClass", student.getAdminClass());
            } else if ("county".equals(scope)) {
                StudentHome home = student.getHome();
                Assert.notNull(home, "家庭信息为空，不法查询同区县校友。");
                query.where("home.county = :county", home.getCounty());
            } else {
                query.where("1 = 2");
            }
            query.where("student <> :student", student);
            query.limit(new PageLimit(1, 300));
            List<Student> students = entityDao.search(query);
            put("students", students);
        }catch (IllegalArgumentException e){
            put("errormsg", e.getMessage());
        }
        return forward();
    }

    public String studentHomes() {
        Student student = studentService.getStudentByCode(getUsername());
        StudentHome home = student.getHome();
        return forward();
    }
}
