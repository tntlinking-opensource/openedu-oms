package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.FootLink;
import com.yzsoft.yxxt.web.service.IndexService;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FootLinkAction extends EntityDrivenAction {

    @Resource
    private IndexService indexService;

    @Override
    protected String getEntityName() {
        return FootLink.class.getName();
    }

    @Override
    protected String getDefaultOrderString() {
        return "sort";
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        List<DictData> groups = indexService.findFootLinkGroup();
        put("groups", groups);
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        FootLink link = (FootLink) entity;
        if (link.getSort() == null) {
            link.setSort(999);
        }
        return super.saveAndForward(entity);
    }


    public String sort() {
        Long groupId = getLong("footLink.group.id");
        put("footLinks", indexService.findFootLink(groupId));
        return forward();
    }

    public String sortSave() {
        List<FootLink> list = populateList(FootLink.class, "footLink");
        entityDao.saveOrUpdate(list);
        return redirect("search", "info.save.success");
    }
}
