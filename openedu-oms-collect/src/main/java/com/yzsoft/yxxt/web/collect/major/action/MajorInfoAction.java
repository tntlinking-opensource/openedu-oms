package com.yzsoft.yxxt.web.collect.major.action;

import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import org.beangle.model.Entity;
import org.beangle.product.core.action.MajorAction;
import org.beangle.product.core.model.Major;
import org.beangle.website.common.service.UploadFileService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MajorInfoAction extends MajorAction {

    @Resource
    private UploadFileService uploadFileService;

    @Override
    protected String getDefaultOrderString() {
        return "major.code";
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        Major major = (Major) entity;
        put("majorInfo", getMajorInfo(major));
    }

    private MajorInfo getMajorInfo(Major major) {
        MajorInfo majorInfo = entityDao.getEntity(MajorInfo.class, "major", major);
        if (majorInfo == null) {
            majorInfo = new MajorInfo();
            majorInfo.setMajor(major);
        }
        return majorInfo;
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        MajorInfo majorInfo = populateEntity(MajorInfo.class, "majorInfo");
        majorInfo.setLogo(uploadFileService.update(get("newimg"), majorInfo.getLogo()));
//        entityDao.saveOrUpdate(majorInfo);
        return super.saveAndForward(majorInfo);
    }

    @Override
    public String info() {
        Long entityId = getEntityId(getShortName());
        if (null == entityId) {
            logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
        }
        Major major = (Major) getModel(getEntityName(), entityId);
        put(getShortName(), major);
        put("majorInfo", getMajorInfo(major));
        return forward();
    }
}
