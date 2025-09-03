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
import com.yzsoft.dorm.model.MajorAccomPlan;
import com.yzsoft.dorm.plan.importer.MajorAccomPlanImportListener;
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
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.TeachCalendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;

@Controller
@Scope("prototype")
public class MajorAccomPlanAction extends PlanCommonAction {
    @Autowired
    private DormAllocService dormAllocService;
    @Autowired
    private DormService dormService;
    @Autowired
    private DormPlanService dormPlanService;

    public MajorAccomPlanAction() {
    }

    public String getEntityName() {
        return MajorAccomPlan.class.getName();
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

        this.putCommonDatas();
    }

    public void indexSetting() {
        this.putDatas();
    }

    public OqlBuilder<MajorAccomPlan> getQueryBuilder() {
        Long dormPlanId = this.getLong("majorAccomPlan.dormPlan.id");
        if (null != dormPlanId) {
            DormPlan dormPlan = (DormPlan)this.entityDao.get(DormPlan.class, dormPlanId);
            this.put("dormPlan", dormPlan);
        }

        OqlBuilder<MajorAccomPlan> oql = OqlBuilder.from(MajorAccomPlan.class, "majorAccomPlan");
        this.populateConditions(oql);
        oql.orderBy(this.getOrderString()).limit(this.getPageLimit());
        this.put("isAdmin", this.dormService.isDormAdmin(this.getCurrentUser()));
        return oql;
    }

    public void checkAccomPlan(Entity<?> entity) {
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)entity;
        Assert.isTrue(!majorAccomPlan.getConfirm(), "该计划已确认无法进行修改！");
    }

    public void editSetting(Entity<?> entity) {
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)entity;
        this.put("majorAccomPlan", majorAccomPlan);
        this.putDatas();
    }

    public String saveAndForward(Entity<?> entity) {
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)entity;
        if (majorAccomPlan.getAllotNum() != null) {
            if (majorAccomPlan.getAllotNum() > majorAccomPlan.getPlanNum()) {
                this.put("majorAccomPlan", majorAccomPlan);
                this.putDatas();
                return this.forward("form", "专业计划 的计划人数不能小于已安排人数，已安排人数为" + majorAccomPlan.getAllotNum() + "人");
            }
        } else {
            majorAccomPlan.setAllotNum(0);
        }

        Map<String, Object> params = new HashMap();
        params.put("dormPlan.id", majorAccomPlan.getDormPlan().getId());
        params.put("major.id", majorAccomPlan.getMajor().getId());
        params.put("gender", majorAccomPlan.getGender());
        if (this.entityDao.duplicate(this.getEntityName(), majorAccomPlan.getId(), params)) {
            this.put("majorAccomPlan", majorAccomPlan);
            this.putDatas();
            return this.forward("edit", "专业计划已存在！");
        } else {
            Long departmentId = this.getLong("department.id");
            if (null != departmentId) {
                Department department = (Department)this.entityDao.get(Department.class, departmentId);
                DeptAccomPlan deptAccomPlan = this.dormPlanService.getDeptAccomPlan(majorAccomPlan.getDormPlan(), department, majorAccomPlan.getGender());
                majorAccomPlan.setDeptAccomPlan(deptAccomPlan);
                this.entityDao.saveOrUpdate(new Object[]{majorAccomPlan});
                return this.redirect("search", "保存成功！");
            } else {
                this.put("majorAccomPlan", majorAccomPlan);
                this.putDatas();
                return this.forward("edit", "院系计划不存在！");
            }
        }
    }

    public String info() {
        Long id = this.getEntityId("majorAccomPlan");
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)this.entityDao.get(MajorAccomPlan.class, id);
        this.put("majorAccomPlan", majorAccomPlan);
        return this.forward();
    }

    protected String removeAndForward(Collection<?> entities) {
        List<MajorAccomPlan> majorAccomPlans = (List)entities;
        Iterator var3 = majorAccomPlans.iterator();

        while(var3.hasNext()) {
            MajorAccomPlan majorAccomPlan = (MajorAccomPlan)var3.next();
            if (majorAccomPlan.getConfirm()) {
                this.addFlashWarning(majorAccomPlan.getMajor().getName() + "住宿计划已确认，无法删除!", new Object[0]);
                return this.redirect("search");
            }

            List<ClassAccomPlan> classPlans = this.entityDao.get(ClassAccomPlan.class, new String[]{"dormPlan.id", "adminClass.department.id", "gender"}, new Object[]{majorAccomPlan.getDormPlan().getId(), majorAccomPlan.getMajor().getDepartment().getId(), majorAccomPlan.getGender()});
            if (classPlans.size() > 0) {
                this.addFlashWarning(majorAccomPlan.getMajor().getName() + "已存在班级住宿计划,请先删除对应的班级住宿计划!", new Object[0]);
                return this.redirect("search");
            }
        }

        List<DormPlanBed> majorPlanBeds = CollectUtils.newArrayList();
        Iterator var7 = majorAccomPlans.iterator();

        MajorAccomPlan daps;
        while(var7.hasNext()) {
            daps = (MajorAccomPlan)var7.next();
            majorPlanBeds.addAll(daps.getPlanBeds());
        }

        var7 = majorAccomPlans.iterator();

        while(var7.hasNext()) {
            daps = (MajorAccomPlan)var7.next();
            daps.setPlanBeds((Set)null);
        }

        var7 = majorPlanBeds.iterator();

        while(var7.hasNext()) {
            DormPlanBed planBed = (DormPlanBed)var7.next();
            planBed.setMajorAccomPlan((MajorAccomPlan)null);
        }

        this.entityDao.saveOrUpdate(majorAccomPlans);
        this.entityDao.saveOrUpdate(majorPlanBeds);
        this.entityDao.remove(majorAccomPlans);
        return this.redirect("search", "info.remove.success");
    }

    public String confirm() {
        Long[] majorAccomPlanIds = this.getEntityIds();
        List<MajorAccomPlan> plans = this.entityDao.get(MajorAccomPlan.class, majorAccomPlanIds);
        Iterator var3 = plans.iterator();

        while(var3.hasNext()) {
            MajorAccomPlan plan = (MajorAccomPlan)var3.next();
            plan.setConfirm(true);
        }

        this.entityDao.saveOrUpdate(plans);
        return this.redirect("search", "info.save.success");
    }

    public String confirmCancle() {
        Long[] majorAccomPlanIds = this.getEntityIds();
        List<MajorAccomPlan> plans = this.entityDao.get(MajorAccomPlan.class, majorAccomPlanIds);
        Iterator var3 = plans.iterator();

        while(var3.hasNext()) {
            MajorAccomPlan plan = (MajorAccomPlan)var3.next();
            plan.setConfirm(false);
        }

        this.entityDao.saveOrUpdate(plans);
        return this.redirect("search", "info.save.success");
    }

    public String dormStudentList() {
        Long majorAccomPlanId = this.getEntityId("majorAccomPlan");
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)this.entityDao.get(MajorAccomPlan.class, majorAccomPlanId);
        this.put("majorAccomPlan", majorAccomPlan);
        String orderBy = this.get("orderBy");
        OqlBuilder<DormStudent> query = OqlBuilder.from(DormStudent.class, "dormStudent");
        query.where("dormStudent.plan =:plan", new Object[]{majorAccomPlan.getDormPlan()});
        query.where("dormStudent.student.gender.name =:gender", new Object[]{majorAccomPlan.getGender()});
        query.where("dormStudent.student.major =:major", new Object[]{majorAccomPlan.getMajor()});
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

    public String allocStdBed() {
        Long majorAccomPlanId = this.getEntityId("majorAccomPlan");
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)this.entityDao.get(MajorAccomPlan.class, majorAccomPlanId);
        Long[] dormStudentIds = this.getEntityIds("dormStudent");

        try {
            this.dormAllocService.alloc(dormStudentIds, majorAccomPlan);
            this.addFlashMessage("分配成功", new Object[0]);
        } catch (Exception var5) {
            this.addFlashWarning(var5.getMessage(), new Object[0]);
            logger.error(var5.getMessage(), var5);
        }

        return this.redirect("dormStudentList", "info.save.success", "&classAccomPlan.id=" + majorAccomPlanId);
    }

    public String allocCancle() {
        Long majorAccomPlanId = this.getEntityId("majorAccomPlan");
        Long[] dormStudentIds = this.getEntityIds("dormStudent");

        try {
            this.dormAllocService.allocCancle(dormStudentIds);
            this.addFlashMessage("取消分配成功", new Object[0]);
        } catch (Exception var4) {
            this.addFlashWarning(var4.getMessage(), new Object[0]);
            logger.error(var4.getMessage(), var4);
        }

        return this.redirect("dormStudentList", "info.save.success", "&majorAccomPlan.id=" + majorAccomPlanId);
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
        MajorAccomPlanImportListener importListener = new MajorAccomPlanImportListener(this.entityDao, dormPlan, this.dormPlanService);
        return importListener;
    }

    @Override
    public String dormPlanBedStdList() {
        Long majorAccomPlanId= getLong("dormPlanBed.majorAccomPlan.id");
        put("majorAccomPlanId",majorAccomPlanId);
        String orderBy = this.get("orderBy");
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        this.populateConditions(query);
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

        Long majorAccomPlanId = getLong("majorAccomPlanId");
        MajorAccomPlan majorAccomPlan = (MajorAccomPlan)this.entityDao.get(MajorAccomPlan.class, majorAccomPlanId);
        String orderBy = this.get("orderBy");
        OqlBuilder<DormPlanBed> query = OqlBuilder.from(DormPlanBed.class, "dormPlanBed");
        query.where("dormPlanBed.majorAccomPlan =:majorAccomPlan", new Object[]{majorAccomPlan});
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
}
