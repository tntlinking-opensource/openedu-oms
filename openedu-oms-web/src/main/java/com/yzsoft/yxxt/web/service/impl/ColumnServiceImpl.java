package com.yzsoft.yxxt.web.service.impl;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.service.ColumnService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ColumnServiceImpl extends EntityDaoSupport implements ColumnService {

    @Resource
    private DictDataService dictDataService;

    @Override
    public List<DictData> findLevel() {
        String code = "COLUMN_LEVEL";
        List<DictData> levels = dictDataService.findDictData(code);
        if (levels.isEmpty()) {
            String name = "栏目级别";
            String[] names = new String[]{"一级", "二级"};
            levels = dictDataService.init(code, name, names);
        }
        return levels;
    }

    @Override
    public List<DictData> findType() {
        String code = "COLUMN_TYPE";
        List<DictData> types = dictDataService.findDictData(code);
        if (types.isEmpty()) {
            String name = "栏目类别";
            String[] names = new String[]{"信息发布", "节点", "链接"};
            types = dictDataService.init(code, name, names);
        }
        return types;
    }

    @Override
    public List<Column> findTopColumn() {
        OqlBuilder query = OqlBuilder.from(Column.class);
        query.where("parent is null");
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<Column> findColumn() {
        return findColumn(null);
    }

    @Override
    public List<Column> findColumn(Long columnId) {
        OqlBuilder query = OqlBuilder.from(Column.class);
        if (columnId != null) {
            query.where("parent.id = :columnId", columnId);
        }
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<Column> findContentColumn() {
        OqlBuilder query = OqlBuilder.from(Column.class);
//        query.where("type.name = '信息发布' or type.name = '节点'");
        query.where("type.name = '信息发布'");
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    public List<Column> findContentColumn(Long columnId) {
        OqlBuilder query = OqlBuilder.from(Column.class);
        if (columnId != null) {
            query.where("parent.id = :columnId", columnId);
        } else {
            query.where("parent.id is null");
        }
        query.where("type.name = '信息发布' or type.name = '节点'");
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public Column getContentColumn(Long columnId) {
        Column column = entityDao.get(Column.class, columnId);
        if ("信息发布".equals(column.getType().getName())) {
            return column;
        }
        List<Column> columns = findContentColumn(columnId);
        if (!columns.isEmpty()) {
            column = columns.get(0);
        }
        return column;
    }

    @Override
    public Column getTopColumn(Column column) {
        if (column.getParent() != null) {
            return getTopColumn(column.getParent());
        }
        return column;
    }

    @Override
    public Column getColumn(String code) {
        OqlBuilder query = OqlBuilder.from(Column.class);
        query.where("code = :code", code);
        query.cacheable();
        List<Column> columns = entityDao.search(query);
        return columns.isEmpty() ? null : columns.get(0);
    }
}
