package com.yzsoft.yxxt.web.action;

import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.model.Content;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.struts2.action.BaseAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ContentListAction extends BaseAction {

    @Resource
    private ContentService contentService;
    @Resource
    private ColumnService columnService;

    public String index() {
        Long columnId = getLong("columnId");
        Column column = columnService.getContentColumn(columnId);
        PageLimit limit = getPageLimit();
        limit.setPageSize(10);
        List<Content> contents = contentService.findContent(column.getId(), limit);
        if (limit.getPageNo() == 1 && contents.size() == 1) {
            return redirect(new Action(ContentDetailAction.class, "index", "contentId=" + contents.get(0).getId()));
        }
        put("contents", contents);
        put("column", column);
        put("topColumn", columnService.getTopColumn(column));
        return forward();
    }
}
