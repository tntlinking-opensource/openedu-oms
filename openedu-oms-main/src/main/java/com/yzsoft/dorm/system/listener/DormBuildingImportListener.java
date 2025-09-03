package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBuilding;
import com.yzsoft.dorm.model.DormManager;
import com.yzsoft.dorm.model.DormZone;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.ItemImporterException;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 宿舍楼导入监听
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年1月20日 下午3:22:51
 */
public class DormBuildingImportListener extends ItemImporterListener {

	public DormBuildingImportListener(EntityDao entityDao) {
		super();
		this.entityDao = entityDao;
	}

	public void onItemFinish(TransferResult tr) {
		DormBuilding source = (DormBuilding) importer.getCurrent();
		DormBuilding dormBuilding = getObject(source);
		copyPropertiesIgnoreNull(source, dormBuilding);
		//判断性别是否填写正确并保存
		String gender = source.getGender();
		if(StringUtils.isNotEmpty(gender)){
			if(gender.equals("男")){
				gender = "男";
			}else if(gender.equals("女")){
				gender = "女";
			}else if(gender.equals("混合")){
				gender = "混合";
			}else{
				gender = "";
			}
		}else{
			gender = "";
		}
		dormBuilding.setGender(gender);
		//保存是否可用
		String enabled=source.getEnabledStr();
		Boolean en=Boolean.TRUE;
		if(StringUtils.isNotEmpty(enabled)){
			if(enabled.equals("否")){
				en = Boolean.FALSE;
			}
		}
		dormBuilding.setEnabled(en);
		dormBuilding.setZone(get(DormZone.class, source.getZone(), dormBuilding.getZone()));
		
		//宿舍管理员
		if(null!=source.getManager()&&StringUtils.isNotEmpty(source.getManager().getCode())){
			dormBuilding.getManagers().clear();
			DormManager dormManager = get(DormManager.class, source.getManager(), dormBuilding.getManager());
			if(null!=dormManager){
				dormBuilding.getManagers().add(dormManager);
			}
		}
		saveOrUpdate(dormBuilding);
	}

	private DormBuilding getObject(DormBuilding source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getCode()), "宿舍楼编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "宿舍楼名称不能为空");
		Assert.isTrue(source.getZone() != null && StringUtils.isNotBlank(source.getZone().getCode()), "宿舍区编号不能为空");
		Assert.isTrue(source.getFloorNum()>0, "宿舍楼楼层数不能为空或不能小于1");
		//Assert.isTrue(StringUtils.isNotBlank((String)importer.getCurData().get("enabled")) , "是否可用不能为空");
		
		DormZone dormZone = entityDao.getEntity(DormZone.class, "code", source.getZone().getCode());
		if (dormZone == null) {
			throw new ItemImporterException("宿舍区不存在", source.getZone().getCode());
		}
		
		if(source.getManager() != null && StringUtils.isNotBlank(source.getZone().getCode())){
			DormManager dormManager = entityDao.getEntity(DormManager.class, "code",source.getManager().getCode());
			if (dormManager == null) {
				throw new ItemImporterException("楼栋管理员不存在", source.getManager().getCode());
			}
		}
		
		List<DormBuilding> dormBuildings = entityDao.get(DormBuilding.class, new String[]{"code", "zone.code"}, source.getCode(), source.getZone().getCode());
		if (dormBuildings == null || dormBuildings.isEmpty()) {
			return new DormBuilding();
		}
		return dormBuildings.get(0);
	}
}
