package com.yzsoft.dorm.system.listener;

import com.yzsoft.dorm.model.DormBed;
import com.yzsoft.dorm.model.DormRoom;
import com.yzsoft.dorm.system.service.DormService;
import com.yzsoft.dorm.utils.DictTypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.ItemImporterException;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.service.DictDataService;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 床位导入接口
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年1月22日 下午2:13:51
 */
public class DormBedImportListener extends ItemImporterListener {

	private DormService dormService;
	private Map<String, Map<String, Object>> map = new HashMap<String, Map<String, Object>>();

	public DormBedImportListener(EntityDao entityDao, DormService dormService, DictDataService dictDataService) {
		super();
		this.entityDao = entityDao;
		this.dictDataService = dictDataService;
		this.dormService = dormService;
	}

	/**
	 * 导入模版字段：床位编号、床位号、楼栋、寝室、上下铺、床铺方位、面向层次、学号、姓名、学院、班级、是否可用
	 *  
	 * @param tr
	 */
	public void onItemFinish(TransferResult tr) {
		DormBed source = (DormBed) importer.getCurrent();
//		DormBed dormBed = getObject(source);
		if(StringUtils.isEmpty(source.getCode())){
			tr.addFailure("床位编号不能为空！", source.getCode());
		}
		DormBed dormBed = this.getDormBedByBedCode(source.getCode());
		if(null==dormBed){
			dormBed = new DormBed();
		}
		copyPropertiesIgnoreNull(source, dormBed);
		if (dormBed.getId() == null) {
			dormBed.setRoom(getRoom(source, dormBed.getRoom()));
		}
		Student student = getStudent(source, dormBed.getStudent());
		dormBed.setBunkBed(getDictData(source.getBunkBed(), DictTypeUtils.CW_SXP, dormBed.getBunkBed()));
		dormBed.setBedType(getDictData(source.getBedType(), DictTypeUtils.CW_CPFW, dormBed.getBedType()));
		dormBed.setEducation(getDictData(source.getEducation(), StudentService.XLCC, dormBed.getEducation()));
		saveOrUpdate(dormBed);
		if(null != student && null != student.getId()){
			dormService.change(student.getId(), dormBed.getId(), true);
		}
	}

	private Student getStudent(DormBed source, Student defaultStudent) {
		if (source.getStudent() == null || StringUtils.isBlank(source.getStudent().getCode())) {
			return defaultStudent;
		}
		Student student = entityDao.getEntity(Student.class, "code", source.getStudent().getCode());
		if (student == null) {
			throw new ItemImporterException("该学号学生不存在", source.getStudent().getCode());
		}
		return student;
	}

	private DormRoom getRoom(DormBed source, DormRoom room) {
		List<DormRoom> dormRooms = entityDao.get(DormRoom.class, new String[]{"name", "building.code"}, source.getRoom().getName(), source.getRoom().getBuilding().getCode());
		if (dormRooms == null || dormRooms.isEmpty()) {
			throw new ItemImporterException("寝室或楼栋有误", source.getRoom().getName() + "," + source.getRoom().getBuilding().getCode());
		}
		return dormRooms.get(0);
	}

	private DormBed getObject(DormBed source) {
		Assert.isTrue(StringUtils.isNotBlank(source.getName()), "床位号不能为空");
		Assert.isTrue(source.getRoom() != null && StringUtils.isNotBlank(source.getRoom().getName()), "寝室不能为空");
		Assert.isTrue(source.getRoom().getFloor() != null && source.getRoom().getBuilding() != null && StringUtils.isNotBlank(source.getRoom().getBuilding().getName()), "楼栋不能为空");
		List<DormBed> dormBeds = entityDao.get(DormBed.class, new String[]{"code", "room.code", "room.building.name"}, source.getName(), source.getRoom().getName(), source.getRoom().getFloor().getBuilding().getCode());
		if (dormBeds == null || dormBeds.isEmpty()) {
			return new DormBed();
		}
		return dormBeds.get(0);
	}
	
	private DormBed getDormBedByBedCode(String bedCode){
		return  entityDao.getEntity(DormBed.class, "code", bedCode);
	}
}
