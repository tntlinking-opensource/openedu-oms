package com.yzsoft.yxxt.mobile.action;

import com.yzsoft.yxxt.web.ask.model.AskCommon;
import com.yzsoft.yxxt.web.ask.service.AskService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class HelpAction extends BaseAction {

    @Resource
    private AskService askService;

    public String index() {
        more();
        return forward();
    }

    public String more() {
        String key = get("key");
        List<AskCommon> asks = askService.findAskCommon(key, getPageLimit());
        put("asks", asks);
        return forward();
    }

}
