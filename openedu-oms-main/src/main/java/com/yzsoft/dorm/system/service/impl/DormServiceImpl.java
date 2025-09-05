package com.yzsoft.dorm.system.service.impl;

import com.yzsoft.dorm.model.*;
import com.yzsoft.dorm.model.change.DormChangeApply;
import com.yzsoft.dorm.model.change.DormChangeLog;
import com.yzsoft.dorm.model.repair.ApartmentRepair;
import com.yzsoft.dorm.model.rule.DormRule;
import com.yzsoft.dorm.repair.service.ApartmentRepairService;
import com.yzsoft.dorm.std.bean.DormTimeLineBean;
import com.yzsoft.dorm.std.bean.DormTimeLineYearBean;
import com.yzsoft.dorm.system.service.DormService;
import com.yzsoft.dorm.utils.DictDataUtils;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.GroupService;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Campus;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.service.CampusService;
import org.beangle.product.core.service.TeachCalendarService;
import org.beangle.website.system.service.DictDataService;
import org.beangle.workflow3.model.YewuBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.*;

/**
 * 宿舍接口实现
 * 
 * @作者：周建明
 * @创建日期：2017年2月14日 上午11:37:36
 */
@Service
public class DormServiceImpl implements DormService {

	private static String DORM_ADMIN ="宿舍管理员";
	private static String INSTRUCTOR_GROUP = "辅导员";
	
	@Resource
	private EntityDao entityDao;
	@Resource
	private DictDataService dictDataService;
	@Resource
	private CampusService campusService;
	@Resource
	private TeachCalendarService teachCalendarService;
	@Resource
	private GroupService groupService;
	@Resource
	private UserService userService;
	@Resource
	private ApartmentRepairService apartmentRepairService;
	
	public boolean isDormAdmin(User user){
		if(userService.isAdmin(user)){
			return true;
		}else{
			Group group = groupService.getGroup(DORM_ADMIN);
			return userService.isMember(user, group);
		}
	}
	
	public boolean isInstructor(User user){
		Group group = groupService.getGroup(INSTRUCTOR_GROUP);
		return userService.isMember(user, group);
	}

	@Transactional
	public void check(Long studentId, Long bedId) {
		change(studentId, bedId, false);
	}

	@Transactional
	public void change(Long studentId, Long newBedId, boolean admin) {
		Student student = entityDao.get(Student.class, studentId);
		DormBed bed = getBed(studentId);
		change(student, bed, newBedId, admin);
//		床位信息放入计划中
		DormPlanBed dormPlanBed = entityDao.getEntity(DormPlanBed.class, "bed.id", newBedId);
		if(dormPlanBed!=null) {
			dormPlanBed.setStudent(student);
		}
	}

	/**
	 * @param student
	 * @param oldBed
	 * @param newBedId
	 * @param admin
	 * @author 费文俊
	 * @createDate 2016年9月3日 上午10:54:02
	 */
	private void change(Student student, DormBed oldBed, Long newBedId, boolean admin) {
		DormBed newBed = entityDao.get(DormBed.class, newBedId);
		Assert.isNull(newBed.getStudent(), "该床位已被占用，床位："+newBed.getBedDetails());
		if (oldBed != null) {
			oldBed.setStudent(null);
			DormBedLog log = getLog(student, oldBed);
			oldBed.setState(DormBed.STATE_FREE);//空闲
			oldBed.setInterim(false);
			entityDao.saveOrUpdate(oldBed);
			if(null!=log){
				log.setEndDate(new Date());
				entityDao.saveOrUpdate(log);
			}
		}
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		//床位日志
		DormBedLog log = new DormBedLog();
		log.setTeachCalendar(teachCalendar);
		log.setBed(newBed);
		log.setStudent(student);
		log.setRent(newBed.getRoom().getRent());
		log.setStartDate(new Date());

		newBed.setStudent(student);
		newBed.setState(DormBed.STATE_USE);//入住
		//宿舍变动日志
		DormChangeLog changeLog = new DormChangeLog();
		changeLog.setTeachCalendar(teachCalendar);
		changeLog.setStudent(student);
		changeLog.setCreateDate(new Date());
		if (oldBed == null) {
			changeLog.setType(dictDataService.getDictData(DictDataUtils.DORM_CHANGE_TYPE_XSFP));
		} else {
			if (admin) {
				changeLog.setType(dictDataService.getDictData(DictDataUtils.DORM_CHANGE_TYPE_SGTZ));
			} else {
				changeLog.setType(dictDataService.getDictData(DictDataUtils.DORM_CHANGE_TYPE_BGSQ));
			}
		}
		changeLog.setBedBefore(oldBed);
		changeLog.setBedAfter(newBed);
		entityDao.saveOrUpdate(newBed);
		entityDao.saveOrUpdate(log);
		entityDao.saveOrUpdate(changeLog);
	}

	private DormBedLog getLog(Student student, DormBed bed) {
		OqlBuilder<DormBedLog> query = OqlBuilder.from(DormBedLog.class);
		query.where("student = :student", student);
		query.where("bed = :bed", bed);
		List<DormBedLog> ls = entityDao.search(query);
		return ls.isEmpty()?null:ls.get(0);
	}

	
	public DormBed getBed(Long studentId) {
		return entityDao.getEntity(DormBed.class, "student.id", studentId);
	}
	
	public List<Campus> findCampus() {
		return campusService.findAllCampus();
	}
	
	public List<DormZone> findZone(Long campusId) {
		OqlBuilder<DormZone> query = OqlBuilder.from(DormZone.class);
		query.where("enabled = true");
		query.where("campus.id = :campusId", campusId);
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}
	
	public List<DormBuilding> findBuilding(Long zoneId) {
		OqlBuilder<DormBuilding> query = OqlBuilder.from(DormBuilding.class);
		query.where("enabled = true");
		query.where("zone.id = :zoneId", zoneId);
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}

	
	public List<DormFloor> findFloor(Long buildingId) {
		OqlBuilder<DormFloor> query = OqlBuilder.from(DormFloor.class);
		query.where("enabled = true");
		query.where("building.id = :buildingId", buildingId);
		query.cacheable();
		return entityDao.search(query);
	}
	
	public List<String> findGrades() {
		OqlBuilder query = OqlBuilder.from(DormBed.class);
		query.select("student.grade");
		query.groupBy("student.grade");
		query.orderBy("student.grade desc");
		return entityDao.search(query);
	}
	
	public List<DormRoom> findRoom(Long floorId) {
		OqlBuilder<DormRoom> query = OqlBuilder.from(DormRoom.class);
		query.where("enabled = true");
		query.where("floor.id = :floorId", floorId);
		query.cacheable();
		return entityDao.search(query);
	}

	
	public List<DormRule> findRule() {
		OqlBuilder<DormRule> query = OqlBuilder.from(DormRule.class);
		query.where("enabled = true");
		query.cacheable();
		return entityDao.search(query);
	}

	@Transactional
	
	public void save(DormZone dormZone) {
		entityDao.saveOrUpdate(dormZone);
	}

	
	public void add(Long studentId, Long bedId) {
		addOrInterim(studentId, bedId, null);
	}

	
	public void checkOut(Long studentId) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		Student student = entityDao.get(Student.class, studentId);
		DormBed oldBed = getBed(studentId);
		if (oldBed != null) {
			oldBed.setStudent(null);
			oldBed.setState("空闲");
			oldBed.setEnabled(true);
			oldBed.setInterim(false);
			DormBedLog log = getLog(student, oldBed);
			entityDao.saveOrUpdate(oldBed);
			if(null!=log){
				log.setEndDate(new Date());
				entityDao.saveOrUpdate(log);
			}
		}
		//宿舍变动日志
		DormChangeLog changeLog = new DormChangeLog();
		changeLog.setTeachCalendar(teachCalendar);
		changeLog.setStudent(student);
		changeLog.setCreateDate(new Date());
		changeLog.setType(dictDataService.getDictData("DORM_CHANGE_TYPE_04"));
		changeLog.setBedAfter(oldBed);
		entityDao.saveOrUpdate(changeLog);
	}

	
	public void Logging(Long studentId) {
		Student student = entityDao.get(Student.class, studentId);
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		//宿舍变动日志
		DormChangeLog changeLog = new DormChangeLog();
		changeLog.setTeachCalendar(teachCalendar);
		changeLog.setStudent(student);
		changeLog.setCreateDate(new Date());
		changeLog.setType(dictDataService.getDictData("DORM_CHANGE_TYPE_05"));
		entityDao.saveOrUpdate(changeLog);
	}

	
	public void interim(Long studentId, Long bedId, String reason) {
		addOrInterim(studentId, bedId, reason);
	}
	
	private void addOrInterim(Long studentId, Long bedId, String reason){
		Student student = entityDao.get(Student.class, studentId);
		DormBed newBed = entityDao.get(DormBed.class, bedId);
		Assert.isNull(newBed.getStudent(), "该床位已被占用");
		
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		//床位日志
		DormBedLog log = new DormBedLog();
		log.setTeachCalendar(teachCalendar);
		log.setBed(newBed);
		log.setStudent(student);
		log.setRent(newBed.getRoom().getRent());
		log.setStartDate(new Date());

		newBed.setStudent(student);
		newBed.setState("入住");
		//宿舍变动日志
		DormChangeLog changeLog = new DormChangeLog();
		changeLog.setTeachCalendar(teachCalendar);
		changeLog.setStudent(student);
		changeLog.setCreateDate(new Date());
		changeLog.setType(dictDataService.getDictData("DORM_CHANGE_TYPE_01"));
		changeLog.setBedAfter(newBed);
		if(StringUtils.isNotEmpty(reason)){
			changeLog.setReason(reason);
			newBed.setInterim(true);
		}
		entityDao.saveOrUpdate(newBed);
		entityDao.saveOrUpdate(log);
		entityDao.saveOrUpdate(changeLog);
	}

	
	public void changeBed(Long studentId, Long newBedId, DormBed oldBed) {
		Student student = entityDao.get(Student.class, studentId);
		change(student, oldBed, newBedId, false);
	}

	
	public void checkOutInterim(Long studentId,DormBed dormBed) {
		TeachCalendar teachCalendar = teachCalendarService.getCurrentTeachCalendar();
		Student student = entityDao.get(Student.class, studentId);
		if (dormBed != null) {
			dormBed.setStudent(null);
			dormBed.setState("空闲");
			dormBed.setEnabled(true);
			dormBed.setInterim(false);
			DormBedLog log = getLog(student, dormBed);
			entityDao.saveOrUpdate(dormBed);
			if(null!=log){
				log.setEndDate(new Date());
				entityDao.saveOrUpdate(log);
			}
		}
		//宿舍变动日志
		DormChangeLog changeLog = new DormChangeLog();
		changeLog.setTeachCalendar(teachCalendar);
		changeLog.setStudent(student);
		changeLog.setCreateDate(new Date());
		changeLog.setType(dictDataService.getDictData(DictDataUtils.DORM_CHANGE_TYPE_TS));
		changeLog.setBedBefore(dormBed);
		entityDao.saveOrUpdate(changeLog);
	}
	
	public void updateDormNum(DormBuilding dormBuilding){
		//更新楼栋楼栋数
		String hqlBf = "update "+DormBuilding.class.getName()+" dormBuilding "
				+ "set dormBuilding.floorNum= (select count(*) from "+DormFloor.class.getName()+" dormFloor where dormFloor.building.id = ?)"
						+ " where dormBuilding.id=?";
		entityDao.executeUpdateHql(hqlBf, dormBuilding.getId(),dormBuilding.getId());
	}
	
	@SuppressWarnings("rawtypes")
	public List findDormManagerUsers(){
		OqlBuilder<DormManager> query = OqlBuilder.from(DormManager.class,"dormManager");
		query.where("dormManager.enabled is true");
		query.select("distinct dormManager.user");
		return entityDao.search(query);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<DormTimeLineYearBean> getDormTimeLineYearBeans(String stdNo){
		//获取所有的宿舍变更记录
		List<DormTimeLineBean> dtlbs = this.getDormTimeLineByDormChangeApply(getDormChangeApplys(stdNo));
		dtlbs.addAll(this.getDormTimeLineByApartmentRepair(apartmentRepairService.findApartmentRepairsByStdCode(stdNo)));
		Collections.sort(dtlbs, new PropertyComparator<DormTimeLineBean>("date desc")); 
		
		//根据年份转换住宿历程
		Map<String,DormTimeLineYearBean> dtlyMap = new HashMap<String,DormTimeLineYearBean>();
		
		for (DormTimeLineBean dtlb : dtlbs) {
			GregorianCalendar calendar=new GregorianCalendar();
			calendar.setTime(dtlb.getDate());
			String year = String.valueOf(calendar.get(Calendar.YEAR));
			DormTimeLineYearBean dtly = dtlyMap.get(year);
			if(null==dtly){
				dtly = new DormTimeLineYearBean();
				dtly.setYear(year);
				dtly.getDtlbs().add(dtlb);
				dtlyMap.put(year, dtly);
			}else{
				dtly.getDtlbs().add(dtlb);
			}
		}
		return new ArrayList(dtlyMap.values());
	}
	
	/**
	 * 根据宿舍变更记录转换宿舍历程
	 * @param dormChangeApplys
	 * @return
	 * @author 周建明
	 * @createDate 2017年5月19日 下午2:20:33
	 */
	public List<DormTimeLineBean> getDormTimeLineByDormChangeApply(List<DormChangeApply> dormChangeApplys){
		List<DormTimeLineBean> dtlbs = new ArrayList<DormTimeLineBean>();
		//获取所有年份
		for (DormChangeApply dormChangeApply : dormChangeApplys) {
			DormTimeLineBean dtlb = new DormTimeLineBean();
			dtlb.setDate(dormChangeApply.getCtime());
			if(null!=dormChangeApply.getRw()){
				dtlb.setTitle(dormChangeApply.getRw().getYw().getMc());
			}else{
				dtlb.setTitle(dormChangeApply.getApplyType().getName());
			}
			dtlbs.add(dtlb);
		}
		return dtlbs;
	}
	
	public List<DormTimeLineBean> getDormTimeLineByApartmentRepair(List<ApartmentRepair> apartmentRepairs){
		List<DormTimeLineBean> dtlbs = new ArrayList<DormTimeLineBean>();
		for (ApartmentRepair apartmentRepair : apartmentRepairs) {
			DormTimeLineBean dtlb = new DormTimeLineBean();
			dtlb.setDate(apartmentRepair.getCtime());
			if(null!=apartmentRepair.getRw()){
				dtlb.setTitle(apartmentRepair.getRw().getMc());
			}else{
				dtlb.setTitle("公寓报修申请");
			}
			dtlbs.add(dtlb);
		}
		return dtlbs;
	}
	
	public List<DormChangeApply> getDormChangeApplys(String stdNo){
		OqlBuilder<DormChangeApply> query = OqlBuilder.from(DormChangeApply.class,"dormChangeApply");
		query.where("dormChangeApply.student.code =:stdNo",stdNo);
		query.where("(dormChangeApply.zt !=:ycx "
				+ " and dormChangeApply.zt !=:wtg and dormChangeApply.zt !=:btg)",YewuBean.ZT_YCX,YewuBean.ZT_WTG,"不通过");
		query.orderBy("dormChangeApply.ctime desc");
		return entityDao.search(query);
	}
	
	public void addDormPlanBed(DormBed dormBed, Student student) {
		DormPlanBed dormPlanBed = entityDao.getEntity(DormPlanBed.class, "bed.id", dormBed.getId());
		if(dormPlanBed!=null) {
			dormPlanBed.setStudent(student);
		}
	}
}
