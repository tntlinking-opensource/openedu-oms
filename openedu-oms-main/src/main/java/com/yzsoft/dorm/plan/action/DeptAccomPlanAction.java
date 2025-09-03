package com.yzsoft.dorm.plan.action;

import com.yzsoft.dorm.model.*;
import com.yzsoft.dorm.model.rule.DormRule;
import com.yzsoft.dorm.plan.bean.DormAllocState;
import com.yzsoft.dorm.plan.importer.DeptAccomPlanImportListener;
import com.yzsoft.dorm.plan.service.DormAllocService;
import com.yzsoft.dorm.plan.service.DormPlanService;
import com.yzsoft.dorm.system.service.DormService;
import com.yzsoft.yxxt.web.collect.service.CountUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.model.Teacher;
import org.beangle.struts2.convention.route.Action;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * 院系住宿计划Action
 * @author 王春
 * @时间 2016-5-13上午11:03:19
 * @公司	上海彦致信息科技有限公司
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DeptAccomPlanAction extends PlanCommonAction {
	
	private static final String ALLOC_STATE = "DORM_ALLOC_STATE";
	
	@Autowired
	private DormService dormService;
	
	@Autowired
	private DormPlanService dormPlanService;
	
	@Autowired
	private DormAllocService dormAllocService;
	
	public String getEntityName(){
		return DeptAccomPlan.class.getName();
	}
	
	public void putDatas(){
		TeachCalendar curTeachCalendar = teachCalendarService.getCurrentTeachCalendar();
		put("years", teachCalendarService.findAllYear());
		put("curYear", curTeachCalendar);
		
		put("departments",getDeparts());// 学院
		
		Long dormPlanId = getLong("deptAccomPlan.dormPlan.id");
		if(null!=dormPlanId){
			DormPlan dormPlan =  entityDao.get(DormPlan.class, dormPlanId);
			List<DormPlan> dormPlans = new ArrayList<DormPlan>();
			dormPlans.add(dormPlan);
			put("dormPlans", dormPlans);
			put("dormPlan", dormPlan);
		}else{
			put("dormPlans", dormPlanService.findDormPlansByYear(curTeachCalendar.getYear()));
		}
	}

	public List getDeparts() {
		List<Department> departments = restrictionHelper.getDeparts();
		if (departments.isEmpty()) {
			Teacher teacher = teacherService.getTeacherByCode(getUsername());
			if (null != teacher && null != teacher.getDepartment()) {
				departments.add(teacher.getDepartment());
			}
		}
		return departments;
	}
	
	public void indexSetting(){
		putDatas();
	}
	
	public OqlBuilder<DeptAccomPlan> getQueryBuilder(){
		Long dormPlanId = getLong("deptAccomPlan.dormPlan.id");
		if(null!=dormPlanId){
			DormPlan dormPlan = entityDao.get(DormPlan.class, dormPlanId);
			put("dormPlan", dormPlan);
		}
		List<Department> departments = getTeachDeparts();
		OqlBuilder<DeptAccomPlan> query = OqlBuilder.from(DeptAccomPlan.class,"deptAccomPlan");
		if(departments.size()>0){
			query.where("deptAccomPlan.department in(:departments)",departments);
		}else{
			query.where("1=2");
		}
		populateConditions(query);
		query.orderBy(getOrderString()).limit(getPageLimit());
		put("isAdmin",dormService.isDormAdmin(getCurrentUser())?true:false);
		return query;
	}
	
	protected String getDefaultOrderString() {
		return "deptAccomPlan.department.code";
	}
	
	/**
	 * 验证计划是否确认
	 */
	@Override
	public void checkAccomPlan(Entity<?> entity){
		DeptAccomPlan deptAccomPlan = (DeptAccomPlan)entity;
		Assert.isTrue(!deptAccomPlan.getConfirm(), "该计划已确认无法进行修改！");
	}
	
	public void editSetting(Entity<?> entity){
		DeptAccomPlan deptAccomPlan = (DeptAccomPlan)entity;
		put("deptAccomPlan",deptAccomPlan);
		putDatas();
	}
	
	public String saveAndForward(Entity<?> entity){
		DeptAccomPlan deptAccomPlan = (DeptAccomPlan)entity;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("dormPlan.id", deptAccomPlan.getDormPlan().getId());
		params.put("department.id", deptAccomPlan.getDepartment().getId());
		params.put("gender", deptAccomPlan.getGender());
		if (entityDao.duplicate(getEntityName(), deptAccomPlan.getId(), params)) {
		   put("deptAccomPlan", deptAccomPlan);
		   putDatas();
		   return forward("edit","院系计划已存在！");
		}
		
		if(deptAccomPlan.getAllotNum() != null){
			if(null!=deptAccomPlan.getRealNum() && deptAccomPlan.getRealNum() > deptAccomPlan.getPlanNum()){
				put("deptAccomPlan",deptAccomPlan);
				putDatas();
				return forward("form","院系计划  计划人数不能小于实际人数,实际人数为"+deptAccomPlan.getRealNum()+"人!");
			}
			
			if(null!=deptAccomPlan.getAllotNum() && deptAccomPlan.getAllotNum() > deptAccomPlan.getPlanNum()){
				put("deptAccomPlan",deptAccomPlan);
				putDatas();
				return forward("form","院系计划  计划人数不能小于已安排人数,已安排人数为"+deptAccomPlan.getAllotNum()+"人!");
			}
		}else{
			deptAccomPlan.setAllotNum(0);
		}
		entityDao.saveOrUpdate(deptAccomPlan);
		return redirect("search","保存成功！");
	}
	
	@SuppressWarnings("unchecked")
	public String removeAndForward(Collection<?> entities){
		List<DeptAccomPlan> deptAccomPlans = (List<DeptAccomPlan>) entities;
		for (DeptAccomPlan deptAccomPlan : deptAccomPlans) {
			//验证院系计划是否确认
			if(deptAccomPlan.getConfirm()){
				addFlashWarning(deptAccomPlan.getDepartment().getName()+"住宿计划已确认，无法删除!");
				return redirect("search");
			}
			
			//验证是否存在专业计划
			List<MajorAccomPlan> clist = entityDao.get(MajorAccomPlan.class, new String[]{"dormPlan.id","major.department.id", "gender"}, 
					deptAccomPlan.getDormPlan().getId(),deptAccomPlan.getDepartment().getId(),deptAccomPlan.getGender());
			
			if(clist.size()>0){
				addFlashWarning(deptAccomPlan.getDepartment().getName()+"已存在专业住宿计划,请先删除对应的专业住宿计划!");
				return redirect("search");
			}
			
			//验证是否存在班级计划
			List<ClassAccomPlan> classPlans = entityDao.get(ClassAccomPlan.class, new String[]{"dormPlan.id","adminClass.department.id", "gender"}, 
					deptAccomPlan.getDormPlan().getId(),deptAccomPlan.getDepartment().getId(),deptAccomPlan.getGender());
			
			if(classPlans.size()>0){
				addFlashWarning(deptAccomPlan.getDepartment().getName()+"已存在班级住宿计划,请先删除对应的班级住宿计划!");
				return redirect("search");
			}
		}
		
		
		List<DormPlanBed> deptPlanBeds=CollectUtils.newArrayList();//清除班级床位
		List<DormBed> dormBeds=CollectUtils.newArrayList();//释放床位占用
		
		for(DeptAccomPlan dept : deptAccomPlans){
			deptPlanBeds.addAll(dept.getPlanBeds());
		}
		
		for (DeptAccomPlan daps : deptAccomPlans) {
			daps.setPlanBeds(null);
		}
		
		for (DormPlanBed dormPlanBed : deptPlanBeds) {
			DormBed dormBed = dormPlanBed.getBed();
			if(null==dormBed.getStudent()){
				dormBed.setState(DormBed.STATE_FREE);//释放床位计划占用
			}
			dormBeds.add(dormBed);
		}
		
		entityDao.saveOrUpdate(deptAccomPlans);
		entityDao.remove(deptPlanBeds);
		entityDao.remove(deptAccomPlans);
		entityDao.saveOrUpdate(dormBeds);
		return redirect("search", "info.remove.success");
	}
	
	public String dormArrange(){
		DeptAccomPlan deptAccomPlan = getEntity(DeptAccomPlan.class);
		put("deptAccomPlan",deptAccomPlan);
		return forward();
	}
	
	/**
	 * 学院住宿名单
	 * @return
	 * @author 周建明
	 * @createDate 2017年2月13日 下午4:05:57
	 */
	public String dormStudentList(){
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		put("deptAccomPlan", deptAccomPlan);
		
		Boolean isArranged = getBoolean("isArranged");
		
		String orderBy = get(Order.ORDER_STR);
		OqlBuilder<DormStudent> query = OqlBuilder.from(DormStudent.class, "dormStudent");
		query.where("dormStudent.plan =:plan",deptAccomPlan.getDormPlan());
		query.where("dormStudent.student.gender.name =:gender",deptAccomPlan.getGender());
		query.where("dormStudent.student.department =:department",deptAccomPlan.getDepartment());
		if(null!=isArranged&&isArranged){
			query.where("dormStudent.planBed is not null");
		}
		if(StringUtils.isEmpty(orderBy)){
			query.orderBy("dormStudent.student.code");
		}else{
			query.orderBy(orderBy);
		}
		query.limit(getPageLimit());
		put("dormStudents", entityDao.search(query));
		return forward();
	}
	
	public String confirm() {
		Long[] ids = getEntityIds();
		List<DeptAccomPlan> plans = entityDao.get(DeptAccomPlan.class, ids);
		for (DeptAccomPlan plan : plans) {
			plan.setConfirm(true);
		}
		entityDao.saveOrUpdate(plans);
		return redirect("search", "info.save.success");
	}

	public String confirmCancle() {
		Long[] ids = getEntityIds();
		List<DeptAccomPlan> plans = entityDao.get(DeptAccomPlan.class, ids);
		for (DeptAccomPlan plan : plans) {
			if(!plan.getFinish()){
				plan.setConfirm(false);
			}else{
				addFlashWarning(plan.getDepartment().getName()+"——"+plan.getGender()+"计划已发布完成，无法取消确认！");
			}
		}
		entityDao.saveOrUpdate(plans);
		return redirect("search", "info.save.success");
	}
	
	/**
	 * 最终确认发布
	 * @return
	 * @author 周建明
	 * @createDate 2017年2月23日 下午4:47:56
	 */
	public String publish(){
		Long[] ids = getEntityIds();
		List<DeptAccomPlan> plans = entityDao.get(DeptAccomPlan.class, ids);
		for (DeptAccomPlan plan : plans) {
			if(!plan.getFinish()){
				Long unAllocStudentNum = dormAllocService.getUnAllocStudentNum(plan);
				if (unAllocStudentNum > 0) {
					addFlashWarning(String.format("还有%d个学生未分配床位,无法发布", unAllocStudentNum));
				}else{
					dormAllocService.publish(plan);
				}
			}
		}
		return redirect("search", "发布成功！");
	}
	
	public String allocStdBed(){
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		Long[] dormStudentIds = getEntityIds("dormStudent");
		try {
			dormAllocService.alloc(dormStudentIds);
			addFlashMessage("分配成功");
		} catch (Exception e) {
			addFlashWarning(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		return redirect("dormStudentList","","&deptAccomPlan.id="+deptAccomPlanId);
	}
	
	public String allocCancle() {
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		Long[] dormStudentIds = getEntityIds("dormStudent");
		try{
			dormAllocService.allocCancle(dormStudentIds);
			addFlashMessage("取消分配成功");
		}catch (Exception e) {
			addFlashWarning(e.getMessage());
			logger.error(e.getMessage(),e);
		}
		return redirect("dormStudentList","","&deptAccomPlan.id="+deptAccomPlanId);
	}
	
	
	public String alloc() {
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		
		if(deptAccomPlan.getFinish()){
			addFlashWarning("学院住宿计划已发布确认，无法进行床位自动分配！");
			return redirect("search");
		}
		
		//验证床位安排是否确认
		if(!deptAccomPlan.getConfirm()){
			addFlashWarning("学院住宿计划尚未确认，无法进行床位自动分配！");
			return redirect("search");
		}
		
		List<DormRule> rules = dormService.findRule();
		put("rules", rules);
		put("studentNum", dormAllocService.getUnAllocStudentNum(deptAccomPlan));
		put("deptAccomPlan", deptAccomPlan);
		return forward();
	}
	
	public String manualAlloc() {
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		
		if(deptAccomPlan.getFinish()){
			addFlashWarning("学院住宿计划已发布确认，无法进行床位自动分配！");
			return redirect("search");
		}
		
		//验证床位安排是否确认
		if(!deptAccomPlan.getConfirm()){
			addFlashWarning("学院住宿计划尚未确认，无法进行床位自动分配！");
			return redirect("search");
		}
		return redirect(new Action(DormPlanBedAllocAction.class, "index","&deptAccomPlan.id="+deptAccomPlanId));
	}
	
	
	public void allocStart() {
		final Long deptAccomPlanId = getEntityId("deptAccomPlan");
		final DormAllocState state = new DormAllocState();
		getRequest().getSession().setAttribute(ALLOC_STATE, state);
		final Long[] ruleIds = getEntityIds("rule", Long.class);
		new Thread(new Runnable() {
			public void run() {
				SessionFactory sessionFactory = entityDao.getSessionFactory();
				Session session = SessionFactoryUtils.getSession(sessionFactory, true);
				TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
				DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
				List<DormRule> dormRules = entityDao.get(DormRule.class, ruleIds);
				try {
					dormAllocService.alloc(deptAccomPlan, dormRules, state);
				} catch (Exception e) {
					logger.error(e.getMessage());
					e.printStackTrace();
				}
				TransactionSynchronizationManager.unbindResource(sessionFactory);
				SessionFactoryUtils.closeSession(session);
			}
		}).start();
	}
	
	
	public void alocState() {
		try {
			DormAllocState state = (DormAllocState) getRequest().getSession().getAttribute(ALLOC_STATE);
			JSONObject jsonObject = JSONObject.fromObject(state);
			getResponse().setCharacterEncoding("UTF-8");
			getResponse().getWriter().write(jsonObject.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String cleanAlloc() {
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		if(!deptAccomPlan.getFinish()){
			dormAllocService.clearAlloc(deptAccomPlan);
		}
		return redirect("alloc","清除成功！","&deptAccomPlan.id="+deptAccomPlanId);
	}
	
	public String dormPlanBedStdList(){
		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		put("deptAccomPlan", deptAccomPlan);
		
		String orderBy = get(Order.ORDER_STR);
		OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
		query.where("dormPlanBed.deptAccomPlan =:deptAccomPlan",deptAccomPlan);
		query.where("dormPlanBed.student is not null");
		if(StringUtils.isEmpty(orderBy)){
			query.orderBy("dormPlanBed.student.code");
		}else{
			query.orderBy(orderBy);
		}
		query.limit(getPageLimit());
		put("dormPlanBeds", entityDao.search(query));
		return forward();
	}
	
	@Override
	public String importForm() {
		Long dormPlanId = getEntityId("dormPlan");
		DormPlan dormPlan = entityDao.get(DormPlan.class, dormPlanId);
		put("dormPlan", dormPlan);
		return forward();
	}
	  
	@Override
	public ItemImporterListener getImporterListener() {
		Long dormPlanId = getEntityId("dormPlan");
		DormPlan dormPlan = entityDao.get(DormPlan.class, dormPlanId);
		
		DeptAccomPlanImportListener importListener = new DeptAccomPlanImportListener(entityDao,dormPlan);
		return importListener;
	}

	public void exportExcelStdNum() {

		HttpServletRequest request = ServletActionContext.getRequest();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		int rowNum = 0;

//        sheet.createRow(rowNum++).createCell(0).setCellValue("学生志愿统计");

		Long deptAccomPlanId = getEntityId("deptAccomPlan");
		DeptAccomPlan deptAccomPlan = entityDao.get(DeptAccomPlan.class, deptAccomPlanId);
		String orderBy = get(Order.ORDER_STR);
		OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
		query.where("dormPlanBed.deptAccomPlan =:deptAccomPlan",deptAccomPlan);
		query.where("dormPlanBed.student is not null");
		if(StringUtils.isEmpty(orderBy)){
			query.orderBy("dormPlanBed.student.code");
		}else{
			query.orderBy(orderBy);
		}
		List<DormPlanBed> dormPlanBeds = entityDao.search(query);

		String[] nums = new String[]{"一", "二", "三", "四", "五", "六"};
		Map<String, Object> datas = (Map<String, Object>) request.getAttribute("datas");

		HSSFRow titleRow = sheet.createRow(rowNum++);
		titleRow.createCell(0).setCellValue("学号");
		titleRow.createCell(1).setCellValue("姓名");
		titleRow.createCell(2).setCellValue("性别");
		titleRow.createCell(3).setCellValue("年级");
		titleRow.createCell(4).setCellValue("学院");
		titleRow.createCell(5).setCellValue("班级");
		titleRow.createCell(6).setCellValue("学历层次");
		titleRow.createCell(7).setCellValue("入学年月");
		titleRow.createCell(8).setCellValue("校区");
		titleRow.createCell(9).setCellValue("宿舍区");
		titleRow.createCell(10).setCellValue("楼栋");
		titleRow.createCell(11).setCellValue("宿舍");
		titleRow.createCell(12).setCellValue("床位");
		titleRow.createCell(13).setCellValue("租金");

		for (DormPlanBed dormPlanBed : dormPlanBeds) {
			HSSFRow row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(dormPlanBed.getStudent().getCode());
			row.createCell(1).setCellValue(dormPlanBed.getStudent().getName());
			row.createCell(2).setCellValue(dormPlanBed.getStudent().getGender().getName());
			row.createCell(3).setCellValue(dormPlanBed.getStudent().getGrade());
			row.createCell(4).setCellValue(dormPlanBed.getStudent().getDepartment().getName());
			row.createCell(5).setCellValue(dormPlanBed.getStudent().getAdminClass().getName());
			row.createCell(6).setCellValue(dormPlanBed.getStudent().getEducation().getName());
			if(null!=dormPlanBed.getStudent().getEnrollmentDate()){
				row.createCell(7).setCellValue(dormPlanBed.getStudent().getEnrollmentDate());
			}else {
				row.createCell(7).setCellValue("");
			}
			row.createCell(8).setCellValue(dormPlanBed.getStudent().getCampus().getName());
			row.createCell(9).setCellValue(dormPlanBed.getBed().getRoom().getBuilding().getZone().getName());
			row.createCell(10).setCellValue(dormPlanBed.getBed().getRoom().getBuilding().getName());
			row.createCell(11).setCellValue(dormPlanBed.getBed().getRoom().getCode());
			row.createCell(12).setCellValue(dormPlanBed.getBed().getName());
			row.createCell(13).setCellValue(dormPlanBed.getBed().getRoom().getRent());
		}
		CountUtil.output(wb, "export.xls");
	}

}
