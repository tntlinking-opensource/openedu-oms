package com.yzsoft.yxxt.prepare.service;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;

public interface StudentPasswordProducer {

	String getCode();

	String getPassword(StudentEnroll studentEnroll);

}
