package com.yzsoft.yxxt.web.view;

import com.opensymphony.xwork2.util.ValueStack;
import com.yzsoft.yxxt.web.service.ColumnService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.struts2.view.component.ClosingUIBean;


public class Head extends ClosingUIBean {

    public Head(ValueStack stack) {
        super(stack);
        ColumnService columnService = getBean(ColumnService.class);
        put("topColumns", columnService.findTopColumn());
        put("userid", SecurityUtils.getUserId());
        put("category", SecurityUtils.getCategory());
    }

}
