package org.beangle.product.core.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.importer.StudentImportListener;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.sync.BspSyncStudent;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yzsoft.yxxt.core.service.YxxtService;
import org.springframework.util.CollectionUtils;


/**
 * 学生管理
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年9月18日 上午10:02:13
 */
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Controller
public class StudentAction extends SyncAction {

    @Resource
    private StudentService studentService;

    @Resource
    private DictDataService dictDataService;

    @Resource
    protected YxxtService yxxtService;

    @Resource
    protected RestrictionHelper restrictionHelper;

    @Resource
    private DepartmentService departmentService;

    @Override
    protected String getEntityName() {
        return Student.class.getName();
    }

    protected void putDatas() {
        put("departments", getDeparts());
        put("majors", majorService.findAllMajor());
//		put("genders", getDictDataByDictTypeCode(DictTypeUtils.GENDER));
        put("genders", studentService.findGender());
        put("grades", gradeService.findAllGrade());
    }

    private List getDeparts() {
        List<Department> departments = restrictionHelper.getDeparts();
        if (departments.isEmpty()) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            if (null != teacher && null != teacher.getDepartment()) {
                departments.add(teacher.getDepartment());
            }
        }
        return departments;
    }

    @Override
    protected void indexSetting() {
        putDatas();
    }

    protected QueryBuilder<?> getQueryBuilder() {
        OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
        populateConditions(builder);
        List<Department> departs = getDeparts();
        //20230927修复年级查询条件无效bug begin
        String gradeIds=get("gradeIds");
        if(null!=gradeIds){
            if(gradeIds.indexOf(",")!=-1){
                String[] grades=gradeIds.split(",");
                List<String> list= Arrays.asList(grades);
                builder.where("student.grade in (:grades)", list);
            }else{
                builder.where("student.grade = :grade", gradeIds);
            }
        }
        //20230927修复年级查询条件无效bug end
        //是否为院系管理员
        if (departs.size() == 1) {
            builder.where("student.department in (:depts)", departs);
            put("isDepartmentAdmin", true);
        }else {
            put("isDepartmentAdmin", false);
        }
        QueryHelper.populateIds(builder, getShortName() + ".id");
        QueryHelper.populateAdParams(builder, get("adSearchParams"), getShortName(), "long");
        QueryHelper.populateAdParams(builder, get("adSearchBooleanParams"), getShortName(), "boolean");
        builder.orderBy(getOrderString()).limit(getPageLimit());
        return builder;
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        Student student = (Student) entity;
        put("student", student);
        putDatas();
        put("directions", directionService.findAllDirection());
        put("adminClasss", adminClassService.findAllAdminClass());
//		put("enrollTypes", findDictData("STD_ENROLL_TYPE"));
        put("enrollTypes", studentService.findEnrollType());
        put("trainTypes", findDictData("TRAIN_TYPE"));
        put("educations", findDictData(StudentService.XLCC));
        put("campuses", campusService.findAllCampus());

        if (isDeptAdmin()) {
            put("deptAdmin", true);
        }
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        Student student = (Student) entity;
        //学生学号唯一
        if (entityDao.duplicate(Student.class, student.getId(), "cardcode", student.getCardcode())) {
            put("student", student);
            putDatas();
            put("directions", directionService.findAllDirection());
            put("adminClasss", adminClassService.findAllAdminClass());
            return forward("edit", "该学生证件号" + student.getCardcode() + "已存在！");
        }

        if (null == student.getUser()) {
            String code = student.getCardcode().toUpperCase();
            User userBean = userService.get(code);
            //创建学生用户
            User user = bspBaseService.createNewUser(userBean, code, student.getName(), getCurrentUser(), 1L, "学生");
            student.setUser(user);
        }

/*        System.out.println(entityDao.get(Department.class,student.getDepartment().getId()).getName());
        System.out.println(entityDao.get(Department.class,getLong("department.id")).getName());
        System.out.println(entityDao.get(Major.class,student.getMajor().getId()).getName());
        System.out.println(entityDao.get(Major.class,getLong("major.id")).getName());
        System.out.println(entityDao.get(AdminClass.class,student.getAdminClass().getId()).getName());
        System.out.println(entityDao.get(AdminClass.class,getLong("adminClass.id")).getName());*/

/*        //new
        Department department = entityDao.get(Department.class, student.getDepartment().getId());
        Major major = entityDao.get(Major.class, student.getMajor().getId());
        AdminClass adminClass = entityDao.get(AdminClass.class, student.getAdminClass().getId());
        //old
        if (!student.getDepartment().getId().equals(getLong("department.id")) ||
                !student.getMajor().getId().equals(getLong("major.id")) ||
                !student.getAdminClass().getId().equals(getLong("adminClass.id"))
        ) {
            student.setAdminClass(adminClass);
            student.setDepartment(department);
            student.setMajor(major);
            //生成学号
            AdminClass bj = adminClass;

            //通过年份、院系、专业、班级，获取最大的个人序号
            String sql = "select max(substr(code,-2)) from cp_students where admin_class_id = " + bj.getId();
            List list = entityDao.searchSqlQuery(sql);
            StringBuffer xh = new StringBuffer();
            long j = 0;
            if (!CollectionUtils.isEmpty(list)) {
                j = NumberUtils.toInt((String) list.get(0));
            }
            j++;
            xh.append(bj.getCode());
            System.out.println(bj.getCode());
            xh.append(StringUtils.repeat("0", 2 - String.valueOf(j).length()) + j);
            student.setCode(xh.toString());
            StudentEnroll enroll = (StudentEnroll) entityDao.getEntity(StudentEnroll.class, "student", student);
            if (null != enroll) {
                enroll.setCode(xh.toString());
                entityDao.saveOrUpdate(enroll);
            }
        }*/


/*
        String xh = "";
        if (null != department && null != major && adminClass != null) {
            if (!department.getId().equals(getLong("department.id"))
                    || !major.getId().equals(getLong("major.id")) || !adminClass.getId().equals(getLong("adminClass.id"))) {
                xh = yxxtService.createXh(student);
                student.setCode(xh);
                StudentEnroll enroll = (StudentEnroll) entityDao.getEntity(StudentEnroll.class, "student", student);
                if (null != enroll) {
                    enroll.setCode(xh);
                    entityDao.saveOrUpdate(enroll);
                }
            }
        }
*/
        entityDao.saveOrUpdate(student);
        return redirect("search", "保存成功！");
    }

    @Override
    public ItemImporterListener getImporterListener() {
        StudentImportListener importListener = new StudentImportListener(getCurrentUser(), entityDao, dictDataService, bspBaseService);
        return importListener;
    }

    public String sync() {
        if (!hasTask()) {
            BspSyncStudent sync = new BspSyncStudent(studentService, getRequest().getSession());
            new Thread(sync).start();
        }
        return super.sync();
    }
}
