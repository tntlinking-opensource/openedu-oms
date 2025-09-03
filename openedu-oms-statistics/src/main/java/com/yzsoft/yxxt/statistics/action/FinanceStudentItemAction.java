package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.finance.model.FinanceStudentItem;
import com.yzsoft.yxxt.finance.service.FinanceService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 财务缴费明细查询
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceStudentItemAction extends FinanceAction {

    @Resource
    private FinanceService financeService;

    @Override
    protected String getEntityName() {
        return FinanceStudentItem.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("items", financeService.findItem());
    }
}
