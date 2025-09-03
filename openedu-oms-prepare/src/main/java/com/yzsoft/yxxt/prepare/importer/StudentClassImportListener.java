package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.AdminClassTemp;
import com.yzsoft.yxxt.prepare.model.StudentClass;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Major;
import org.springframework.util.Assert;

import java.util.List;

public class StudentClassImportListener extends ItemImporterListener {

    private YxxtService yxxtService;
    private String grade;

    public StudentClassImportListener() {
        super();
        this.entityDao = getEntityDao();
        this.yxxtService = getBean(YxxtService.class);
        grade = yxxtService.getGrade();
    }

    /**
     * 学号,学号,姓名,院系代码,院系名称,专业代码,专业名称,班级代码,班级名称
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        StudentClass source = (StudentClass) importer.getCurrent();
        StudentClass studentClass = getObject(source);
        if (studentClass == null) {
            studentClass = new StudentClass();
            StudentEnroll studentEnroll = entityDao.getEntity(StudentEnroll.class, "code", source.getStudent().getCode());
            Assert.notNull(studentEnroll, "学号有误");
            studentClass.setEnroll(studentEnroll);
            studentClass.setStudent(studentEnroll.getStudent());
        }
        AdminClassTemp adminClass = getAdminClass(source);
        studentClass.setAdminClass(adminClass);
        saveOrUpdate(studentClass);
    }

    private AdminClassTemp getAdminClass(StudentClass source) {
        Assert.isTrue(source.getAdminClass() != null && source.getAdminClass().getCode() != null, "班级代码不能为空");
        AdminClassTemp adminClass = get(AdminClassTemp.class, source.getAdminClass(), "code", null);
        if (adminClass == null) {
            adminClass = new AdminClassTemp();
            Assert.isTrue(source.getAdminClass() != null && source.getAdminClass().getName() != null, "班级名称不能为空");
            Assert.isTrue(source.getStudent().getMajor() != null && source.getStudent().getMajor().getCode() != null, "专业代码不能为空");
            Major major = get(Major.class, source.getStudent().getMajor(), "code", null);
            Assert.notNull(major, "专业代码有误");
            adminClass.setGrade(grade);
            adminClass.setCode(source.getAdminClass().getCode());
            adminClass.setName(source.getAdminClass().getName());
            adminClass.setMajor(major);
            entityDao.saveOrUpdate(adminClass);
        }
        return adminClass;
    }

    protected StudentClass getObject(StudentClass source) {
        Assert.isTrue(source.getStudent() != null && source.getStudent().getCode() != null, "学号不能为空");
        OqlBuilder query = OqlBuilder.from(StudentClass.class);
        query.where("student.code = :code", source.getStudent().getCode());
        List<StudentClass> studentClasses = entityDao.search(query);
        return studentClasses.isEmpty() ? null : studentClasses.get(0);
    }
}