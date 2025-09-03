package org.beangle.product.core.action;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.model.GroupBean;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.importer.AdminClassImportListener;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.AdminClassService;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.sync.BspSyncAdminClass;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.common.util.SeqStringUtil;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * 班级管理
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年9月13日 下午5:12:22
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller
public class AdminClassAction extends SyncAction {

    /**
     * 专业接口
     */
    @Autowired
    private MajorService majorService;

    /**
     * 部门院系接口
     */
    @Autowired
    private DepartmentService departmentService;

    /**
     * 班级接口
     */
    @Autowired
    private AdminClassService adminClassService;

    @Resource
    protected RestrictionHelper restrictionHelper;

    @Override
    protected String getEntityName() {
        return AdminClass.class.getName();
    }

    protected void putDatas() {
        put("departs", getDeparts());
        put("majors", majorService.findAllMajor());
        put("enrollTypes", dictDataService.findDictData("STD_ENROLL_TYPE"));
    }


    protected List<Department> getDeparts() {
        List<Department> departments = restrictionHelper.getDeparts();
        if (departments.isEmpty() || userService.isMember(getCurrentUser(), new GroupBean(3079L))) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            departments.clear();
            if (null != teacher && null != teacher.getDepartment()) {
                departments.add(teacher.getDepartment());
            }
        }
        ArrayList<Department> departments1 = new ArrayList<Department>();
        for (int i = 0; i < departments.size(); i++) {
            if (departments.get(i).isEnabled()) {
                departments1.add(departments.get(i));
            }
        }
        return departments1;
    }


    @Override
    protected void indexSetting() {
        putDatas();
    }

    protected QueryBuilder<?> getQueryBuilder() {
        String instructorCode = get("instructorCode");
        String instructorName = get("instructorName");
        Long enrollTypeId = getLong("enrollTypeId");

        OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
        List<Department> departs = getDeparts();
        if (departs.size() == 1) {
            builder.where("adminClass.department in (:depts)", departs);
        }
        populateConditions(builder);
        if (StringUtils.isNotEmpty(instructorCode) || StringUtils.isNotEmpty(instructorName)) {
            builder.join(getShortName() + ".instructors", "instructor");
            if (StringUtils.isNotEmpty(instructorCode)) {
                builder.where("instructor.code like:instructorCode", "%" + instructorCode + "%");
            }
            if (StringUtils.isNotEmpty(instructorName)) {
                builder.where("instructor.code like:instructorName", "%" + instructorName + "%");
            }
        }
        if (null != enrollTypeId) {
            builder.join(getShortName() + ".enrolls", "enroll");
            builder.where("enroll.id =:enrollTypeId", enrollTypeId);
        }
        QueryHelper.populateIds(builder, getShortName() + ".id");
        builder.orderBy(getOrderString()).limit(getPageLimit());
        return builder;
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        putDatas();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        AdminClass adminClass = (AdminClass) entity;

        //班级代码唯一
        if (entityDao.duplicate(AdminClass.class, adminClass.getId(), "code", adminClass.getCode())) {
            put("adminClass", adminClass);
            putDatas();
            return forward("edit", "该班级代码" + adminClass.getCode() + "已存在！");
        }
        Long[] instructorIds = SeqStringUtil.transformToLong(get("instructorIds"));
        adminClass.getInstructors().clear();
        if (instructorIds != null && instructorIds.length > 0) {
            List<Teacher> instructors = entityDao.get(Teacher.class, instructorIds);
            adminClassService.addInstructorGroupByTeachers(instructors);

            adminClass.getInstructors().addAll(instructors);
            adminClass.setInstructor(instructors.get(0));
        } else {
            adminClass.setInstructor(null);
        }

        Long[] enrollTypeIds = StrUtils.splitToLong(get("enrollTypeId"));
        adminClass.getEnrolls().clear();
        adminClass.getEnrolls().addAll(entityDao.get(DictData.class, enrollTypeIds));
        entityDao.saveOrUpdate(adminClass);
        return redirect("search", "info.save.success");
    }

    @Override
    public ItemImporterListener getImporterListener() {
        AdminClassImportListener importListener = new AdminClassImportListener(entityDao, departmentService, majorService);
        return importListener;
    }

    public String sync() {
        if (!hasTask()) {
            BspSyncAdminClass sync = new BspSyncAdminClass(adminClassService, getRequest().getSession());
            new Thread(sync).start();
        }
        return super.sync();
    }
}
