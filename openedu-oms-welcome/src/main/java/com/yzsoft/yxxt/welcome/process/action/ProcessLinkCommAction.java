package com.yzsoft.yxxt.welcome.process.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ProcessLinkCommAction extends ProcessLinkActionSupport {

}
