package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.service.StudentPasswordProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 学号后六位
 */
@Service
public class StudentPasswordProducerImpl3 implements StudentPasswordProducer {

    @Override
    public String getCode() {
        return "STUDENT_PASSWORD_PRODUCER_03";
    }

    @Override
    public String getPassword(StudentEnroll studentEnroll) {
        Assert.notNull(studentEnroll.getStudent().getCode(), "考生号为空");
        Assert.isTrue(studentEnroll.getStudent().getCode().length() > 6, "考生号不足6位");
        return StringUtils.substring(studentEnroll.getStudent().getCode(), -6);
    }

}
