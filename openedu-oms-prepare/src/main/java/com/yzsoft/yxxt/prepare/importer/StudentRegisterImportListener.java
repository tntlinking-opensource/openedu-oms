package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.beangle.model.transfer.TransferResult;
import org.beangle.product.core.model.Student;

public class StudentRegisterImportListener extends StudentEntityImportListener {

    /**
     * 导入模版字段：
     * 学号 姓名 是否注册
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        StudentEnrollImp source = (StudentEnrollImp) importer.getCurrent();
        StudentEnroll studentEnroll = getObject(source);
        Student student = studentEnroll.getStudent();
        student.setRegisted("是".equals(source.getRegistedStr()));
        saveOrUpdate(student);
    }
}