package com.yzsoft.yxxt.web.collect.clothes.action;

import com.yzsoft.yxxt.web.collect.clothes.model.ClothesSize;
import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;
import com.yzsoft.yxxt.web.collect.clothes.service.ClothesService;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.struts2.convention.route.Action;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesSizeAction extends EntityDrivenAction {

	@Resource
	private ClothesService clothesService;

	@Override
	protected Class<?> getEntityClass() {
		return ClothesSize.class;
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("types", clothesService.findType());
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		put("types", clothesService.findType());
		ClothesSize size = (ClothesSize) entity;
		if (size.getType() != null && size.getType().getId() != null && size.getType().getName() == null) {
			size.setType(entityDao.get(ClothesType.class, size.getType().getId()));
		}
	}

	@Override
	protected <T> List<T> search(QueryBuilder<T> query) {
		put("typeId", get("clothesSize.type.id"));
		return super.search(query);
	}

	public String multiEdit() {
		put("typeId", get("clothesSize.type.id"));
		return forward();
	}

	public String multiSave() {
		String code = get("code");
		String value = get("value");
		String[] values = get("values").replace("\t", " ").split(" ");
		String[] names = get("names").replace("\t", " ").split(" ");
		Long typeId = getLong("typeId");
		if (code == null) {
			code = "";
		}
		if (value == null) {
			value = "";
		}
		ClothesType type = new ClothesType();
		type.setId(typeId);
		List<ClothesSize> list = new ArrayList<ClothesSize>();
		for (int i = 0; i < names.length; i++) {
			ClothesSize size = new ClothesSize();
			size.setCode(code + String.format("%02d", i + 1));
			size.setName(names[i]);
			size.setType(type);
			size.setValue(value + " " + values[i]);
			list.add(size);
		}
		entityDao.saveOrUpdate(list);
		return redirect("search");
	}

	public String back() {
		return forward();
	}
}
