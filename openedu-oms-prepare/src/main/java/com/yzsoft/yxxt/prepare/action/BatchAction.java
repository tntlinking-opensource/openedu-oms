package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.model.Batch;
import org.beangle.model.Entity;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.service.MajorService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.util.DateUtil;
import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class BatchAction extends EntityDrivenAction {

    @Resource
    private DictDataService dictDataService;
    @Resource
    private MajorService majorService;

    @Override
    protected String getEntityName() {
        return Batch.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("terms", dictDataService.findDictData("TERM"));
        put("educations", dictDataService.findDictData("XLCC"));
    }

    @Override
    protected String getDefaultOrderString() {
        return "batch.startTime desc";
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        indexSetting();
        Batch batch = (Batch) entity;
        List<Major> majors = majorService.findAllMajor();
        majors.removeAll(batch.getMajors());
        put("majors", majors);
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        Batch batch = (Batch) entity;
        if (batch.getTerm().getName() == null) {
            batch.setTerm(entityDao.get(DictData.class, batch.getTerm().getId()));
        }
        batch.setStartTime(DateUtil.getDayStart(batch.getStartTime()));
        batch.setEndTime(DateUtil.getDayEnd(batch.getEndTime()));
        batch.setName(batch.getYear() + "（" + batch.getTerm().getName() + "）");
//        batch.getEducations().clear();
//        batch.getEducations().addAll(entityDao.get(DictData.class, SeqStringUtil.transformToLong(get("educationIds"))));
        batch.getMajors().clear();
        batch.getMajors().addAll(entityDao.get(Major.class, SeqStringUtil.transformToLong(get("majorIds"))));
        return super.saveAndForward(entity);
    }
}
