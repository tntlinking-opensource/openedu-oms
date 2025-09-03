package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.model.StationDate;
import com.yzsoft.yxxt.web.collect.model.StationReason;
import com.yzsoft.yxxt.web.collect.model.StationTime;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StationServiceImpl implements StationService {

    @Resource
    private EntityDao entityDao;
    @Resource
    private DictDataService dictDataService;

    @Override
    public StationDate getDate() {
        OqlBuilder query = OqlBuilder.from(StationDate.class);
        query.cacheable();
        List<StationDate> list = entityDao.search(query);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public List<Station> findStation() {
        OqlBuilder query = OqlBuilder.from(Station.class);
        query.orderBy("vehicle.name");
        query.orderBy("name");
        query.cacheable();
        List<Station> list = entityDao.search(query);
        return list;
    }

    @Override
    public List<StationReason> findReason() {
        OqlBuilder query = OqlBuilder.from(StationReason.class);
        query.orderBy("name");
        query.cacheable();
        List<StationReason> list = entityDao.search(query);
        return list;
    }

    @Override
    public List<DictData> findVehicle() {
        List<DictData> types = dictDataService.findDictData("JTGJ");
        if (types.isEmpty()) {
            types = initVehicle();
        }
        return types;
    }

    private List<DictData> initVehicle() {
        String code = "JTGJ";
        String name = "交通工具";
        String[] names = new String[]{"火车", "汽车", "飞机", "其它"};
        List<DictData> types = dictDataService.init(code, name, names);
        return types;
    }
}
