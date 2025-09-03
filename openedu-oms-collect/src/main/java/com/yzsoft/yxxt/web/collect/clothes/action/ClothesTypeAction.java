package com.yzsoft.yxxt.web.collect.clothes.action;

import com.yzsoft.yxxt.web.collect.clothes.model.ClothesType;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.util.WebuploaderUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.Collection;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesTypeAction extends SecurityActionSupport {

	@Override
	protected Class<?> getEntityClass() {
		return ClothesType.class;
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("superadmin", getUserId() == 1);
	}

	@Override
	protected <T> List<T> search(QueryBuilder<T> query) {
		indexSetting();
		return super.search(query);
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		indexSetting();
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		ClothesType type = (ClothesType) entity;
		type.setImg(WebuploaderUtils.updateTempFile(get("newimg"), type.getImg()));
		return super.saveAndForward(entity);
	}

	@Override
	protected void afterRemove(Collection<?> entities) {
		super.afterRemove(entities);
		for (Object obj : entities) {
			ClothesType type = (ClothesType) obj;
			WebuploaderUtils.removeFile(type.getImg());
		}
	}

	public String size() {
		put("clothesTypeId", getLong("clothesType.id"));
		return forward();
	}
}
