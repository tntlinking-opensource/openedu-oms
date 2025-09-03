package com.yzsoft.yxxt.dictdata.action;

import org.apache.commons.collections.map.LinkedMap;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictType;

import java.util.List;
import java.util.Map;

public abstract class DictDataAction extends EntityDrivenAction {

	@Override
	protected String getEntityName() {
		return DictData.class.getName();
	}

	abstract protected String getDictTypeCode();

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder query = (OqlBuilder) super.getQueryBuilder();
		query.where("dictType.code = :code", getDictTypeCode());
		return query;
	}

	protected DictType getDictType() {
		return entityDao.getEntity(DictType.class, "code", getDictTypeCode());
	}

	public String activate() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<DictData> dictDatas = entityDao.get(DictData.class, menuIds);
		for (DictData dictData : dictDatas) {
			dictData.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(dictDatas);
		return redirect("search", "info.save.success");
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		DictData dictData = (DictData) entity;
		if (dictData.getDictType() == null) {
			dictData.setDictType(getDictType());
		}
		dictData.setCode(getDictTypeCode() + "_" + dictData.getGbxb());
		return super.saveAndForward(entity);
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new LinkedMap();
		map.put("代码", "gbxb");
		map.put("名称", "name");
		return map;
	}

	@Override
	public ItemImporterListener getImporterListener() {
		DictDataImporter importListener = new DictDataImporter();
		importListener.setDictType(getDictType());
		return importListener;
	}
}
