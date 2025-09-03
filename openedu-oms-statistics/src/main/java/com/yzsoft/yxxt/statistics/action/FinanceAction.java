package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.core.service.YxxtService;
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
 * 财务缴费查询
 */
public class FinanceAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    protected FinanceService financeService;

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("year", yxxtService.getYear());
    }

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        Integer status = getInteger("status");
        if(status != null){
            switch (status){
                case 1 :
                    query.where("feePaid = feeTotal");
                    break;
                case 2 :
                    query.where("feePaid > 0 and feePaid < feeTotal");
                    break;
                case 3 :
                    query.where("feePaid = 0");
                    break;
            }
        }
    }
}
