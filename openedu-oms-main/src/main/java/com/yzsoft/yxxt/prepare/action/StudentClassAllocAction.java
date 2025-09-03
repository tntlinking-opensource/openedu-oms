package com.yzsoft.yxxt.prepare.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.yzsoft.yxxt.prepare.importer.StudentImportListener;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.exception.MyException;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.model.GroupBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.exporter.Context;
import org.beangle.model.transfer.exporter.Exporter;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.AdminClassService;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.struts2.action.ExportObjectThread;
import org.beangle.struts2.action.ExportSqlDataThread;
import org.beangle.struts2.action.ExportThread;
import org.beangle.struts2.helper.QueryHelper;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.importer.StudentClassImportListener;
import com.yzsoft.yxxt.prepare.model.AdminClassRule;
import com.yzsoft.yxxt.prepare.model.AdminClassTemp;
import com.yzsoft.yxxt.prepare.model.StudentClass;
import com.yzsoft.yxxt.prepare.service.AdminClassPlanService;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentClassAllocAction extends SecurityActionSupport {

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
    protected DictDataService dictDataService;

    @Resource
    private YxxtService yxxtService;
    @Resource
    private AdminClassPlanService adminClassPlanService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private StudentService studentService;
    @Resource
    private MajorService majorService;
    @Resource
    protected RestrictionHelper restrictionHelper;
    @Resource
    protected UserService userService;

    @Override
    protected String getEntityName() {
        return AdminClass.class.getName();
    }

    @SuppressWarnings("unchecked")
    protected List<Department> getDeparts() {
        List<Department> departments = restrictionHelper.getDeparts();
        if (userService.isMember(getCurrentUser(), new GroupBean(3079L))) {
            departments = CollectUtils.newArrayList();
        }
        if (departments.isEmpty()) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            if (null != teacher && null != teacher.getDepartment()) {
                departments.add(teacher.getDepartment());
            }
        }
        return departments;
    }

    protected void putDatas() {
        put("grade", yxxtService.getGrade());
        put("departments", getDeparts());
//		put("majors",majorService.findAllMajor());
        put("enrollTypes", dictDataService.findDictData("STD_ENROLL_TYPE"));
    }

    @Override
    public String index() {
        putDatas();
        return super.index();
    }

    @Override
    public String search() {
        return super.search();
    }

    @Override
    protected QueryBuilder<?> getQueryBuilder() {
        Long enrollTypeId = getLong("enrollTypeId");
        OqlBuilder<?> builder = OqlBuilder.from(getEntityName(), getShortName());
        populateConditions(builder);
        if (getDeparts().size() == 1) {
            builder.where("adminClass.department in (:depts)", getDeparts());
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
    protected String getDefaultOrderString() {
        return "adminClass.code";
    }

    @Override
    protected void editSetting(Entity<?> entity) {
        putDatas();
    }

    @Override
    protected String saveAndForward(Entity<?> entity) {
        AdminClassTemp adminClassTemp = (AdminClassTemp) entity;

        //班级代码唯一
        if (entityDao.duplicate(AdminClassTemp.class, adminClassTemp.getId(), "code", adminClassTemp.getCode())) {
            put("adminClassTemp", adminClassTemp);
            putDatas();
            return forward("edit", "该班级代码" + adminClassTemp.getCode() + "已存在！");
        }
        entityDao.saveOrUpdate(adminClassTemp);
        return redirect("search", "info.save.success");
    }

    public String allocSave() throws Exception {
        try {
            //根据班级查询专业信息，通过专业来查找学生并进行分班
            //统招	  班级分配
            adminClassPlanService.alloc("STD_ENROLL_TYPE_01", getDeparts());
            //自主招生 班级分配
            adminClassPlanService.alloc("STD_ENROLL_TYPE_02", getDeparts());

            return redirect("search", "info.save.success");
        } catch (Exception e) {
            logger.info("saveAndForwad failure", e);
            return redirect("search", "info.save.failure");
        }
    }

    public String students() {
        AdminClass adminClass = entityDao.get(AdminClass.class, getEntityId(getShortName()));
        put("ac", adminClass);
        put("students", entityDao.get(Student.class, "adminClass", adminClass));
        return forward();
    }

    public String addStudentClass() {
        AdminClass adminClass = entityDao.get(AdminClass.class, getEntityId(getShortName()));

        OqlBuilder oql = OqlBuilder.from(Student.class);
        populateConditions(oql);
        oql.where("grade =:grade", adminClass.getGrade());
        oql.where("major =:major", adminClass.getMajor());
        oql.where("adminClass is null ");
        oql.limit(getPageLimit());
        put("students", entityDao.search(oql));
        put("ac", adminClass);

        return forward();
    }

    public String saveStudentClass() {

        AdminClass adminClass = entityDao.get(AdminClass.class, getEntityId(getShortName()));
        List<Student> students = entityDao.get(Student.class, getEntityIds("student"));
        for (Student student : students) {
            student.setAdminClass(adminClass);
        }
        entityDao.saveOrUpdate(students);

        return redirect("addStudentClass", "info.save.success", "adminClass.id=" + getEntityId(getShortName()));
    }

    public String removeStudentClass() {
        List<Student> students = entityDao.get(Student.class, getEntityIds("student"));

        for (Student student : students) {
            student.setAdminClass(null);
            student.setCode("");
        }
        entityDao.saveOrUpdate(students);
        return redirect("students", "info.save.success", "adminClass.id=" + getEntityId(getShortName()));
    }

    private Map<String, StudentClass> getStudentClassMap() {
        Map<String, StudentClass> map = CollectUtils.newHashMap();
        List<StudentClass> studentClasss = entityDao.getAll(StudentClass.class);
        for (StudentClass studentClass : studentClasss) {
            map.put(studentClass.getStudent().getId().toString(), studentClass);

        }
        return map;
    }

    public String createXh() {
        List<AdminClass> adminClasses = entityDao.get(AdminClass.class, getEntityIds("adminClass"));
        OqlBuilder oql = OqlBuilder.from(Student.class, "s");
        oql.where("s.grade =:grade", yxxtService.getGrade());
        oql.where("s.adminClass in (:adminClass)", adminClasses);
        List<Student> students = entityDao.search(oql);
        for (Student student : students) {
            String code = yxxtService.createXh(student);
            String sql2 = "select student_id from yxp_student_enrolls where code = " + code;
            List list = entityDao.searchSqlQuery(sql2);

            if (list != null && !list.isEmpty()) {
                if (student.getId() != list.get(0)) {
                    //String sql3 = "update yxp_student_enrolls set student_id = " + student.getId() + " where code =" +code;
                    continue;
                }
            }
            String sql = "update yxp_student_enrolls set code = " + code + "where student_id = " + student.getId();
            entityDao.executeUpdateSql(sql);
            System.out.println(1);
        }
        /*
            String sql = "update yxp_student_enrolls s set s.code = (select t.code from cp_students t where s.student_id = t.id)";
            entityDao.executeUpdateSql(sql);
        }*/
        return redirect("search", "生成学号成功！");
    }

    public String generateCode() {
        List<AdminClassRule> rules = adminClassPlanService.findRule();
        put("rules", rules);
        return forward();
    }

    public String generateCodeSave() {
        try {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            AdminClassRule rule = entityDao.get(AdminClassRule.class, getLong("rule.STUDENT_CODE"));
            adminClassPlanService.generateCode(teacher.getDepartment(), rule);
            return redirect("search", "info.save.success");
        } catch (MyException e) {
            generateCode();
            addActionError(e.getMessage());
            return forward("alloc");
        } catch (Exception e) {
            logger.info("saveAndForwad failure", e);
            return redirect("search", "info.save.failure");
        }
    }

    private List<AdminClassRule> getRules(String[] types) {
        List<Long> ruleIds = new ArrayList<Long>();
        for (String type : types) {
            ruleIds.add(getLong("rule." + type));
        }
        List<AdminClassRule> rules = entityDao.get(AdminClassRule.class, ruleIds);
        return rules;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        return new StudentClassImportListener();
    }

    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("班级代码", "code");
        map.put("班级名称", "name");
        map.put("身份证号", "student.cardcode");
        map.put("姓名", "student.name");
/*        map.put("专业代码", "major.code");
        map.put("专业名称", "major.name");
        map.put("招生类型", "enrollType.code");
        map.put("分数线", "score");
        map.put("班级人数", "num");

        map.put("培养类型代码", "duration");
        map.put("学生类别代码", "duration");
        map.put("年级", "grade");
        map.put("学制", "duration");
        map.put("院系部代码", "major.department.code");
        map.put("专业方向代码", "duration");
        map.put("计划人数", "num");
        map.put("班主任", "instructor.code");
        map.put("导师", "");
        map.put("专用教室", "duration");*/

        return map;
    }

    /**
     * 导出数据
     *
     * @return
     * @throws Exception
     */
    public String export() throws Exception {
        String format = get("format");
        String fileName = get("fileName");
        String template = get("template");

        // 配置导出上下文
        Context context = new Context();
        context.put("format", format);
        context.put("exportFile", fileName);
        context.put("fileName", fileName);
        context.put("template", template);
        String properties = get("propertiesExport");
        Map<String, String> propMap = getPropMap();
        if (null != properties) {
            String[] props = StringUtils.split(properties, ",");
            List<String> keys = CollectUtils.newArrayList();
            List<String> titles = CollectUtils.newArrayList();
            for (String prop : props) {
                if (prop.indexOf(":") > 0) {
                    keys.add(StringUtils.substringBefore(prop, ":"));
                    titles.add(getTextInternal(StringUtils.substringAfter(prop, ":")));
                } else {
                    String title = prop;
                    String key = propMap.get(title);
                    keys.add(key);
                    titles.add(getTextInternal(title));
                }
            }
            context.put(Context.KEYS, StrUtils.join(keys, ","));
            context.put(Context.TITLES, StrUtils.join(titles, ","));
        } else {
            context.put(Context.KEYS, get("keys"));
            context.put(Context.TITLES, get("titles"));
        }
        context.put(Context.EXTRACTOR, getPropertyExtractor());

        //显示的属性名
        context.put("attributeNames", "code,name,educationType.code,stdType.code,grade,duration,department.code,major.code,majorField.code,planStdCount,instructor.code,tutor.code,classroom.code");

        final Exporter exporter = buildExporter(context);

        configExporter(exporter, context);
        // 进行输出
        ExportThread thread = null;
        if (context.get(Context.KEYS) != null) {
            thread = new ExportObjectThread();
        } else {
            thread = new ExportSqlDataThread();
        }
        thread.setEntityDao(entityDao);
        thread.setQuery(getExportQueryBuilder());
        thread.setExporter(exporter);
        thread.setContext(context);
        thread.start();
        getSession().put("export_thread", thread);
        return forward("/template/export");
    }

    /**
     * 导出班级学生信息
     */
    public String exportStudentClass() throws Exception {
        String format = get("format");
        String fileName = get("fileName");
        String template = get("template");

        // 配置导出上下文
        Context context = new Context();
        context.put("format", format);
        context.put("exportFile", fileName);
        context.put("fileName", fileName);
        context.put("template", template);
        String properties = get("propertiesExport");
        Map<String, String> propMap = getPropMap();
        if (null != properties) {
            String[] props = StringUtils.split(properties, ",");
            List<String> keys = CollectUtils.newArrayList();
            List<String> titles = CollectUtils.newArrayList();
            for (String prop : props) {
                if (prop.indexOf(":") > 0) {
                    keys.add(StringUtils.substringBefore(prop, ":"));
                    titles.add(getTextInternal(StringUtils.substringAfter(prop, ":")));
                } else {
                    String title = prop;
                    String key = propMap.get(title);
                    keys.add(key);
                    titles.add(getTextInternal(title));
                }
            }
            context.put(Context.KEYS, StrUtils.join(keys, ","));
            context.put(Context.TITLES, StrUtils.join(titles, ","));
        } else {
            context.put(Context.KEYS, get("keys"));
            context.put(Context.TITLES, get("titles"));
        }
        context.put(Context.EXTRACTOR, getPropertyExtractor());

        //显示的属性名
        context.put("attributeNames", "class.code,student.code");

        final Exporter exporter = buildExporter(context);

        configExporter(exporter, context);
        // 进行输出
        ExportThread thread = null;
        if (context.get(Context.KEYS) != null) {
            thread = new ExportObjectThread();
        } else {
            thread = new ExportSqlDataThread();
        }
        thread.setEntityDao(entityDao);
        thread.setQuery(getStudentClassQueryBuilder());
        thread.setExporter(exporter);
        thread.setContext(context);
        thread.start();
        getSession().put("export_thread", thread);
        return forward("/template/export");
    }

    public OqlBuilder getStudentClassQueryBuilder() {
        OqlBuilder oql = OqlBuilder.from(StudentClass.class, "studentClass");
        oql.orderBy("adminClass.code asc,student.code asc");
        return oql;
    }
}
