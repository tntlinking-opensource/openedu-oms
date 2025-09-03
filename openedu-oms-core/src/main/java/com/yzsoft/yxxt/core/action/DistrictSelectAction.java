package com.yzsoft.yxxt.core.action;

import org.beangle.model.query.builder.OqlBuilder;
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
public class DistrictSelectAction extends EntityDrivenAction {

    @Resource
    private DictDataService dictDataService;

    public String province() {
	    OqlBuilder query = OqlBuilder.from(DictData.class);
	    query.where("dictType.code = 'DISTRICT'");
	    query.where("gbxb like :gbxb", "%0000");
	    query.orderBy("gbxb");
	    query.cacheable();
	    List<DictData> citys = entityDao.search(query);
	    put("datas", citys);
	    return forward("datas");
    }

    public String city() {
        Long provinceId = getLong("id");
        DictData dictData = entityDao.get(DictData.class, provinceId);
        OqlBuilder query = OqlBuilder.from(DictData.class);
        query.where("dictType.code = 'DISTRICT'");
        query.where("gbxb like :gbxb", dictData.getGbxb().substring(0, 2) + "%00");
        query.where("gbxb <> :gbxb2", dictData.getGbxb());
	    query.orderBy("gbxb");
        query.cacheable();
        List<DictData> citys = entityDao.search(query);
        put("datas", citys);
        return forward("datas");
    }

    public String county() {
        Long cityId = getLong("id");
        DictData dictData = entityDao.get(DictData.class, cityId);
        OqlBuilder query = OqlBuilder.from(DictData.class);
        query.where("dictType.code = 'DISTRICT'");
        query.where("gbxb like :gbxb", dictData.getGbxb().substring(0, 4) + "%");
	    query.where("gbxb <> :gbxb2", dictData.getGbxb());
	    query.orderBy("gbxb");
        query.cacheable();
        List<DictData> areas = entityDao.search(query);
        put("datas", areas);
        return forward("datas");
    }

}
