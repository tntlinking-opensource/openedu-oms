package com.yzsoft.yxxt.prepare.action;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.print.action.PrintDesignerBaseAction;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Arrays;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class PrintConfigAction extends PrintDesignerBaseAction {

	@Override
	protected List<DictData> findDictData() {
		OqlBuilder<DictData> query = OqlBuilder.from(DictData.class, "dd");
		query.where("dd.enabled = true");
		query.where("dd.code in (:code)", Arrays.asList(new String[]{"DYMBLX_YBD_01_TZS", "DYMBLX_YBD_02_FM"}));
		query.orderBy("dd.code");
		query.cacheable();
		return entityDao.search(query);
	}
}
