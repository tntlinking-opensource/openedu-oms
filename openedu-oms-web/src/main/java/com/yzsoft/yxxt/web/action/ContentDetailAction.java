package com.yzsoft.yxxt.web.action;

import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ColumnService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContentDetailAction extends BaseAction {

    @Resource
    private ColumnService columnService;

    public String index() {
        Long contentId = getLong("contentId");
        Content content = entityDao.get(Content.class, contentId);
        Assert.isTrue(content.isEnabled());
        put("content", content);
        put("column", content.getColumn());
        put("topColumn", columnService.getTopColumn(content.getColumn()));
        return forward();
    }

}
