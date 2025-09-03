package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.web.collect.model.Supplies;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesSettingAction extends EntityDrivenAction {

	@Resource
	private DictDataService dictDataService;

	@Override
	protected String getEntityName() {
		return Supplies.class.getName();
	}

	@Override
	protected String getDefaultOrderString() {
		return "code";
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		List<DictData> types = dictDataService.findDictData("SUPPLIES_TYPE");
		if (types.isEmpty()) {
			types = init();
		}
		Supplies supplies = (Supplies) entity;
		if (supplies.getType() == null) {
			supplies.setType(types.get(0));
		}
		put("types", types);
	}

	private List<DictData> init() {
		String code = "SUPPLIES_TYPE";
		String name = "日常用品类型";
		String[] names = new String[]{"订购", "信息收集"};
		List<DictData> types = dictDataService.init(code, name, names);
		return types;
	}
}
