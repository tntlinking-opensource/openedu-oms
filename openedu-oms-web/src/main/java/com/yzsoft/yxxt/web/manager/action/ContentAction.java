package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ColumnService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Date;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContentAction extends SecurityActionSupport {

    @Resource
    private ColumnService columnService;

    @Override
    protected String getEntityName() {
        return Content.class.getName();
    }


    @Override
    protected String getDefaultOrderString() {
        return "createTime desc";
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
    }

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        Long columnId = null;
        Long column2 = getLong("column2");
        if (column2 != null) {
            columnId = column2;
        } else {
            Long column1 = getLong("column1");
            if (column1 != null) {
                columnId = column1;
            }
        }
        if (columnId != null) {
            Column column = entityDao.get(Column.class, columnId);
            if(column.getColumns().size() > 0){
                query.where("column.parent.id = :columnid", columnId);
            }else{
                query.where("column.id = :columnid", columnId);
            }
        }
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        Content content = (Content) entity;
        Assert.notNull(content.getColumn());
        if (content.getCreateTime() == null) {
            content.setCreateTime(new Date());
        }
        if (content.getUser() == null) {
            content.setUser(getCurrentUser());
        }
        return super.saveAndForward(entity);
    }

    public String findColumn() {
        put("datas", columnService.findColumn());
        return forward();
    }
}
