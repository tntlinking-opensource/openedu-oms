package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.service.StudentPasswordProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 身份证后六位
 */
@Service
public class StudentPasswordProducerImpl1 implements StudentPasswordProducer {

    @Override
    public String getCode() {
        return "STUDENT_PASSWORD_PRODUCER_01";
    }

    @Override
    public String getPassword(StudentEnroll studentEnroll) {
        Assert.notNull(studentEnroll.getStudent().getCardcode(), "身份证号码为空");
        Assert.isTrue(studentEnroll.getStudent().getCardcode().length() > 6, "身份证号码不足6位");
        return StringUtils.substring(studentEnroll.getStudent().getCardcode(), -6);
    }

}
