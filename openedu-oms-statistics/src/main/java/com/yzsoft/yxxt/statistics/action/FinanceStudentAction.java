package com.yzsoft.yxxt.statistics.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.statistics.model.FinanceStudentView;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 财务缴费查询
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceStudentAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;

	@Override
	protected String getEntityName() {
		return FinanceStudentView.class.getName();
	}


	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("year", yxxtService.getYear());
	}

	@Override
	protected void querySetting(OqlBuilder query) {
		super.querySetting(query);
		Integer status = getInteger("status");
		if (status != null) {
			switch (status) {
				case 1:
//					query.where("financeStudent.feePaid = financeStudent.feeTotal");
					query.where("financeStudent.feeTotal > 0 and financeStudent.feeOdd = 0");
					break;
				case 3:
					query.select("financeStudentView");
					query.join("left", "financeStudentView.financeStudent", "fs");
					query.where("fs.id is null or fs.feePaid = 0");
					break;
			}
		}
	}

}
