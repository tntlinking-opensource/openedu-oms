package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RegisteredAction extends ProcessLinkActionSupport {

    @Resource
    private CollectService collectService;
    @Resource
    private YxxtService yxxtService;

    @Override
    protected void editSetting(Student student) {
        super.editSetting(student);
        User user = yxxtService.getUser(student);
        put("switches", collectService.findSwitch());
        put("states", collectService.findState(user.getId()));
    }
}
