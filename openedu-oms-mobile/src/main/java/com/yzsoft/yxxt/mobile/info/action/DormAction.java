package com.yzsoft.yxxt.mobile.info.action;

import com.yzsoft.dorm.model.DormBed;
import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.dorm.web.collect.service.DormChooseService;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.TeachCalendar;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeachCalendarService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static org.beangle.ems.security.SecurityUtils.getUsername;

@Controller
public class DormAction extends BaseAction {

    @Resource
    protected DormChooseService dormChooseService;
    @Resource
    protected TeachCalendarService teachCalendarService;
    @Resource
    protected StudentService studentService;

    public String index() {
        TeachCalendar curTeachCalendar = teachCalendarService.getCurrentTeachCalendar();
        Student std = studentService.getStudentByCode(getUsername());
        Assert.notNull(std, "对不起，你不是学生无法访问！");
//        DormPlanBed planBed = dormChooseService.findPlanBedByPlanBed(std, curTeachCalendar.getYear());
//        put("planBed", planBed);
        DormBed dormBed = dormChooseService.findDormBedByStd(std);
        put("dormBed", dormBed);
        return forward();
    }

}
