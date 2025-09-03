package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.model.FootLink;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.IndexService;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ColumnAction extends EntityDrivenAction {

    @Resource
    private ColumnService columnService;

    @Override
    protected String getEntityName() {
        return Column.class.getName();
    }


    @Override
    protected String getDefaultOrderString() {
        return "code";
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("levels", columnService.findLevel());
        put("types", columnService.findType());
        put("topColumns", columnService.findTopColumn());
    }

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        Long parentId = getLong("column.parent.id");
        if(parentId == null){
            query.where("column.parent is null");
        }
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        return super.saveAndForward(entity);
    }
}
