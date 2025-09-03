package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.web.collect.supplies.action.SuppliesInfoParentAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesInfoAction extends SuppliesInfoParentAction {

}
