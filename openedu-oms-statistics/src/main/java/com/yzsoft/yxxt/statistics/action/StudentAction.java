package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.product.core.model.Student;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Calendar;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;

    @Override
    protected String getEntityName() {
        return Student.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("year", yxxtService.getYear());
    }
}
