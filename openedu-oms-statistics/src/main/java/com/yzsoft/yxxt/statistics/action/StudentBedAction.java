package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.dorm.model.StudentBed;
import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;

import javax.annotation.Resource;

public class StudentBedAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;

    @Override
    protected String getEntityName() {
        return StudentBed.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("year", yxxtService.getYear());
    }

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        Boolean hasBed = getBoolean("hasBed");
        if (hasBed != null) {
            if (hasBed) {
                query.where("bed is not null");
            } else {
                query.where("bed is null");
            }
        }
    }
}
