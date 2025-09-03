package com.yzsoft.yxxt.prepare.importer;

import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Student;
import org.springframework.util.Assert;

public class StudentClassImportListener extends ItemImporterListener {

    /**
     * 班级代码，班级名称，身份证号，姓名
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        AdminClass source = (AdminClass) importer.getCurrent();
        Assert.isTrue(source != null && source.getCode() != null, "班级代码不能为空");
        AdminClass adminClass = entityDao.getEntity(AdminClass.class, "code", source.getCode());
        Assert.notNull(adminClass, "未找到代码为：" + source.getCode() + "的班级代码");
        if(adminClass.getNum()==adminClass.getStdNum()){
            tr.addFailure("班级人数已满","");
        }
        Assert.isTrue(source.getStudent() != null && source.getStudent().getCardcode() != null, "身份证号不能为空");
        Student student = entityDao.getEntity(Student.class, "cardcode", source.getStudent().getCardcode());
        Assert.notNull(adminClass, "未找到身份证号码为：" + source.getCode() + "的学生");
        student.setAdminClass(adminClass);
        entityDao.saveOrUpdate(student);
    }

}