package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.model.IconLink;
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
import java.io.File;
import java.util.List;


@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class IconLinkAction extends EntityDrivenAction {

    @Resource
    private UploadFileService uploadFileService;
    @Resource
    private IndexService indexService;

    @Override
    protected String getEntityName() {
        return IconLink.class.getName();
    }

    @Override
    protected String getDefaultOrderString() {
        return "sort";
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        IconLink link = (IconLink) entity;
        if (link.getSort() == null) {
            link.setSort(999);
        }
        File file = get("file", File.class);
        String fileName = get("fileFileName");
        if (file != null) {
//            UploadFile uploadFile = uploadFileService.saveFile(file, fileName, "iconLink");
//            link.setImg(uploadFileService.update(uploadFile.getUuid(), link.getImg()));
            UploadFile uploadFile = WebuploaderUtils.saveFile(file, fileName, "iconLink");
            link.setImg(WebuploaderUtils.updateTempFile(uploadFile.getPath(), link.getImg()));
        }
        return super.saveAndForward(entity);
    }


    public String sort() {
        put("iconLinks", indexService.findIconLink());
        return forward();
    }

    public String sortSave() {
        List<IconLink> list = populateList(IconLink.class, "iconLink");
        entityDao.saveOrUpdate(list);
        return redirect("search", "info.save.success");
    }
}
