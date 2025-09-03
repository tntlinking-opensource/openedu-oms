package com.yzsoft.yxxt.mobile.action;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class ContentAction extends BaseAction {

    @Resource
    private ColumnService columnService;
    @Resource
    private ContentService contentService;

    public String index() {
//        Long cid = getLong("cid");
//        List<Column> columns = columnService.findContentColumn(cid);
//        if (cid != null && columns.isEmpty()) {
//            return redirect("search", null, "cid=" + cid);
//        }
//        if (cid != null) {
//            Column column = entityDao.get(Column.class, cid);
//            put("column", column);
//        }
        List<Column> columns = columnService.findContentColumn();
        put("columns", columns);
        return forward();
    }

    public String search() {
        Long cid = getLong("cid");
        Column column = entityDao.get(Column.class, cid);
        put("column", column);
        more();
        List<Content> contents = (List<Content>) getRequest().getAttribute("contents");
        if (contents.size() == 1) {
            return redirect("info", null, "id=" + contents.get(0).getId());
        }
        return forward();
    }

    public String more() {
        Column column = entityDao.get(Column.class, getLong("cid"));
        PageLimit limit = getPageLimit();
        limit.setPageSize(10);
        put("contents", contentService.findContent(column.getId(), limit));
        return forward();
    }

    public String info() {
        Content content = entityDao.get(Content.class, getLong("id"));
        put("column", content.getColumn());
        put("content", content);
        return forward();
    }

}
