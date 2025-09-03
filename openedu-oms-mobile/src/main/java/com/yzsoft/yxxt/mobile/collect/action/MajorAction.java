package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.model.StudentMajorDetail;
import com.yzsoft.yxxt.web.collect.action.StudentMajorInfoAction;
import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MajorAction extends StudentMajorInfoAction {

    public String majorList() {
        Student student = getStudent();
        put("majorInfos", majorInfoService.find(student));
        put("switch", getSwitch());
        return forward();
    }

    public String majorInfo() {
        Long majorInfoId = getLong("majorInfoId");
        Student student = getStudent();
        List<MajorInfo> majorInfos = majorInfoService.find(student);
        MajorInfo majorInfo = null;
        MajorInfo nextMajorInfo = null;
        for (int i = 0; i < majorInfos.size(); i++) {
            MajorInfo mi = majorInfos.get(i);
            if (mi.getId().equals(majorInfoId)) {
                majorInfo = mi;
                if (i + 1 < majorInfos.size()) {
                    nextMajorInfo = majorInfos.get(i + 1);
                }
                break;
            }
        }
        StudentMajor studentMajor = studentMajorService.get(getUserId());
        put("nextMajorInfo", nextMajorInfo);
        put("majorInfo", majorInfo);
        put("studentMajor", studentMajor);
        put("switch", getSwitch());
        put("studentMajorNum", majorPlanService.getNum());
        put("emptyDetail", new StudentMajorDetail());
        return forward();
    }

    public String selectMajor() {
        Long majorInfoId = getLong("majorInfoId");
        Integer sort = getInteger("sort");
        MajorInfo majorInfo = entityDao.get(MajorInfo.class, majorInfoId);
        Student student = entityDao.getEntity(Student.class, "code", getUsername());
        StudentMajor studentMajor = studentMajorService.get(getUserId());

        //删除重复专业
        for (StudentMajorDetail detail : studentMajor.getDetails()) {
            if (detail.getMajor() != null && detail.getMajor().equals(majorInfo.getMajor())) {
                detail.setMajor(null);
            }
        }
        if (studentMajor.getStudent() == null) {
            studentMajor.setStudent(student);
//            studentMajor.setUser(getCurrentUser());
        }
        StudentMajorDetail detail = studentMajor.getDetail(sort);
        detail.setMajor(majorInfo.getMajor());
        entityDao.saveOrUpdate(studentMajor);
        return redirect("majorInfo", null, "majorInfoId=" + majorInfoId);
    }

}
