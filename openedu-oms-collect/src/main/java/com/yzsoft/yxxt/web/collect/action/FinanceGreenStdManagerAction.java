package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceGreenStdManagerAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;

    @Override
    protected String getEntityName() {
        return FinanceGreenStd.class.getName();
    }

    @Override
    public String index() {
        put("year", yxxtService.getYear());
        return super.index();
    }

    public String count() {
        put("year", yxxtService.getYear());
        return forward();
    }

    public String report() {
        String year = get("year");
        if (StringUtils.isBlank(year)) {
            year = yxxtService.getYear().toString();
        }
        SqlBuilder query = new SqlBuilder();
        query.newFrom("YXW_FINANCE_GREEN_STD_ITEMS fgsi");
        query.join("YXW_FINANCE_GREEN_STDS fgs", "on fgsi.finance_green_std_id = fgs.id");
        query.join("YXF_FINANCE_GREENS fg", "on fgsi.finance_green_id = fg.id");
        query.join("CP_STUDENTS s", "on fgs.student_id = s.id");
        query.where("s.grade = :grade", year);
        query.where("fgs.handle = 1");
        query.select("cc.name cname, fg.name fname, count(*)");
        query.groupBy("cc.name, fg.name");
        query.orderBy("cc.name, fg.name");
        String title = CountUtil.where(query);
        List<Object[]> datas = entityDao.search(query);
        put("datas", CountUtil.convert(datas, title));
        return forward();
    }

    public String print() {
        report();
        return forward();
    }

    public void exportExcel() {
        report();
        CountUtil.exportExcel("绿色通道统计");
    }

}
