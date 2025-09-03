package com.yzsoft.yxxt.core.action;

import org.beangle.ems.security.model.GroupBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.*;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.action.BaseCommonAction;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentSelectAction extends BaseCommonAction {

    @Resource
    private CampusService campusService;
    @Resource
    private DepartmentService departmentService;
    @Resource
    private MajorService majorService;
    @Resource
    private DirectionService directionService;
    @Resource
    private DictDataService dictDataService;
    @Resource
    protected RestrictionHelper restrictionHelper;
    @Resource
    protected UserService userService;
    @Resource
    protected TeacherService teacherService;


    public String education() {
        put("datas", dictDataService.findDictData("XLCC"));
        return forward("datas");
    }

    public String campus() {
        List<Campus> campuses = campusService.findAllCampus();
        put("datas", campuses);
        return forward("datas");
    }

    public String department() {
        List<Department> departs = getDeparts();
        if (departs.size() == 1) {
            put("datas", departs);
        } else {
            List<Department> departments = departmentService.findTeachingDepartment();
            put("datas", departments);
        }
        return forward("datas");
    }


    protected List<Department> getDeparts() {
        List<Department> departments = restrictionHelper.getDeparts();
        if (departments.isEmpty() || userService.isMember(getCurrentUser(), new GroupBean(3079L)) || userService.isMember(getCurrentUser(), new GroupBean(3271L))) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            departments.clear();
            if (null != teacher && null != teacher.getDepartment()) {
                departments.add(teacher.getDepartment());
            }
        }
        return departments;
    }

    public String major() {
        Long departmentId = getLong("id");
        List<Major> majors = majorService.findMajorByDepartmentId(departmentId);
        put("datas", majors);
        return forward("datas");
    }

    public String direction() {
        Long departmentId = getLong("id");
        List<Direction> directions = directionService.findDirectionByMajorId(departmentId);
        put("datas", directions);
        return forward("datas");
    }

    public String adminClass() {
        Long majorId = getLong("id");
        OqlBuilder<AdminClass> oql = OqlBuilder.from(AdminClass.class, "adminClass");
        oql.where("adminClass.major.id=:majorId", majorId);
        oql.orderBy("adminClass.id desc");
        oql.limit(1, 100);
        List<AdminClass> datas = entityDao.search(oql);
        put("datas", datas);
        return forward("datas");
    }

}
