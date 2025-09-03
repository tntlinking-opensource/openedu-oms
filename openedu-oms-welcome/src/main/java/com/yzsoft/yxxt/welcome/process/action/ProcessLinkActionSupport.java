package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.welcome.model.WelcomeStudent;
import com.yzsoft.yxxt.welcome.service.WelcomeService;
import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.service.UserService;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.*;
import org.beangle.process.service.ProcessLinkLogService;
import org.beangle.process.service.ProcessService;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.struts2.convention.route.Action;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ProcessLinkActionSupport extends SecurityActionSupport {

    @Resource
    protected StudentService studentService;
    @Resource
    protected ProcessService processService;
    @Resource
    protected ProcessLinkLogService processLinkLogService;
    @Resource
    protected UserService userService;
    @Resource
    protected TeacherService teacherService;
    @Resource
    protected WelcomeService welcomeService;

    @Override
    protected String getEntityName() {
        return Student.class.getName();
    }

    @Override
    protected void indexSetting() {
        ProcessBatch batch = entityDao.get(ProcessBatch.class, getLong("batchId"));
        Long itemId = getLong("itemId");
        ProcessLinkItem item = entityDao.get(ProcessLinkItem.class, itemId);
        List<String> ifbds = new ArrayList<String>();
        ifbds.add("是");
        ifbds.add("否");
        put("ifbds", ifbds);
        put("batch", batch);
        put("batchId", get("batchId"));
        put("itemId", itemId);
        put("item", item);
        super.indexSetting();
    }

    @Override
    public String search() {
        put("batchId", get("batchId"));
        put("itemId", get("itemId"));
        return super.search();
    }

    @Override
    protected OqlBuilder<?> getOqlBuilder() {
        OqlBuilder query = super.getOqlBuilder();
        //过滤批次学生
        ProcessBatch batch = entityDao.get(ProcessBatch.class, getLong("batchId"));
        query.where("student.grade = :grade", batch.getYear());
        query.where("student.education in (:educations)", batch.getEducations());
        //过滤是否办理
        if (StringUtils.isNotEmpty(get("sfbd"))) {
            if (getBool("sfbd")) {
                query.where("exists( from " + ProcessLinkItemLog.class.getName()
                        + " pl where pl.batch.id = " + getLong("batchId") + " and pl.item.id = " + getLong("itemId")
                        + " and pl.student.id = student.id)");
            } else {
                query.where("not exists( from " + ProcessLinkItemLog.class.getName()
                        + " pl where pl.batch.id = " + getLong("batchId") + " and pl.item.id = " + getLong("itemId")
                        + " and pl.student.id = student.id)");
            }
        }

        //过滤学生数据
        Long itemId = getLong("itemId");
        ProcessLinkItem item = entityDao.get(ProcessLinkItem.class, itemId);
        AuditGroup auditGroup = item.getAuditGroup();
        if (auditGroup != null && !"普通".equals(auditGroup.getGroupType().getName())) {
            Teacher teacher = teacherService.getTeacherByCode(getUsername());
            if (teacher == null || teacher.getDepartment() == null) {
                query.where("1 = 2");
            } else if ("院系".equals(auditGroup.getGroupType().getName())) {
                query.where("student.department = :department", teacher.getDepartment());
            } else if ("班级".equals(auditGroup.getGroupType().getName())) {
                query.join("student.adminClass.instructors", "instructor");
                query.where("instructor = :instructor", teacher);
            } else {
                query.where("1 = 2");
            }
        }
//		query.where("student.inSchooled = true");
        query.where("student.registed = true");
        return query;
    }

    @Override
    public String edit() {
        String code = get("code");
        String save = get("save");
//		Student student = studentService.getStudentByCode(code);
        Student student = entityDao.getEntity(Student.class, "cardcode", code);
        if (student == null) {
            return redirect(new Action(ErrorStudentNotFountdAction.class, null, "code=" + code));
        }
        StudentEnroll enroll = entityDao.getEntity(StudentEnroll.class, "student", student);
        //检验权限 当前用户是否可以处理该学生
        if (!hasStudent(student)) {
            return redirect(new Action(ErrorStudentCanNotDealAction.class, null, "id=" + student.getId()));
        }
        Long batchId = getLong("batchId");
        editSetting(student);
        editSetting(batchId, student);
        Long itemId = getLong("itemId");
        Long studentId = student.getId();
        ProcessLinkItem processLinkItem = entityDao.get(ProcessLinkItem.class, itemId);
        ProcessLinkItemLog processLinkItemLog = processLinkLogService.getLog(batchId, itemId, studentId);
        List<ProcessLinkItemForm> itemForms = processService.findItemForm(itemId);
        if (!itemForms.isEmpty()) {
            List<ProcessLinkItemFormValue> itemFormValues = processLinkLogService.findItemFormValue(itemId, studentId);
            put("itemFormValues", itemFormValues);
        }
        if (processLinkItem.isPrinted()) {
            List<ProcessLinkItemPrint> prints = processService.findPrint(processLinkItem);
            put("prints", prints);
        }
        put("batchId", batchId);
        put("itemForms", itemForms);
        put("itemId", itemId);
        put("studentId", studentId);
        put("enroll", enroll);
        put("processLinkItemLog", processLinkItemLog);
        put("processLinkItem", processLinkItem);
        put("handleable", processLinkLogService.handleable(batchId, itemId, studentId));
        put("save", save);
        SuppliesStd suppliesStd = entityDao.getEntity(SuppliesStd.class, "student", student);
        put("supply", suppliesStd);
        return forward();
    }

    private boolean hasStudent(Student student) {
        OqlBuilder query = getOqlBuilder();
        query.select("student.id");
        query.where("student = :student", student);
        List list = entityDao.search(query);
        return list.size() == 1;
    }

    protected void editSetting(Student student) {
        super.editSetting(student);
    }

    protected void editSetting(Long batchId, Student student) {
        editSetting(student);
    }

    @Override
    public String save() throws Exception {
        try {
            boolean pass = getBool("pass");
            boolean save = getBool("save");
            Long batchId = getLong("batchId");
            Long itemId = getLong("itemId");
            Long studentId = getLong("studentId");

            //判断是否有权限操作
            User user = getCurrentUser();
            List<Group> userGroups = userService.getGroups(user, GroupMember.Ship.MEMBER);
            ProcessLinkItem item = entityDao.get(ProcessLinkItem.class, itemId);
            Assert.notNull(item);
            Assert.notNull(item.getAuditGroup());
            Assert.notNull(item.getAuditGroup().getGroup());
            Assert.isTrue(userGroups.contains(item.getAuditGroup().getGroup()));

            Student student = entityDao.get(Student.class, studentId);
            if (StringUtils.isEmpty(student.getCode())) {
                Assert.notNull(student.getGrade(), "年级为空");
                Assert.notNull(student.getDepartment(), "院系为空");
                Assert.notNull(student.getMajor(), "专业为空");
                Assert.notNull(student.getAdminClass(), "班级为空");
            }
            //业务项
            processLinkLogService.removeFormValue(itemId, studentId);
            Long[] itemFormIds = getAll("itemForm", Long.class);
            if (itemFormIds != null) {
                List<ProcessLinkItemFormValue> values = new ArrayList<ProcessLinkItemFormValue>();
                for (Long itemFormId : itemFormIds) {
                    String val = get("itemForm" + itemFormId);
                    if (StringUtils.isNotEmpty(val)) {
                        val = val.replaceAll(",", "、");
                        ProcessLinkItemFormValue value = new ProcessLinkItemFormValue();
                        value.setForm(new ProcessLinkItemForm());
                        value.getForm().setId(itemFormId);
                        value.setStudent(student);
                        value.setValue(val);
                        values.add(value);
                    }
                }
                entityDao.saveOrUpdate(values);
            }

            if (save) {
                save(batchId, student);
            } else {
                if (pass) {
                    pass(batchId, student);
                    //确认办理
                    processLinkLogService.saveLog(batchId, itemId, studentId);
                    welcomeService.register(studentId, true);
                    welcomeService.processLinkPassed(batchId, itemId, studentId);
                } else {
                    cancel(batchId, student);
                    //撤销办理
                    welcomeService.register(studentId, false);
                    processLinkLogService.cancleLog(batchId, itemId, studentId);
                }
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            addFlashError(e.getMessage());
        }
        return redirect("edit", null, "save=1");
    }

    protected void save(Long batchId, Student student) {
    }

    protected void pass(Long batchId, Student student) {
        save(batchId, student);
    }

    protected void cancel(Long batchId, Student student) {
    }

    @Override
    public String remove() throws Exception {
        return null;
    }

    /**
     * 配置
     *
     * @return
     */
    public String setting() {
        return forward();
    }

    /**
     * 配置编辑
     *
     * @return
     */
    public String settingEdit() {
        return forward();
    }

    /**
     * 配置保存
     *
     * @return
     */
    public String settingSave() {
        return forward();
    }
}
