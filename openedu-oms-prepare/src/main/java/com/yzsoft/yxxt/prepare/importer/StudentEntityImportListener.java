package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.springframework.util.Assert;

import java.util.List;

public class StudentEntityImportListener extends ItemImporterListener {

    protected StudentEnroll getObject(StudentEnroll source) {
        Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "报名号不能为空");
        Assert.notNull(source.getStudent(), "学生信息不能为空");
        OqlBuilder query = OqlBuilder.from(StudentEnroll.class);
        query.where("code = :code", source.getCode());
        List<StudentEnroll> students = entityDao.search(query);
        Assert.isTrue(!students.isEmpty(), "报名号不存在，报名号：" + source.getCode());
        StudentEnroll studentEnroll = students.get(0);
        Assert.isTrue(studentEnroll.getStudent().getName().equals(source.getStudent().getName()), "报名号或姓名有误");
        return studentEnroll;
    }
}