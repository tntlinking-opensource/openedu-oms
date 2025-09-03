package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IndexAction extends SecurityActionSupport {

    @Resource
    private CollectService collectService;

    public String index() {
        List<CollectSwitch> switches = collectService.findSwitch();
        try {
            if (switches.isEmpty()) {
                getResponse().sendError(404);
            }
            getResponse().sendRedirect(getRequest().getContextPath() + switches.get(0).getUrl() + ".action");
        } catch (Exception e) {

        }
        return null;
    }
}
