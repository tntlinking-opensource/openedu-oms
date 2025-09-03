package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import com.yzsoft.yxxt.finance.service.FinanceService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinanceServiceImpl implements FinanceService {

	@Resource
	private EntityDao entityDao;
	@Resource
	private YxxtService yxxtService;

	@Override
	public List<FinanceItem> findItem() {
		OqlBuilder<FinanceItem> query = OqlBuilder.from(FinanceItem.class);
		query.where("enabled = true");
		query.cacheable();
		query.orderBy("code");
		return entityDao.search(query);
	}

	@Override
	public List<Integer> findYear() {
		OqlBuilder query = OqlBuilder.from(FinanceTemplate.class);
		query.select("year");
		query.where("enabled = true");
		query.orderBy("year");
		query.groupBy("year");
		return entityDao.search(query);
	}

	@Override
	public void copy() {
		Integer year = yxxtService.getYear();
		List<FinanceTemplate> templates = findTempalte(year - 1);
		List<FinanceTemplate> newTemplates = new ArrayList<FinanceTemplate>();
		for (FinanceTemplate template : templates) {
			FinanceTemplate newTemplate = template.copy();
			newTemplate.setYear(year);
			newTemplates.add(newTemplate);
		}
		entityDao.saveOrUpdate(newTemplates);
	}

	public List<FinanceTemplate> findTempalte() {
		return findTempalte(yxxtService.getYear());
	}

	public List<FinanceTemplate> findTempalte(int year) {
		OqlBuilder query = OqlBuilder.from(FinanceTemplate.class);
		query.where("enabled = true");
		query.where("year = :year", year);
		query.cacheable();
		return entityDao.search(query);
	}
}
