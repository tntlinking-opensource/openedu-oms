package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.web.collect.model.ClothesSize;
import com.yzsoft.yxxt.web.collect.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.model.ShoesSize;
import com.yzsoft.yxxt.web.collect.service.ClothesService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClothesServiceImpl implements ClothesService {

	@Resource
	private EntityDao entityDao;

	@Override
	public List<ClothesSize> findClothesSize() {
		OqlBuilder query = OqlBuilder.from(ClothesSize.class);
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public List<ShoesSize> findShoesSize() {
		OqlBuilder query = OqlBuilder.from(ShoesSize.class);
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public ClothesStudent get(Long userId) {
		ClothesStudent clothesStudent = entityDao.getEntity(ClothesStudent.class, "student.user.id", userId);
		return clothesStudent;
	}
}
