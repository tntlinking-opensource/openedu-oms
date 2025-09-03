package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.*;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesManagerAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;

	@Override
	protected String getEntityName() {
		return SuppliesStd.class.getName();
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
		query.newFrom("YXW_SUPPLIES_STD_ITEMS ssi");
		query.join("YXW_SUPPLIESES sup", "on ssi.supplies_id = sup.id");
		query.join("YXW_SUPPLIES_STDS ss", "on ssi.supplies_std_id = ss.id");
		query.join("CP_STUDENTS s", "on ss.student_id = s.id");
		query.where("s.grade = :grade", year);
//		query.select("cc.name cname, sup.name sname, sum(ssi.num)");
//		query.groupBy("cc.name, sup.name");
//		query.orderBy("cc.name, sup.name");
//		String title = CountUtil.where(query);
		query.select("s.grade, sup.name sname, sum(ssi.num)");
		query.groupBy("s.grade, sup.name");
		query.orderBy("s.grade, sup.name");
		List<Object[]> datas = entityDao.search(query);
		put("datas", CountUtil.convert(datas, "年份"));
		return forward();
	}

    public String print() {
        report();
        return forward();
    }

    public void exportExcel() {
        report();
        CountUtil.exportExcel("生活用品统计");
    }


}
