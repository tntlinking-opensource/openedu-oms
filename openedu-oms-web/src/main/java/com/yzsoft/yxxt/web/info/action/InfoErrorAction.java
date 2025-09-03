package com.yzsoft.yxxt.web.info.action;

import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
public class InfoErrorAction extends BaseAction {

    public String index() {
        put("errormsg", get("errormsg"));
        return forward();
    }
}
