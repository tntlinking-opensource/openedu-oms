package com.yzsoft.yxxt.core.action;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Direction;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.service.DirectionService;
import org.beangle.product.core.service.MajorService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AreaSelectAction extends EntityDrivenAction {

    @Resource
    private DictDataService dictDataService;

    public String province() {
        List<DictData> provinces = dictDataService.findDictData("PROVINCE");
        put("datas", provinces);
        return forward("datas");
    }

    public String city() {
        Long provinceId = getLong("id");
        DictData dictData = entityDao.get(DictData.class, provinceId);
        OqlBuilder query = OqlBuilder.from(DictData.class);
        query.where("dictType.code = 'CITY'");
        query.where("gbxb like :gbxb", dictData.getGbxb().substring(0, 2) + "%");
        query.cacheable();
        List<DictData> citys = entityDao.search(query);
        put("datas", citys);
        return forward("datas");
    }

    public String area() {
        Long cityId = getLong("id");
        DictData dictData = entityDao.get(DictData.class, cityId);
        OqlBuilder query = OqlBuilder.from(DictData.class);
        query.where("dictType.code = 'AREA'");
        query.where("gbxb like :gbxb", dictData.getGbxb().substring(0, 4) + "%");
        query.cacheable();
        List<DictData> areas = entityDao.search(query);
        put("datas", areas);
        return forward("datas");
    }

}
