package com.yzsoft.yxxt.dictdata.action;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.model.DictType;
import org.springframework.util.Assert;

import java.util.List;

public class DictDataImporter extends ItemImporterListener {

	private DictType dictType;

	public void setDictType(DictType dictType) {
		this.dictType = dictType;
	}

	public DictDataImporter() {
	}

	public void onItemFinish(TransferResult tr) {
		DictData source = (DictData) importer.getCurrent();
		DictData dictData = getObject(source);
		if (dictData == null) {
			dictData = new DictData();
		}
		copyPropertiesIgnoreNull(source, dictData);
		if(dictData.getDictType() == null){
			dictData.setDictType(dictType);
		}
		dictData.setCode(dictType.getCode() + "_" + dictData.getGbxb());
		saveOrUpdate(dictData);
	}


	private DictData getObject(DictData source) {
		Assert.isTrue(source.getGbxb() != null, "代码不能为空");
		OqlBuilder query = OqlBuilder.from(DictData.class);
		query.where("dictType = :dictType", dictType);
		query.where("gbxb = :gbxb", source.getGbxb());
		List<DictData> list = entityDao.search(query);
		return list.isEmpty() ? null : list.get(0);
	}
}
