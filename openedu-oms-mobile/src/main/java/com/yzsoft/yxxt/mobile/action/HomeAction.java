package com.yzsoft.yxxt.mobile.action;

import com.yzsoft.dorm.model.DormPlanBed;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.mobile.model.HomeHelp;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.model.UserBean;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class HomeAction extends BaseAction {

    @Resource
    private ColumnService columnService;
    @Resource
    private ContentService contentService;
    @Resource
    private YxxtService yxxtService;

    public String index() {
        try {
            HomeHelp homeHelp = entityDao.getEntity(HomeHelp.class, "user.id", SecurityUtils.getUserId());
            if (homeHelp == null) {
                this.homeHelp();
            }
            Student student = yxxtService.getStudent();
            String photo = student.getPhoto();
            if (StringUtils.isBlank(photo)) {
                StudentEnroll enroll = entityDao.getEntity(StudentEnroll.class, "student", student);
                photo = enroll.getPhoto();
            }
            DormPlanBed dormPlanBed = entityDao.getEntity(DormPlanBed.class, "student.id", student.getId());
            put("dormPlanBed", dormPlanBed);
            put("student", student);
            put("photo", photo);
            put("homeHelp", homeHelp);
            put("isBirthday", getIsBirthday(student));
//			put("student", new Student());

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        Column noticeColumn = columnService.getColumn("0101");
        //报到须知1
        Column column = columnService.getColumn("0201");
        if (column != null) {
            put("column", column);
            put("contents", contentService.findContent(column.getId(), new PageLimit(1, 8)));
        }
        put("noticeColumn", noticeColumn);
        return forward();
    }

    private Boolean getIsBirthday(Student student) {
        if (student == null || student.getCardcode() == null) {
            return false;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
        String now = sdf.format(new Date());
        return now.equals(student.getCardcode().substring(10, 14));
    }


    public void homeHelp() {
        HomeHelp homeHelp = entityDao.getEntity(HomeHelp.class, "user.id", SecurityUtils.getUserId());
        if (homeHelp == null) {
            homeHelp = new HomeHelp();
            homeHelp.setUser(new UserBean(SecurityUtils.getUserId()));
            homeHelp.setCreateTime(new Date());
            entityDao.saveOrUpdate(homeHelp);
        }
    }

    public void birthdayClicked() {
        HomeHelp homeHelp = entityDao.getEntity(HomeHelp.class, "user.id", SecurityUtils.getUserId());
        if (homeHelp == null) {
            homeHelp = new HomeHelp();
        }
        homeHelp.setBirthdayClickTime(new Date());
        entityDao.saveOrUpdate(homeHelp);
    }

}
