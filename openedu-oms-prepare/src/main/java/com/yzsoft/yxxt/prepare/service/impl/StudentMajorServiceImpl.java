package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.service.StudentMajorService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class StudentMajorServiceImpl extends EntityDaoSupport implements StudentMajorService {
	@Override
	public StudentMajor get(Long userId) {
		StudentMajor studentMajor =  entityDao.getEntity(StudentMajor.class, "student.user.id", userId);
		if (studentMajor == null) {
			studentMajor = new StudentMajor();
		}
		return studentMajor;
	}
}
