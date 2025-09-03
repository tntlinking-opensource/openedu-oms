package com.yzsoft.yxxt.web.collect;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.action.CollectErrorAction;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import com.yzsoft.yxxt.web.collect.service.MobileCollectAction;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.model.UserBean;
import org.beangle.ems.security.model.UserObject;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.pojo.LongIdEntity;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentEntity;
import org.beangle.product.core.model.StudentObject;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.convention.route.Action;
import org.beangle.web.util.CheckMobile;
import org.beangle.web.util.RedirectUtils;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

public abstract class CollectAction extends SecurityActionSupport {

    @Resource
    protected CollectService collectService;
    @Resource
    protected StudentService studentService;
    @Resource
    protected YxxtService yxxtService;

    abstract public String getCode();

    public String index() {
        try {
            put("switch", getSwitch());
            indexSetting();
            return forward();
        } catch (Exception e) {
            e.printStackTrace();
            return redirect(new Action(CollectErrorAction.class));
        }
    }

    protected CollectSwitch getSwitch() {
        CollectSwitch collectSwitch= collectService.getSwitch(getCode());
        Assert.notNull(collectSwitch, "collectSwitch is null for " + getCode());
        return collectSwitch;
    }

    public String edit() {
        checkSwitch();
        return forward();
    }

    public String save() {
        return forward();
    }

    /**
     * 将当前开关标记为完成
     */
    protected void afterSave() {
        if (finished()) {
            collectService.finish(getUserId(), getUsername(), getCode());
        }
    }

    /**
     * 是否完成采集
     *
     * @return
     */
    protected boolean finished() {
        return true;
    }

    protected void beforeSave(LongIdObject object) {
        if (object == null) {
            return;
        }
        checkSwitch();
        if (object instanceof UserObject) {
            UserObject userObject = (UserObject) object;
            if (userObject.getUser() == null) {
                userObject.setUser(new UserBean(getUserId()));
            }
            Assert.isTrue(getUserId().equals(userObject.getUser().getId()));
        }
        if (object instanceof StudentObject) {
            StudentObject studentObject = (StudentObject) object;
            if (studentObject.getStudent() == null) {
                Student student = studentService.getStudentByCode(getUsername());
                studentObject.setStudent(student);
            }
        }
    }

    protected void checkSwitch() {
        CollectSwitch collectSwitch = getSwitch();
        Assert.notNull(collectSwitch);
        Assert.isTrue(collectSwitch.isEditable());
        put("switch", collectSwitch);
        put("collectSwitch", collectSwitch);
    }


    protected void checkStudent(StudentEntity obj, Student std) {
        if (obj.getStudent() == null) {
            obj.setStudent(std);
        }
//        if (obj.getUser() == null) {
//            obj.setUser(std.getUser());
//        }
        Assert.isTrue(std.equals(obj.getStudent()));
    }

    @Override
    public String remove() throws Exception {
        return null;
    }

    protected Student getStudent() {
        Student student = yxxtService.getStudent();
        return student;
    }

    protected String nextAction() {
        try {
            CollectSwitch collectSwitch = getNextCollectSwitch();
            if (collectSwitch != null) {
                if (CheckMobile.check(getRequest())) {
                    String code = collectSwitch.getCode();
                    Class action = MobileCollectAction.getAction(code);
                    return redirect(new Action(action));
                } else {
                    String url = collectSwitch.getUrl() + ".action";
                    RedirectUtils.sendRedirect(getRequest(), getResponse(), url);
                    return null;
                }
            }
        } catch (IOException e) {
        }
        return redirect("index");
    }

    public String next() {
        return nextAction();
    }

    protected CollectSwitch getNextCollectSwitch() {
        List<CollectSwitch> collectSwitches = collectService.findSwitch(SecurityUtils.getUsername());
        CollectSwitch curr = null;
        for (CollectSwitch collectSwitch : collectSwitches) {
            if (curr != null) {
                return collectSwitch;
            }
            if (getCode().equals(collectSwitch.getCode())) {
                curr = collectSwitch;
            }
        }
        return null;
    }
}
