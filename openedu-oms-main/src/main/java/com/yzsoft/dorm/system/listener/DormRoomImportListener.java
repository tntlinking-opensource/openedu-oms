package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBuilding;
import com.yzsoft.dorm.model.DormFloor;
import com.yzsoft.dorm.model.DormRoom;
import com.yzsoft.dorm.system.service.DormService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.ItemImporterException;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.util.Assert;

import java.util.*;

/**
 * 宿舍导入监听
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年1月22日 下午2:24:24
 */
public class DormRoomImportListener extends ItemImporterListener {

	private DormService dormService;
	private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();
	private Set<DormFloor> dormFloors = new HashSet<DormFloor>();
	private Set<DormBuilding> dormBuildings = new HashSet<DormBuilding>();

	public DormRoomImportListener(EntityDao entityDao, DormService dormService, DictDataService dictDataService) {
		super();
		this.entityDao = entityDao;
		this.dictDataService = dictDataService;
		this.dormService = dormService;
	}

	public void onItemFinish(TransferResult tr) {
		DormRoom source = (DormRoom) importer.getCurrent();
		DormRoom dormRoom = getObject(source);
		if(null==dormRoom.getId()){
			copyPropertiesIgnoreNull(source, dormRoom);
		}else{
			dormRoom.setName(source.getCode());
			dormRoom.setName(source.getName());
			dormRoom.setBedNum(source.getBedNum());
		}
		
		dormRoom.setGender( this.getGenderStr(source));//面向性别
		dormRoom.setFloor(getFloor(source));
		dormRoom.setBuilding(dormRoom.getFloor().getBuilding());
		
		if(null!=dormRoom.getType()&&StringUtils.isNotEmpty(dormRoom.getType().getName())){
			dormRoom.setType(getDictData(source.getType(), "DORM_ROOM_TYPE", dormRoom.getType()));
		}else{
			DictData roomType = new DictData();
			roomType.setName("寝室");
			dormRoom.setType(getDictData(roomType, "DORM_ROOM_TYPE", dormRoom.getType()));
		}
		dormRoom.setDirection(getDictData(source.getDirection(), "DORM_ROOM_DIRECTION", dormRoom.getDirection()));
		saveOrUpdate(dormRoom);
		if (dormRoom.getFloor() != null) {
			dormFloors.add(dormRoom.getFloor());
			dormBuildings.add(dormRoom.getFloor().getBuilding());
		}
	}

	@Override
	public void onFinish(TransferResult tr) {
		super.onFinish(tr);
	}

	private DormFloor getFloor(DormRoom source) {
		Map<String, Object> objectMap = map.get(DormFloor.class.getName());
		if (objectMap == null) {
			objectMap = new HashMap<String, Object>();
			map.put(DormFloor.class.getName(), objectMap);
		}
		String code = source.getFloor().getBuilding().getCode() + "#" + source.getFloor().getName().replaceAll("层", "");

		DormFloor obj = (DormFloor) objectMap.get(code);
		if (obj == null) {
			List<DormFloor> dormFloors = entityDao.get(DormFloor.class, new String[]{"name", "building.code"}, source.getFloor().getName(), source.getFloor().getBuilding().getCode());
			if (dormFloors != null && !dormFloors.isEmpty()) {
				obj = dormFloors.get(0);
			}
			if (obj != null) {
				objectMap.put(code, obj);
			}
		}
		if(obj == null){
			throw new ItemImporterException("楼层不存在", source.getFloor().getName());
		}
		return obj;
	}

	private DormRoom getObject(DormRoom source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "寝室名称不能为空");
		Assert.isTrue(source.getFloor() != null && source.getFloor().getBuilding() != null && StringUtils.isNotBlank(source.getFloor().getBuilding().getCode()), "楼栋编号不能为空");
		Assert.isTrue(StringUtils.isNotBlank(source.getFloor().getName()), "楼层不能为空");
		Assert.isTrue(StringUtils.isNotBlank((String)importer.getCurData().get("enabled")) , "是否可用不能为空");
		
		if(source.getType() != null && StringUtils.isNotBlank(source.getType().getName())){
			DictData type = getDictData("DORM_ROOM_TYPE",source.getType().getName(),false);
			if (type == null) {
				throw new ItemImporterException("房间用途不存在", source.getType().getName());
			}
		}
		
		if(source.getDirection() != null && StringUtils.isNotBlank(source.getDirection().getName())){
			DictData type = getDictData("DORM_ROOM_DIRECTION",source.getDirection().getName(),false);
			if (type == null) {
				throw new ItemImporterException("朝向不存在", source.getDirection().getName());
			}
		}
		
		List<DormRoom> dormRooms = entityDao.get(DormRoom.class, new String[]{"name", "building.code"}, source.getName(), source.getFloor().getBuilding().getCode());
		if (dormRooms == null || dormRooms.isEmpty()) {
			return new DormRoom();
		}
		return dormRooms.get(0);
	}
	
	public String getGenderStr(DormRoom source){
		String gender = source.getGender();
		if(StringUtils.isNotEmpty(gender)){
			if(gender.equals("男")){
				gender = "男";
			}else if(gender.equals("女")){
				gender = "女";
			}
		}else{
			gender = "";
		}
		return gender;
	}
}
