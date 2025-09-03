package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.ImgLink;
import com.yzsoft.yxxt.web.service.IndexService;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.model.UploadFile;
import org.beangle.website.common.service.UploadFileService;
import org.beangle.website.common.util.WebuploaderUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ImgLinkAction extends EntityDrivenAction {

    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private IndexService indexService;

    @Override
    protected String getEntityName() {
        return ImgLink.class.getName();
    }

    @Override
    protected String getDefaultOrderString() {
        return "sort";
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        ImgLink link = (ImgLink) entity;
        if (link.getSort() == null) {
            link.setSort(999);
        }
//        link.setImg(uploadFileService.update(get("newimg"), get("oldimg")));
        link.setImg(WebuploaderUtils.updateTempFile(get("newimg"), link.getImg()));
        return super.saveAndForward(entity);
    }


    public String sort() {
        put("imgLinks", indexService.findImgLink());
        return forward();
    }

    public String sortSave() {
        List<ImgLink> list = populateList(ImgLink.class, "imgLink");
        entityDao.saveOrUpdate(list);
        return redirect("search", "info.save.success");
    }
}
