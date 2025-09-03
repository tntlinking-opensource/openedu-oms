//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.yzsoft.dorm.plan.action;

import com.yzsoft.dorm.model.ClassAccomPlan;
import com.yzsoft.dorm.model.DeptAccomPlan;
import com.yzsoft.dorm.model.DormPlan;
import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.dorm.model.DormStudent;
import com.yzsoft.dorm.plan.importer.ClassAccomPlanImportListener;
import com.yzsoft.dorm.plan.service.DormAllocService;
import com.yzsoft.dorm.plan.service.DormPlanService;
import com.yzsoft.dorm.system.service.DormService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yzsoft.yxxt.web.collect.service.CountUtil;
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
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.TeachCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope("prototype")
public class ClassAccomPlanAction extends PlanCommonAction {
    @Autowired
    private DormAllocService dormAllocService;
    @Autowired
    private DormService dormService;
    @Autowired
    private DormPlanService dormPlanService;

    public ClassAccomPlanAction() {
    }

    public String getEntityName() {
        return ClassAccomPlan.class.getName();
    }

    public void putDatas() {
        TeachCalendar curTeachCalendar = this.teachCalendarService.getCurrentTeachCalendar();
        this.put("years", this.teachCalendarService.findAllYear());
        this.put("curYear", curTeachCalendar);
        Long dormPlanId = this.getLong("deptAccomPlan.dormPlan.id");
        if (null != dormPlanId) {
            DormPlan dormPlan = (DormPlan)this.entityDao.get(DormPlan.class, dormPlanId);
            List<DormPlan> dormPlans = new ArrayList();
            dormPlans.add(dormPlan);
            this.put("dormPlans", dormPlans);
            this.put("dormPlan", dormPlan);
        } else {
            this.put("dormPlans", this.dormPlanService.findDormPlansByYear(curTeachCalendar.getYear()));
        }

    }

    public void indexSetting() {
        this.putDatas();
        boolean isDormAdmin = this.dormService.isDormAdmin(this.getCurrentUser());
        if (isDormAdmin) {
            this.put("departments", this.departmentService.findTeachingDepartment());
            this.put("majors", this.majorService.findAllMajor());
        } else {
            this.put("departments", this.departmentService.findTeachingDepartment());
        }

    }

    public OqlBuilder<ClassAccomPlan> getQueryBuilder() {
        Long dormPlanId = this.getLong("classAccomPlan.dormPlan.id");
        if (null != dormPlanId) {
            DormPlan dormPlan = (DormPlan)this.entityDao.get(DormPlan.class, dormPlanId);
            this.put("dormPlan", dormPlan);
        }

        boolean isDormAdmin = this.dormService.isDormAdmin(this.getCurrentUser());
        OqlBuilder<ClassAccomPlan> oql = OqlBuilder.from(ClassAccomPlan.class, "classAccomPlan");
        if (!isDormAdmin) {
            List<AdminClass> adminClassList = this.adminClassService.findAdminClassByInstructor(this.getUsername());
            if (adminClassList.size() > 0) {
                oql.where("classAccomPlan.adminClass in(:adminClasses)", new Object[]{adminClassList});
            } else {
                oql.where("1=2");
            }
        }

        this.populateConditions(oql);
        oql.orderBy(this.getOrderString()).limit(this.getPageLimit());
        this.put("isAdmin", this.dormService.isDormAdmin(this.getCurrentUser()));
        return oql;
    }

    public String edit() {
        this.putDatas();
        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = null;
        if (null == classAccomPlanId) {
            classAccomPlan = new ClassAccomPlan();
        } else {
            classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, classAccomPlanId);
            if (classAccomPlan.getConfirm()) {
                this.addFlashWarning("该计划已确认无法进行修改！", new Object[0]);
                return this.redirect("search");
            }

            AdminClass adminClass = (AdminClass)this.entityDao.get(AdminClass.class, classAccomPlan.getAdminClass().getId());
            this.put("majorList", this.majorService.findMajorByDepartmentCode(adminClass.getDepartment().getCode()));
            List<AdminClass> adminClasses = new ArrayList();
            adminClasses.add(adminClass);
            this.put("adminClasses", adminClasses);
        }

        this.put("classAccomPlan", classAccomPlan);
        this.put("departments", this.departmentService.findTeachingDepartment());
        return this.forward();
    }

    public String saveAndForward(Entity<?> entity) {
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)entity;
        AdminClass adminClass = (AdminClass)this.entityDao.get(AdminClass.class, classAccomPlan.getAdminClass().getId());
        this.put("majorList", this.majorService.findMajorByDepartmentCode(adminClass.getDepartment().getCode()));
        List<AdminClass> adminClasses = new ArrayList();
        adminClasses.add(adminClass);
        this.put("adminClasses", adminClasses);
        this.put("departments", this.departmentService.findTeachingDepartment());
        if (classAccomPlan.getAllotNum() != null) {
            if (classAccomPlan.getAllotNum() > classAccomPlan.getPlanNum()) {
                this.put("classAccomPlan", classAccomPlan);
                this.putDatas();
                return this.forward("form", "班级计划 的计划人数不能小于已安排人数，已安排人数为" + classAccomPlan.getAllotNum() + "人");
            }
        } else {
            classAccomPlan.setAllotNum(0);
        }

        Map<String, Object> params = new HashMap();
        params.put("dormPlan.id", classAccomPlan.getDormPlan().getId());
        params.put("adminClass.id", classAccomPlan.getAdminClass().getId());
        params.put("gender", classAccomPlan.getGender());
        if (this.entityDao.duplicate(this.getEntityName(), classAccomPlan.getId(), params)) {
            this.put("classAccomPlan", classAccomPlan);
            this.putDatas();
            return this.forward("edit", "班级计划已存在！");
        } else {
            DeptAccomPlan deptAccomPlan = this.dormPlanService.getDeptAccomPlan(classAccomPlan.getDormPlan(), classAccomPlan.getDeptAccomPlan().getDepartment(), classAccomPlan.getGender());
            classAccomPlan.setDeptAccomPlan(deptAccomPlan);
            this.entityDao.saveOrUpdate(new Object[]{classAccomPlan});
            return this.redirect("search", "保存成功！");
        }
    }

    public String info() {
        Long id = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, id);
        this.put("classAccomPlan", classAccomPlan);
        return this.forward();
    }

    protected String removeAndForward(Collection<?> entities) {
        List<ClassAccomPlan> classAccomPlans = (List)entities;
        List<DormPlanBed> classPlanBeds = CollectUtils.newArrayList();
        Iterator var4 = classAccomPlans.iterator();

        ClassAccomPlan daps;
        while(var4.hasNext()) {
            daps = (ClassAccomPlan)var4.next();
            classPlanBeds.addAll(daps.getPlanBeds());
        }

        var4 = classAccomPlans.iterator();

        while(var4.hasNext()) {
            daps = (ClassAccomPlan)var4.next();
            daps.setPlanBeds((Set)null);
        }

        this.entityDao.saveOrUpdate(classAccomPlans);
        var4 = classPlanBeds.iterator();

        while(var4.hasNext()) {
            DormPlanBed planBed = (DormPlanBed)var4.next();
            planBed.setClassAccomPlan((ClassAccomPlan)null);
        }

        this.entityDao.saveOrUpdate(classPlanBeds);
        this.entityDao.remove(classAccomPlans);
        return this.redirect("search", "info.remove.success");
    }

    public String confirm() {
        Long[] classAccomPlanIds = this.getEntityIds();
        List<ClassAccomPlan> plans = this.entityDao.get(ClassAccomPlan.class, classAccomPlanIds);
        Iterator var3 = plans.iterator();

        while(var3.hasNext()) {
            ClassAccomPlan plan = (ClassAccomPlan)var3.next();
            plan.setConfirm(true);
        }

        this.entityDao.saveOrUpdate(plans);
        return this.redirect("search", "info.save.success");
    }

    public String confirmCancle() {
        Long[] classAccomPlanIds = this.getEntityIds();
        List<ClassAccomPlan> plans = this.entityDao.get(ClassAccomPlan.class, classAccomPlanIds);
        Iterator var3 = plans.iterator();

        while(var3.hasNext()) {
            ClassAccomPlan plan = (ClassAccomPlan)var3.next();
            plan.setConfirm(false);
        }

        this.entityDao.saveOrUpdate(plans);
        return this.redirect("search", "info.save.success");
    }

    public String dormStudentList() {
        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, classAccomPlanId);
        this.put("classAccomPlan", classAccomPlan);
        Boolean isArranged = this.getBoolean("isArranged");
        String orderBy = this.get("orderBy");
        OqlBuilder<DormStudent> query = OqlBuilder.from(DormStudent.class, "dormStudent");
        query.where("dormStudent.plan =:plan", new Object[]{classAccomPlan.getDormPlan()});
        query.where("dormStudent.student.gender.name =:gender", new Object[]{classAccomPlan.getGender()});
        query.where("dormStudent.student.adminClass =:adminClass", new Object[]{classAccomPlan.getAdminClass()});
        if (null != isArranged && isArranged) {
            query.where("dormStudent.planBed is not null");
        }

        if (StringUtils.isEmpty(orderBy)) {
            query.orderBy("dormStudent.student.code");
        } else {
            query.orderBy(orderBy);
        }

        query.limit(this.getPageLimit());
        this.put("dormStudents", this.entityDao.search(query));
        this.put("planName", this.get("planName"));
        return this.forward();
    }

    public String dormPlanBedStdList() {
        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, classAccomPlanId);
        this.put("classAccomPlan", classAccomPlan);
        String orderBy = this.get("orderBy");
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        query.where("dormPlanBed.classAccomPlan =:classAccomPlan", new Object[]{classAccomPlan});
        query.where("dormPlanBed.student is not null");
        if (StringUtils.isEmpty(orderBy)) {
            query.orderBy("dormPlanBed.student.code");
        } else {
            query.orderBy(orderBy);
        }

        query.limit(this.getPageLimit());
        this.put("dormPlanBeds", this.entityDao.search(query));
        this.put("planName", this.get("planName"));
        return this.forward();
    }

    public void exportExcelStdNum() {
        HttpServletRequest request = ServletActionContext.getRequest();
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet("sheet1");
        int rowNum = 0;

        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, classAccomPlanId);
        String orderBy = this.get("orderBy");
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        query.where("dormPlanBed.classAccomPlan =:classAccomPlan", new Object[]{classAccomPlan});
        query.where("dormPlanBed.student is not null");
        if (StringUtils.isEmpty(orderBy)) {
            query.orderBy("dormPlanBed.student.code");
        } else {
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

    public String allocStdBed() {
        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        ClassAccomPlan classAccomPlan = (ClassAccomPlan)this.entityDao.get(ClassAccomPlan.class, classAccomPlanId);
        Long[] dormStudentIds = this.getEntityIds("dormStudent");

        try {
            this.dormAllocService.alloc(dormStudentIds, classAccomPlan);
            this.addFlashMessage("分配成功", new Object[0]);
        } catch (Exception var5) {
            this.addFlashWarning(var5.getMessage(), new Object[0]);
            logger.error(var5.getMessage(), var5);
        }

        return this.redirect("dormStudentList", "info.save.success", "&classAccomPlan.id=" + classAccomPlanId);
    }

    public String allocCancle() {
        Long classAccomPlanId = this.getEntityId("classAccomPlan");
        Long[] dormStudentIds = this.getEntityIds("dormStudent");

        try {
            this.dormAllocService.allocCancle(dormStudentIds);
            this.addFlashMessage("取消分配成功", new Object[0]);
        } catch (Exception var4) {
            this.addFlashWarning(var4.getMessage(), new Object[0]);
            logger.error(var4.getMessage(), var4);
        }

        return this.redirect("dormStudentList", "info.save.success", "&classAccomPlan.id=" + classAccomPlanId);
    }

    public String importForm() {
        Long dormPlanId = this.getEntityId("dormPlan");
        DormPlan dormPlan = (DormPlan)this.entityDao.get(DormPlan.class, dormPlanId);
        this.put("dormPlan", dormPlan);
        return this.forward();
    }

    public ItemImporterListener getImporterListener() {
        Long dormPlanId = this.getEntityId("dormPlan");
        DormPlan dormPlan = (DormPlan)this.entityDao.get(DormPlan.class, dormPlanId);
        ClassAccomPlanImportListener importListener = new ClassAccomPlanImportListener(this.entityDao, dormPlan, this.dormPlanService);
        return importListener;
    }
}
