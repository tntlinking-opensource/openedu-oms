package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.ImgLink;
import com.yzsoft.yxxt.web.model.WelcomeLink;
import com.yzsoft.yxxt.web.service.IndexService;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.service.UploadFileService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WelcomeLinkAction extends EntityDrivenAction {

    @Resource
    private IndexService indexService;

    @Override
    protected String getEntityName() {
        return WelcomeLink.class.getName();
    }

    @Override
    protected String getDefaultOrderString() {
        return "sort";
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        WelcomeLink link = (WelcomeLink) entity;
        if (link.getSort() == null) {
            link.setSort(999);
        }
        return super.saveAndForward(entity);
    }


    public String sort() {
        put("welcomeLinks", indexService.findWelcomeLink());
        return forward();
    }

    public String sortSave() {
        List<WelcomeLink> list = populateList(WelcomeLink.class, "welcomeLink");
        entityDao.saveOrUpdate(list);
        return redirect("search", "info.save.success");
    }
}
