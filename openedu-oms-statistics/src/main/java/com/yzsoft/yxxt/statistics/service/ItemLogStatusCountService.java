package com.yzsoft.yxxt.statistics.service;

import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountDepartment;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountItem;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountMajor;
import com.yzsoft.yxxt.statistics.dto.ItemLogStatusCountProvince;
import org.beangle.process.model.ProcessBatch;

import java.util.List;

public interface ItemLogStatusCountService {

	List<ItemLogStatusCountItem> countItem(ProcessBatch batch);

	List<ItemLogStatusCountDepartment> countMajor(ProcessBatch batch);

	List<ItemLogStatusCountProvince> countProvince(ProcessBatch batch);
}
