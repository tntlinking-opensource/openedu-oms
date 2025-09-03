package com.yzsoft.yxxt.web.info.action;

import com.yzsoft.yxxt.web.finance.action.FinanceOrderInfoAction;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceAction extends FinanceOrderInfoAction {
}
