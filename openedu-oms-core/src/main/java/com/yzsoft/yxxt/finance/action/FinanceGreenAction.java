package com.yzsoft.yxxt.finance.action;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceGreenItem;
import com.yzsoft.yxxt.finance.service.FinanceService;
import org.beangle.model.Entity;
import org.beangle.process.utils.DictTypeUtils;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceGreenAction extends EntityDrivenAction {

    @Autowired
    private FinanceService financeService;
    @Autowired
    private DictDataService dictDataService;

    @Override
    protected String getEntityName() {
        return FinanceGreen.class.getName();
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        super.editSetting(entity);
        put("items", financeService.findItem());
        put("educations", dictDataService.findDictData(DictTypeUtils.EDUCATION));
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        FinanceGreen green = (FinanceGreen) entity;

        Long[] educationIds = SeqStringUtil.transformToLong(get("educationIds"));
        green.getEducations().clear();
        if (educationIds != null && educationIds.length > 0) {
            List<DictData> instructors = entityDao.get(DictData.class, educationIds);
            green.getEducations().addAll(instructors);
        }

        green.getItems().clear();
        double money = 0;
//		if (!green.isManual()) {
        green.getItems().addAll(getAllEntity(FinanceGreenItem.class, "item"));
        for (Iterator<FinanceGreenItem> itor = green.getItems().iterator(); itor.hasNext(); ) {
            FinanceGreenItem item = itor.next();
            if (item.getMoney() == 0) {
                itor.remove();
            }
        }
        for (FinanceGreenItem item : green.getItems()) {
            money += item.getMoney();
            item.setGreen(green);
        }
//		}
        green.setMoney(money);
        return super.saveAndForward(entity);
    }
}
