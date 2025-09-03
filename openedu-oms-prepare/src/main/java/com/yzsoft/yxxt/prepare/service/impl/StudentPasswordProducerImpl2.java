package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.service.StudentPasswordProducer;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * 学号后四位
 */
@Service
public class StudentPasswordProducerImpl2 implements StudentPasswordProducer {

	@Override
	public String getCode() {
		return "STUDENT_PASSWORD_PRODUCER_02";
	}

	@Override
	public String getPassword(StudentEnroll studentEnroll) {
		Assert.notNull(studentEnroll.getStudent().getCode(), "考生号为空");
		Assert.isTrue(studentEnroll.getStudent().getCode().length() > 4, "考生号不足4位");
		return StringUtils.substring(studentEnroll.getStudent().getCode(), -4);
	}

}
