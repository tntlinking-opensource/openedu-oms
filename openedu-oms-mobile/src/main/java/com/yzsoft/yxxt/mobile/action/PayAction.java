package com.yzsoft.yxxt.mobile.action;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * @description:
 * @author: wang hao
 * @create: 2020-09-11 10:54
 */
@Controller
public class PayAction extends BaseAction {

    @Resource
    private ColumnService columnService;
    @Resource
    private ContentService contentService;

    public String more() {
        Column column = columnService.getColumn("0800");
        PageLimit limit = getPageLimit();
        limit.setPageSize(10);
        put("contents", contentService.findContent(column.getId(), limit));
        put("column", column);
        return forward();
    }

    public String index() {
        Long id = getLong("id");
        if (id != null) {
            info();
            return "info";
        }
        more();
        return forward();
    }

    public String info() {
        Content content = entityDao.get(Content.class, getLong("id"));
        put("content", content);
        put("column", content.getColumn());
        return forward();
    }
}
