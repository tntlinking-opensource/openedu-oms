package com.yzsoft.yxxt.finance.service;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudent;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudentItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;

import org.beangle.website.system.model.DictData;

import java.util.List;

public interface FinanceGreenService {

	List<FinanceGreen> find();

	List<FinanceGreen> find(DictData education);

	FinanceGreenStudent getFinanceGreenStudent(Long studentId);

	List<FinanceGreenStudentItem> find(Long batchId, Long studentId);

	void save(Long batchId, Long studentId, List<FinanceGreenStudentItem> financeGreenStudents, String remark,FinanceStudent financeStudent);
}
