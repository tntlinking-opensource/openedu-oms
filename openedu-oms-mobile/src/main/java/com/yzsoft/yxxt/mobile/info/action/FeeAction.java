package com.yzsoft.yxxt.mobile.info.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FeeAction extends com.yzsoft.yxxt.web.info.action.FeeAction {
}
