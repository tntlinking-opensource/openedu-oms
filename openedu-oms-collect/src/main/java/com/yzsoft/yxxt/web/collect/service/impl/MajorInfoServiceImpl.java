package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import com.yzsoft.yxxt.web.collect.service.MajorInfoService;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class MajorInfoServiceImpl extends EntityDaoSupport implements MajorInfoService {

	@Resource
	private DictDataService dictDataService;

	@Override
	public List<MajorInfo> find() {
		OqlBuilder query = OqlBuilder.from(MajorInfo.class);
		query.where("enabled = true");
		query.orderBy("isTop desc, sort");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public List<MajorInfo> find(Student student) {
//        System.out.println(gender.getName());
//        System.out.println(enrollType.getName());
		OqlBuilder query = OqlBuilder.from(MajorInfo.class, "majorInfo");
		if (student.getGender() != null) {
			query.where("majorInfo.gender.name = '全部' or majorInfo.gender.name = :gender_name", student.getGender().getName());
		}
		if (student.getEnrollType() != null) {
			query.join("majorInfo.enrollTypes", "enrollType");
			query.where("enrollType = :enrollType", student.getEnrollType());
		}
		if (student.getEducation() != null) {
			query.where("majorInfo.major.education = :education", student.getEducation());
		}
		query.where("majorInfo.enabled = true");
		query.orderBy("majorInfo.isTop desc, majorInfo.sort");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public List<DictData> findGender() {
		String code = "STUDENT_MAJOR_GENDER";
		List<DictData> genders = dictDataService.findDictData(code);
		if (genders.isEmpty()) {
			String name = "专业性别";
			String[] names = new String[]{"全部", "男", "女"};
			genders = dictDataService.init(code, name, names);
		}
		return genders;
	}
}
