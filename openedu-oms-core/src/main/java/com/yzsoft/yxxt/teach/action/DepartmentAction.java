package com.yzsoft.yxxt.teach.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DepartmentAction extends org.beangle.product.core.action.DepartmentAction {
}
