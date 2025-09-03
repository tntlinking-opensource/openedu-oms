package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBuilding;
import com.yzsoft.dorm.model.DormFloor;
import com.yzsoft.dorm.system.service.DormService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.ItemImporterException;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.springframework.util.Assert;

import java.util.List;

public class DormFloorImportListener extends ItemImporterListener {

	private DormService dormService;

	public DormFloorImportListener(EntityDao entityDao, DormService dormService) {
		super();
		this.entityDao = entityDao;
		this.dormService = dormService;
	}

	public void onItemFinish(TransferResult tr) {
		DormFloor source = (DormFloor) importer.getCurrent();
		DormFloor dormFloor = getObject(source);
		copyPropertiesIgnoreNull(source, dormFloor);
		dormFloor.setBuilding(get(DormBuilding.class, source.getBuilding(), dormFloor.getBuilding()));
		saveOrUpdate(dormFloor);
	}

	private DormFloor getObject(DormFloor source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "楼层编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "楼层名称不能为空");
		Assert.isTrue(source.getBuilding() != null && StringUtils.isNotBlank(source.getBuilding().getCode()), "宿舍楼编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank((String)importer.getCurData().get("enabled")) , "是否可用不能为空");
		
		DormBuilding dormBuilding = entityDao.getEntity(DormBuilding.class, "code",source.getBuilding().getCode());
		if (dormBuilding == null) {
			throw new ItemImporterException("宿舍楼不存在", source.getBuilding().getCode());
		}
		
		List<DormFloor> dormFloors = entityDao.get(DormFloor.class, new String[]{"name", "building.code"}, source.getName(), source.getBuilding().getCode());
		if (dormFloors == null || dormFloors.isEmpty()) {
			return new DormFloor();
		}
		return dormFloors.get(0);
	}
}
