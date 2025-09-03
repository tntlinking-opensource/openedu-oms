package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.yxxt.web.collect.action.DormAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormMobileAction extends DormAction {


}
