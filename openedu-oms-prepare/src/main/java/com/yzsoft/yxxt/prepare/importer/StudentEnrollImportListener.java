package com.yzsoft.yxxt.prepare.importer;

import com.yzsoft.yxxt.prepare.bean.StudentEnrollImp;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.beangle.model.transfer.TransferResult;
import org.springframework.util.Assert;

public class StudentEnrollImportListener extends StudentEntityImportListener {

    /**
     * 导入模版字段：
     * 年份	学号	姓名	是否通知
     *
     * @param tr
     */
    public void onItemFinish(TransferResult tr) {
        StudentEnrollImp source = (StudentEnrollImp) importer.getCurrent();
        StudentEnroll studentEnroll = getObject(source);
        studentEnroll.setNoticed(source.getNoticed());
        studentEnroll.setEnrolled(source.getEnrolled());
        if (source.getEnrolled()) {
            studentEnroll.setNoticed(true);
        }
        saveOrUpdate(studentEnroll);
//        if (student.getEnrolled() != null && student.getEnrolled()) {
//            prepareService.createStudent(student);
//        }
    }
}