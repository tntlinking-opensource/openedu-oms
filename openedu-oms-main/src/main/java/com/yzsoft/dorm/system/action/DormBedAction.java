package com.yzsoft.dorm.system.action;

import com.yzsoft.dorm.model.DormBed;
import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.dorm.system.listener.DormBedImportListener;
import com.yzsoft.dorm.utils.DictTypeUtils;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 床位管理
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller("com.yzsoft.dorm.system.action.DormBedAction")
public class DormBedAction extends DormAction {

	@Resource
	private DepartmentService departmentService;
	@Resource
	private DictDataService dictDataService;

	@Override
	protected String getEntityName() {
		return DormBed.class.getName();
	}

	public void putDatas(){
		put("grades", dormService.findGrades());
//		put("educations", dictDataService.findDictData("DORM_BED_EDUCATION"));
		put("educations", dictDataService.findDictData(StudentService.XLCC));
		put("bunkBeds", dictDataService.findDictData(DictTypeUtils.CW_SXP));
		put("bedTypes", dictDataService.findDictData(DictTypeUtils.CW_CPFW));
	}
	
	@Override
	protected void indexSetting() {
		super.indexSetting();
		putDatas();
	}
	
	@Override
	protected OqlBuilder<?> getOqlBuilder() {
		String manager = get("manager");
	    OqlBuilder<?> query = OqlBuilder.from(getEntityName(), getShortName());
	    populateConditions(query);
	    QueryHelper.populateIds(query, getShortName() + ".id");
	    if(StringUtils.isNotEmpty(manager)){
	    	query.join("dormBed.room.building.managers", "manager");
	    	query.where("manager.code like :name or manager.name like :name", "%" + manager+ "%");
	    }else{
	    	String order=getOrderString();
	    	if(!"name".equals(order)){
	    		query.orderBy(order);
	    	}else{
	    		query.orderBy(Order.desc("dormBed.room.building.zone.campus.code"));
	    		query.orderBy(Order.asc("dormBed.room.building.zone.code"));
	    		query.orderBy(Order.asc("dormBed.room.building.code"));
	    		query.orderBy(Order.asc("dormBed.room.code"));
	    		query.orderBy(Order.asc("dormBed.code"));
	    	}
	    }
	    query.limit(getPageLimit());
	    return query;
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		List<String> dormBedNames = new ArrayList<String>();
		DormBed dormBed = (DormBed) entity;
		if (dormBed.getRoom() != null && null!=dormBed.getId()) {
			for (int i = 1; i <= dormBed.getRoom().getBedNum(); i++) {
				dormBedNames.add(i + "");
			}
		}
		put("dormBedNames", dormBedNames);
		putDatas();
	}

	
	@Override
	protected String saveAndForward(Entity<?> entity) {
		DormBed dormBed=(DormBed) entity;
		if(entityDao.duplicate(DormBed.class,dormBed.getId(),"code" ,dormBed.getCode())){
			put("dormBed",dormBed);
			return forward("edit", "床位编号不能重复");
		}
		entityDao.saveOrUpdate(dormBed);
		return redirect("search","info.save.success");
	}
	
	public String historyInfo(){
	    Long entityId = getEntityId(getShortName());
        if (null == entityId) {
            logger.warn("cannot get paremeter {}Id or {}.id", getShortName(), getShortName());
        }
        Entity<?> entity = getModel(getEntityName(), entityId);
        put(getShortName(), entity);
		return forward();
	}
	
	/**
	 * 退宿处理
	 * @return
	 */
	public String checkOutInterim() {
		Long[] ids = getEntityIds();
		List<DormBed> dormBeds = entityDao.get(DormBed.class, ids);
		for (DormBed dormBed : dormBeds) {
			//退宿处理
			Student std = dormBed.getStudent();
			if(null!=std){
				dormService.checkOutInterim(std.getId(), dormBed);
				
				DormPlanBed dormPlanBed = entityDao.getEntity(DormPlanBed.class, "student.id", std.getId());
		        if (dormPlanBed != null) {
		            dormPlanBed.setStudent(null);
		            this.entityDao.saveOrUpdate(new Object[]{dormPlanBed});
		        }
			}
		}
		return redirect("search","退宿成功！");
	}
	
	//导入对应的属性
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new LinkedMap();
		map.put("床位编号", "code");
		map.put("床位号", "name");

		map.put("宿舍楼", "room.building.code");
		map.put("寝室", "room.name");
		map.put("上/下铺", "bunkBed.name");
		map.put("床铺方位", "bedType.name");
		map.put("面向层次", "education.name");
		map.put("学号", "student.code");
		map.put("姓名", "student.name");
		map.put("学院", "student.department.name");
		map.put("班级", "student.adminClass.name");
		map.put("是否可用", "enabled");
		return map;
    }
	
	@Override
	public ItemImporterListener getImporterListener() {
		DormBedImportListener importListener = new DormBedImportListener(entityDao, dormService, dictDataService);
		return importListener;
	}
}
