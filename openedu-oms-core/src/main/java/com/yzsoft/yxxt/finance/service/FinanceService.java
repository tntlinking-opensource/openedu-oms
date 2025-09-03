package com.yzsoft.yxxt.finance.service;

import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface FinanceService {

	List<Integer> findYear();

	List<FinanceItem> findItem();

	List<FinanceTemplate> findTempalte();

	/**
	 * 复制上一年度的模板
	 */
	void copy();
}
