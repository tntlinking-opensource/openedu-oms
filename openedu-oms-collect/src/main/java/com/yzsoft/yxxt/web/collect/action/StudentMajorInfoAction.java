package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.model.StudentMajorDetail;
import com.yzsoft.yxxt.prepare.service.BatchService;
import com.yzsoft.yxxt.prepare.service.MajorPlanService;
import com.yzsoft.yxxt.prepare.service.PrepareService;
import com.yzsoft.yxxt.prepare.service.StudentMajorService;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import com.yzsoft.yxxt.web.collect.service.MajorInfoService;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentMajorInfoAction extends CollectAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    protected StudentMajorService studentMajorService;
    @Resource
    protected MajorInfoService majorInfoService;
    @Resource
    protected MajorPlanService majorPlanService;
    @Resource
    protected BatchService batchService;
    @Resource
    protected PrepareService prepareService;

    @Override
    public String getCode() {
        return "06";
    }

    @Override
    public String index() {
        StudentMajor studentMajor = studentMajorService.get(getUserId());
        if (studentMajor == null || studentMajor.getDetails().isEmpty()) {
            if (getSwitch().isEditable()) {
                return redirect("edit");
            }
        }
        put("studentMajor", studentMajor);
        put("config", majorPlanService.getConfig());
        return super.index();
    }

    @Override
    public String edit() {
        StudentMajor studentMajor = studentMajorService.get(getUserId());
        if (studentMajor.getAlterable() == null) {
            studentMajor.setAlterable(true);
        }
        Student student = getStudent();
        put("studentMajor", studentMajor);
        put("majorInfos", majorInfoService.find(student));
        put("config", majorPlanService.getConfig());
        return super.edit();
    }

    @Override
    public String save() {
        StudentMajor studentMajor = studentMajorService.get(getUserId());
        List<StudentMajorDetail> details = populateList(StudentMajorDetail.class, "detail");
        for (StudentMajorDetail detail : details) {
            detail.setStudentMajor(studentMajor);
        }
        if (details.size() > 0) {
            beforeSave(studentMajor);
            if (studentMajor.getStudent() == null) {
                Student student = entityDao.getEntity(Student.class, "user.id", getUserId());
                studentMajor.setStudent(student);
            }
            if (studentMajor.getEnroll() == null) {
                studentMajor.setEnroll(prepareService.getStudentEnroll(studentMajor.getStudent()));
            }
//            if (studentMajor.getGrade() == null) {
//                studentMajor.setGrade(yxxtService.getGrade());
//            }
            studentMajor.getDetails().clear();
            studentMajor.getDetails().addAll(details);
            studentMajor.setAlterable(getBool("studentMajor.alterable"));
            studentMajor.setBatch(batchService.getLatestBatch());
            entityDao.saveOrUpdate(studentMajor);
            afterSave();
        }
        return redirect("index");
    }

    @Override
    public String info() {
        Long id = getLong("id");
        MajorInfo majorInfo = entityDao.getEntity(MajorInfo.class, "major.id", id);
        put("majorInfo", majorInfo);
        return forward();
    }
}
