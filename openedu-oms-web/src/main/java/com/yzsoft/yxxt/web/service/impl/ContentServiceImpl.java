package com.yzsoft.yxxt.web.service.impl;

import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ContentService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentServiceImpl extends EntityDaoSupport implements ContentService {

    @Override
    public List<Content> findContent(Long columnId, PageLimit limit) {
        OqlBuilder query = OqlBuilder.from(Content.class);
        query.where("column.id = :columnId", columnId);
        query.where("enabled = true");
        query.limit(limit);
        query.orderBy("stick desc, publishTime desc");
        return entityDao.search(query);
    }

}
