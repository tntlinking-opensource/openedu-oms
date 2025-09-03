package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;
import org.springframework.util.Assert;

import java.util.List;

public class StudentMajorImportListener extends ItemImporterListener {

    public StudentMajorImportListener() {
        super();
        this.entityDao = getEntityDao();
    }

    /**
     * 学号	姓名	院系代码	院系名称	专业代码	专业名称
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        StudentMajor source = (StudentMajor) importer.getCurrent();
        StudentMajor studentMajor = getObject(source);
        if (studentMajor == null) {
            studentMajor = new StudentMajor();
            StudentEnroll studentEnroll = entityDao.getEntity(StudentEnroll.class, "code", source.getEnroll().getCode());
            Assert.isTrue(studentEnroll != null, "报名号有误");
            studentMajor.setEnroll(studentEnroll);
            studentMajor.setStudent(studentEnroll.getStudent());
//            studentMajor.setUser(studentMajor.getStudent().getUser());
        }
        Assert.isTrue(source.getStudent() != null && studentMajor.getStudent().getName().equals(source.getStudent().getName()), "报名号或姓名有误");
        studentMajor.setMajor(get(Major.class, source.getMajor(), null));
        studentMajor.setWishOrder(StudentMajor.WishOrder.WISH_ORDER_10);
        Assert.notNull(studentMajor.getMajor(), "专业信息有误");
        saveOrUpdate(studentMajor);
    }

    protected StudentMajor getObject(StudentMajor source) {
        Assert.isTrue(source.getEnroll() != null && source.getEnroll().getCode() != null, "报名号不能为空");
        OqlBuilder query = OqlBuilder.from(StudentMajor.class);
        query.where("studentEnroll.code = :code", source.getEnroll().getCode());
        List<StudentMajor> studentMajors = entityDao.search(query);
        return studentMajors.isEmpty() ? null : studentMajors.get(0);
    }
}