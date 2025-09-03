package com.yzsoft.yxxt.web.service;

import com.yzsoft.yxxt.web.model.Column;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface ColumnService {

    List<DictData> findLevel();

    List<DictData> findType();

    List<Column> findTopColumn();
    List<Column> findColumn();
    List<Column> findColumn(Long columnId);
    List<Column> findContentColumn();
    List<Column> findContentColumn(Long columnId);

    Column getContentColumn(Long columnId);

    Column getTopColumn(Column column);

    Column getColumn(String code);

}
