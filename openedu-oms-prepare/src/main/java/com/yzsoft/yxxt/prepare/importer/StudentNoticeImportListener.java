package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import com.yzsoft.yxxt.prepare.service.PrepareService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.springframework.util.Assert;

import java.util.List;

@Deprecated
public class StudentNoticeImportListener extends ItemImporterListener {

    protected PrepareService prepareService;
    private String grade = null;

    public StudentNoticeImportListener() {
        super();
        this.entityDao = getEntityDao();
        prepareService = getBean(PrepareService.class);
        YxxtService yxxtService = getBean(YxxtService.class);
        grade = yxxtService.getGrade();
    }

    /**
     * 导入模版字段：
     * 年份	学号	姓名	是否通知
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        StudentEnrollImp source = (StudentEnrollImp) importer.getCurrent();
        StudentEnroll student = getObject(source);
        student.setNoticed(source.getNoticed());
        saveOrUpdate(student);
    }

    protected StudentEnroll getObject(StudentEnroll source) {
//        Assert.isTrue(source.getGrade() != null, "年级不能为空");
        Assert.notNull(source.getStudent(), "学号不能为空");
        Assert.isTrue(StringUtils.isNotBlank(source.getStudent().getCode()), "学号不能为空");
        OqlBuilder query = OqlBuilder.from(StudentEnroll.class);
//        query.where("grade = :grade", source.getGrade());
//        query.where("grade = :grade", grade);
        query.where("student.code = :code", source.getStudent().getCode());
        List<StudentEnroll> students = entityDao.search(query);
        Assert.isTrue(!students.isEmpty(), "学生信息不存在，学号：" + source.getStudent().getCode());
        return students.get(0);
    }
}