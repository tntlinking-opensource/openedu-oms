package com.yzsoft.yxxt.web.collect.clothes.service.impl;

import com.yzsoft.yxxt.finance.service.FinanceOrderService;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesStudent;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;
import com.yzsoft.yxxt.web.collect.clothes.service.ClothesService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClothesServiceImpl extends EntityDaoSupport implements ClothesService {

	@Override
	public List<ClothesType> findType() {
		OqlBuilder query = OqlBuilder.from(ClothesType.class);
		query.where("enabled = true");
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public ClothesStudent getClothesStudent(Long userId) {
		ClothesStudent clothesStudent =  entityDao.getEntity(ClothesStudent.class, "student.user.id", userId);
		if(clothesStudent == null){
			clothesStudent = new ClothesStudent();
		}
		return clothesStudent;
	}

}
