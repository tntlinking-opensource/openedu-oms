package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBuilding;
import com.yzsoft.dorm.model.DormMachine;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.springframework.util.Assert;

/**
 * 门禁机器导入监听
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年5月19日 下午4:57:29
 */
public class DormMachineImportListener extends ItemImporterListener {

	public DormMachineImportListener(EntityDao entityDao) {
		super();
		this.entityDao = entityDao;
	}
	
	/**
	 * 导入模版字段：机器编号、机器名称、安放地址、楼栋编号、备注、是否可用
	 * 导入规则：机器编号号唯一约束
	 * @param tr
	 */
	public void onItemFinish(TransferResult tr) {
		DormMachine source = (DormMachine) importer.getCurrent();
		DormMachine dormMachine = getObject(source);
		copyPropertiesIgnoreNull(source, dormMachine);
		if(null!=source.getBuilding()){
			DormBuilding building = entityDao.getEntity(DormBuilding.class, "code", source.getBuilding().getCode());
			dormMachine.setBuilding(building);
		}
		saveOrUpdate(dormMachine);
	}
	
	private DormMachine getObject(DormMachine source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "机器编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "机器名称不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getAddress()), "安放地址不能为空");
		DormMachine dormMachine = entityDao.getEntity(DormMachine.class, "code", source.getCode());
		if (dormMachine == null) {
			dormMachine = new DormMachine();
			return dormMachine;
		}
		return dormMachine;
	}
}
