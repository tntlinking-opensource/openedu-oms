package com.yzsoft.yxxt.process.action;

import org.beangle.process.action.ProcessAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller
public class EnrollProcessAction extends ProcessAction {

}
