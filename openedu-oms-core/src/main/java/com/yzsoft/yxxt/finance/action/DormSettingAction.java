package com.yzsoft.yxxt.finance.action;

import com.yzsoft.yxxt.finance.model.DormSetting;
import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.service.DormService;
import com.yzsoft.yxxt.finance.service.FinanceService;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.util.SeqStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormSettingAction extends EntityDrivenAction {

    @Autowired
    protected FinanceService financeService;
    @Autowired
    protected DormService dormService;
    
    public String getEntityName(){
    	return DormSetting.class.getName();
    }

    @Override
    public String index() {
        List<DormSetting> dormSettings = dormService.findSetting();
        if (dormSettings.isEmpty()) {
            return redirect("edit");
        }
        put("dormSettings", dormSettings);
        return forward();
    }

    @Override
    public String edit() {
        put("financeItem", entityDao.getEntity(FinanceItem.class, "code", "003"));
        put("dormSettings", dormService.findSetting());
        return forward();
    }

    @Override
    public String save() throws Exception {
        List<DormSetting> dormSettings = entityDao.get(DormSetting.class, SeqStringUtil.transformToLong(get("removeIds")));
        entityDao.remove(dormSettings);
        
        List<DormSetting> dorms = populateList(DormSetting.class,"dormSetting");
        entityDao.saveOrUpdate(dorms);
        return redirect("index");
    }
}
