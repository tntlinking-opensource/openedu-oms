package com.yzsoft.yxxt.web.action;

import org.beangle.emsapp.portal.action.HomeAction;
import org.beangle.struts2.action.BaseAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CasAction extends BaseAction {

    public String index() {
        return redirect(new Action(HomeAction.class));
    }

}
