package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.model.AskCommon;
import com.yzsoft.yxxt.web.ask.service.AskService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ListAction extends BaseAction {

    @Resource
    private AskService askService;

    public String index() {
        String key = get("key");
        if (key != null) {
            key = key.replaceAll("\"", "");
            key = key.replaceAll("<", "");
            key = key.replaceAll(">", "");
        }
        PageLimit limit = getPageLimit();
        List<AskCommon> asks = askService.findAskCommon(key, limit);
        put("asks", asks);
        put("key", key);
        return forward();
    }

}
