package com.yzsoft.yxxt.web.collect.supplies.service.impl;

import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import com.yzsoft.yxxt.web.collect.supplies.service.SuppliesService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SuppliesServiceImpl implements SuppliesService {

    @Resource
    private EntityDao entityDao;

    @Override
    public List<Supplies> find() {
        OqlBuilder query = OqlBuilder.from(Supplies.class);
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public Map<Long, Supplies> getMap() {
        Map<Long, Supplies> map = new HashMap<Long, Supplies>();
        List<Supplies> suppliess = find();
        for (Supplies supplies : suppliess) {
            map.put(supplies.getId(), supplies);
        }
        return map;
    }

    @Override
    public SuppliesStd get(Long userId) {
        SuppliesStd suppliesStd = entityDao.getEntity(SuppliesStd.class, "student.user.id", userId);
        if (suppliesStd == null) {
            suppliesStd = new SuppliesStd();
        }
        return suppliesStd;
    }

    @Override
    public SuppliesStdItem getItem(Long suppliesId, Long userId) {
        OqlBuilder query = OqlBuilder.from(SuppliesStdItem.class);
        query.where("supplies.id = :suppliesId", suppliesId);
        query.where("suppliesStd.user.id = :userId", userId);
        List<SuppliesStdItem> suppliesStdItems = entityDao.search(query);
        return suppliesStdItems.isEmpty() ? new SuppliesStdItem() : suppliesStdItems.get(0);
    }
}
