package com.yzsoft.yxxt.web.action;

import com.octo.captcha.service.CaptchaService;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.manager.service.ViewSetService;
import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.service.ColumnService;
import com.yzsoft.yxxt.web.service.ContentService;
import com.yzsoft.yxxt.web.service.IndexService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.emsapp.portal.service.LoginCache;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.security.auth.LoginService;
import org.beangle.security.web.auth.preauth.CookieUsernameSource;
import org.beangle.struts2.action.BaseAction;
import org.beangle.web.util.CookieUtils;
import org.springframework.util.Assert;

import javax.annotation.Resource;

public class IndexParentAction extends BaseAction {

    @Resource
    private IndexService indexService;
    @Resource
    private CaptchaService captchaService;
    @Resource
    private LoginService loginService;
    @Resource
    private StudentService studentService;
    @Resource
    private TeacherService teacherService;
    @Resource
    private ColumnService columnService;
    @Resource
    private ContentService contentService;
    @Resource
    private LoginCache loginCache;
    @Resource
    private ViewSetService viewSetService;
    @Resource
    protected YxxtService yxxtService;

    public String index() {
        put("imgLinks", indexService.findImgLink());
        put("iconLinks", indexService.findIconLink());
        put("welcomeLinks", indexService.findWelcomeLink());
        put("footLinkGroups", indexService.findFootLinkGroup());
        put("footLinks", indexService.findFootLink());
        //报到须知1
        Column column = columnService.getColumn("0201");
        if (column != null) {
            put("column", column);
            put("contents", contentService.findContent(column.getId(), new PageLimit(1, 8)));
        }
        Long userid = SecurityUtils.getUserId();
        if (userid != null) {
            User user = entityDao.get(User.class, userid);
//            Student student = studentService.getStudentByCode(user.getName());
            Student student = yxxtService.getStudent();
            Teacher teacher = null;
            if (student == null) {
                teacher = teacherService.getTeacherByCode(user.getName());
                put("teacher", teacher);
            }
            put("user", user);
            put("student", student);
            put("teacher", teacher);
        }
        put("viewSetIndex", viewSetService.getViewSetIndex());
        put("systemConfig", getConfigFactory().getConfig());
        return forward();
    }

    public String login() {
        String username = get("username");
        try {
            if (needCaptcha(username)) {
                String captcha = get("captcha");
                Assert.isTrue(StringUtils.isNotBlank(captcha), "验证码不能为空");
                String sessionId = getRequest().getSession().getId();
                try {
                    Boolean captchaValid = captchaService.validateResponseForID(sessionId, captcha);
                    Assert.isTrue(captchaValid, "验证码错误");
                } catch (Exception e) {
                    throw new IllegalArgumentException("验证码错误");
                }
            }
            String password = get("password");
            String errormsg = loginService.login(username, password);
            clearLoginFailure(username);
            errormsg = getText(errormsg);
            Assert.isNull(errormsg, errormsg);
            int time = 2 * 7 * 24 * 3600;
            CookieUtils.addCookie(getRequest(), getResponse(), CookieUsernameSource.KEY_USER_NAME, username, time);
            CookieUtils.addCookie(getRequest(), getResponse(), CookieUsernameSource.KEY_UCHECK, CookieUsernameSource.getUcheck(getRequest(), username), time);
        } catch (Exception e) {
//            put("errormsg", e.getMessage());
            put("errormsg", getText(e.getMessage()));
            put("username", username);
            increaseLoginFailure(username);
            needCaptcha(username);
            index();
            return forward("index");
        }
        return redirect("index");
    }

    private boolean needCaptcha(String username) {
        if (username == null) {
            return false;
        }
        Integer count = loginCache.getNum(username);
        if (null == count) {
            return false;
        }
        if (count < 3) {
            return false;
        } else {
            put("needCaptcha", true);
            return true;
        }
    }

    private void increaseLoginFailure(String username) {
        loginCache.error(username);
    }

    private void clearLoginFailure(String username) {
        loginCache.clear(username);
    }

}
