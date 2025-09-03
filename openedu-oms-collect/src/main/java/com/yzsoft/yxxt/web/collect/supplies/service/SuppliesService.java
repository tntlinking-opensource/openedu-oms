package com.yzsoft.yxxt.web.collect.supplies.service;

import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;

import java.util.List;
import java.util.Map;

public interface SuppliesService {

	List<Supplies> find();

	Map<Long, Supplies> getMap();

	SuppliesStd get(Long userId);

	SuppliesStdItem getItem(Long suppliesId, Long userId);
}
