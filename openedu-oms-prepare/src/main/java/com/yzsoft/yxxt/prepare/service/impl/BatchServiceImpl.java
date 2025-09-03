package com.yzsoft.yxxt.prepare.service.impl;

import com.yzsoft.yxxt.prepare.model.Batch;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.common.util.DateUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class BatchServiceImpl extends EntityDaoSupport implements BatchService {

    @Override
    public Batch getBatch() {
        Date now = DateUtil.getDayStart(new Date());
        OqlBuilder query = OqlBuilder.from(Batch.class);
        query.where("startTime <= :now and :now <= endTime", now);
        query.cacheable();
        List<Batch> batches = entityDao.search(query);
        return batches.isEmpty() ? null : batches.get(0);
    }

    @Override
    public Batch getLatestBatch() {
        OqlBuilder query = OqlBuilder.from(Batch.class);
        query.orderBy("startTime desc");
        query.limit(1, 1);
        query.cacheable();
        List<Batch> batches = entityDao.search(query);
        return batches.isEmpty() ? null : batches.get(0);
    }

    @Override
    public List<Batch> findBatch() {
        OqlBuilder query = OqlBuilder.from(Batch.class);
        query.orderBy("startTime desc");
        query.cacheable();
        return entityDao.search(query);
    }

}
