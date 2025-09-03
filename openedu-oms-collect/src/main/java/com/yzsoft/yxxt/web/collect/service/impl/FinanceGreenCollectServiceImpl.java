package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.FinanceGreenCollectService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.springframework.stereotype.Service;

@Service
public class FinanceGreenCollectServiceImpl extends EntityDaoSupport implements FinanceGreenCollectService {

	@Override
	public FinanceGreenStd get(Long userId) {
		return entityDao.getEntity(FinanceGreenStd.class, "student.user.id", userId);
	}
}
