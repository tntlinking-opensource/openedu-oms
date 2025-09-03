package com.yzsoft.yxxt.mobile.info.action;

import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

@Controller
public class IndexAction extends BaseAction {

    public String index() {
        return forward();
    }
}
