package com.yzsoft.yxxt.web.collect.clothes.service;

import com.yzsoft.yxxt.web.collect.clothes.model.ClothesSize;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudentSize;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;
import org.apache.commons.lang.StringUtils;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.springframework.util.Assert;

public class ClothesImportListener extends ItemImporterListener {

    public ClothesImportListener() {
        super();
    }

    public void onItemFinish(TransferResult tr) {
        ClothesStudentSize source = (ClothesStudentSize) importer.getCurrent();

        Assert.notNull(source.getType(), "服装类别不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getType().getName()), "服装类别不能为空");
        Assert.notNull(source.getSize(), "服装尺码不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getSize().getName()), "服装尺码不能为空");

        ClothesStudent clothesStudent = getObject(source);
        if (clothesStudent == null) {
            clothesStudent = new ClothesStudent();
            clothesStudent.setStudent(entityDao.getEntity(Student.class, "code", source.getStudent().getStudent().getCode()));
        }

        ClothesStudentSize size = getSize(clothesStudent, source);
        if (size == null) {
            size = new ClothesStudentSize();
            size.setStudent(clothesStudent);
            size.setType(get(ClothesType.class, source.getType(), "name", null));
            Assert.notNull(size.getType(), "服装类别“" + source.getType().getName() + "”不存在");
            size.setSize(get(ClothesSize.class, source.getSize(), "name", null));
            Assert.notNull(size.getSize(), "服装尺码“" + source.getSize().getName() + "”不存在");
            clothesStudent.getSizes().add(size);
        }
        saveOrUpdate(clothesStudent);
    }

    private ClothesStudentSize getSize(ClothesStudent clothesStudent, ClothesStudentSize source) {
        for (ClothesStudentSize size : clothesStudent.getSizes()) {
            if (size.getType().getName().equals(source.getType().getName())
                    && size.getSize().getName().equals(source.getSize().getName())) {
                return size;
            }
        }
        return null;
    }

    private ClothesStudent getObject(ClothesStudentSize source) {
        Assert.notNull(source.getStudent(), "学号不能为空");
        Assert.notNull(source.getStudent().getStudent(), "学号不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getStudent().getStudent().getCode()), "学号不能为空");
        ClothesStudent clothesStudent = entityDao.getEntity(ClothesStudent.class, "student.code", source.getStudent().getStudent().getCode());
        return clothesStudent;
    }
}
