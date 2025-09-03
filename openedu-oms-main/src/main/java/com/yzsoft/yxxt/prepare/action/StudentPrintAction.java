package com.yzsoft.yxxt.prepare.action;

import java.io.IOException;
import java.util.*;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.Order;
import org.beangle.ems.security.model.GroupBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.print.model.PrintTemplate;
import org.beangle.print.service.PrintTemplateService;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.action.BaseCommonAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.StudentPrint;
import com.yzsoft.yxxt.prepare.model.StudentPrintView;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentPrintAction extends BaseCommonAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private PrintTemplateService printTemplateService;
    @Resource
    protected StudentService studentService;
    @Resource
    private RestrictionHelper restrictionHelper;
    @Resource
    protected UserService userService;
    @Resource
    protected TeacherService teacherService;
    @Resource
    private MajorService majorService;

    @Override
    protected String getEntityName() {
        return StudentPrintView.class.getName();
    }

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("grade", yxxtService.getGrade());
        put("departs", getDeparts());
        put("majors", majorService.findAllMajor());
        put("enrollTypes", studentService.findEnrollType());
        put("provinces", findDictData("PROVINCE"));
        put("trainTypes", findDictData("TRAIN_TYPE"));
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
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        Integer pageSize = getInteger("pageSize");
        if (pageSize == null) {
            query.getLimit().setPageSize(50);
        }
    }

    @Override
    protected String getDefaultOrderString() {
        return "studentPrintView.student.letterCode";
    }

    public String printNotice() throws IOException {
        print("DYMBLX_YBD_01_TZS");
        put("title", "通知书打印");
        return forward("/template/print/printTemplate");
    }

    public String printFace() throws IOException {
        print("DYMBLX_YBD_02_FM");
        put("title", "封面打印");
        return forward("/template/print/printTemplate");
    }

    private void print(String code) throws IOException {
        //查询通知书编号
        String year = yxxtService.getGrade();
        int i = 0;
        String sql = "select max(substr(notice,-4)) from cp_students where grade = " + year;
        List list = entityDao.searchSqlQuery(sql);
        if (!CollectionUtils.isEmpty(list)) {
            i = NumberUtils.toInt((String) list.get(0));
        }

        PrintTemplate printTemplate = printTemplateService.getPrintTemplateByCode(code);
        put("printTemplate", printTemplate);
        String basePath = getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + getRequest().getContextPath() + "/";
        //学生集合
        Long[] stdIds = getEntityIds("studentPrintView", Long.class);
        List<Student> stdList = CollectUtils.newArrayList();
//        List<Student> stdList = entityDao.get(Student.class, stdIds);
//        Collections.sort(stdList, new BeanComparator("id"));
//        for (Student student : stdList) {
        for (int j = 0; j < stdIds.length; j++) {
            Student student = entityDao.get(Student.class, stdIds[j]);
            System.out.println(student.getName());
            //设置学生的通知书编号
            if (null!=student.getEducation() && "XLCC_01".equals(student.getEducation().getCode())
                    && StringUtils.isEmpty(student.getNotice())) {
                i++;
                student.setNotice((student.getTrainType() == null ? "" : student
                        .getTrainType().getEnName())
                        + year
                        + StringUtils.repeat("0", 4 - String.valueOf(i)
                        .length()) + i);
            }
            StudentPrint print = entityDao.getEntity(StudentPrint.class, "student.id", student.getId());
            if (print == null) {
                print = new StudentPrint();
                print.setStudent(student);
            }
            if ("DYMBLX_YBD_01_TZS".equals(code)) {
                print.setPrintNotice(true);
            } else {
                print.setPrintFace(true);
            }
            entityDao.saveOrUpdate(print);
            stdList.add(student);
        }
        entityDao.saveOrUpdate(stdList);
        //向Freemarker模版传递参数
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("students", stdList);
        dataMap.put("base", basePath);
        dataMap.put("pageNo", 1);
        put("templateContent", printTemplateService.getTemplateContent(printTemplate, dataMap, true, false));
        put("templateStyles", printTemplate.getTemplateStyles());
    }

}
