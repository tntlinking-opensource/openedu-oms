package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import com.yzsoft.yxxt.web.collect.model.CollectSwitchState;
import com.yzsoft.yxxt.web.collect.service.CollectService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.model.UserBean;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.model.DictData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {

    @Resource
    private EntityDao entityDao;
    @Resource
    private StudentService studentService;
    @Resource
    private YxxtService yxxtService;

    @Override
    public List<CollectSwitch> findSwitch() {
        return findSwitch(null);
    }

    @Override
    public List<CollectSwitch> findSwitch(String username) {
        OqlBuilder query = OqlBuilder.from(CollectSwitch.class, "o");
        query.where("o.enabled = true");
        query.orderBy("o.sort");
        query.cacheable();
        List<CollectSwitch> list = entityDao.search(query);
        if (username != null) {
            Student student = yxxtService.getStudent();
            if (student.getEnrollType() != null) {
                for (Iterator<CollectSwitch> itor = list.iterator(); itor.hasNext(); ) {
                    CollectSwitch collectSwitch = itor.next();

                    if (!hasDictData(collectSwitch.getEnrollTypes(), student.getEnrollType())
                            || !hasDictData(collectSwitch.getEducations(), student.getEducation())) {
                        itor.remove();
                    }
                }
            }
//            query.join("left", "o.enrollTypes", "enrollType");
//            query.where("enrollType is null or enrollType = :enrollType", student.getEnrollType());
//            query.join("left", "o.educations", "education");
//            query.where("education is null or education = :education", student.getEducation());
        }
        return list;
    }

    private boolean hasDictData(List<DictData> enrollTypes, DictData enrollType) {
        if (enrollTypes == null || enrollTypes.isEmpty()) {
            return true;
        }
        return enrollTypes.contains(enrollType);
    }

    @Override
    public CollectSwitch getSwitch(String code) {
        OqlBuilder query = OqlBuilder.from(CollectSwitch.class);
        query.where("enabled = true");
        query.where("code = :code", code);
        query.cacheable();
        List<CollectSwitch> list = entityDao.search(query);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    public void finish(Long userId, String username, String code) {
        OqlBuilder query = OqlBuilder.from(CollectSwitchState.class);
        query.where("user.id = :userid", userId);
        query.where("collectSwitch.code = :code", code);
        List<CollectSwitchState> list = entityDao.search(query);
        if (list.isEmpty()) {
            CollectSwitchState state = new CollectSwitchState();
            state.setCollectSwitch(getSwitch(code));
            state.setStudent(studentService.getStudentByCode(username));
            state.setUser(new UserBean(userId));
            state.setFinished(true);
            state.setCreateTime(new Date());
            entityDao.saveOrUpdate(state);
        }
    }

    @Override
    public void cancle(Long userId, String code) {
        OqlBuilder query = OqlBuilder.from(CollectSwitchState.class);
        query.where("user.id = :userid", userId);
        query.where("collectSwitch.code = :code", code);
        List<CollectSwitchState> list = entityDao.search(query);
        entityDao.remove(list);
    }

    @Override
    public List<CollectSwitchState> findState(Long userId) {
        OqlBuilder query = OqlBuilder.from(CollectSwitchState.class);
        query.where("user.id = :userid", userId);
        List<CollectSwitchState> list = entityDao.search(query);
        return list;
    }

    @Override
    public CollectSwitchState getState(Long userId, String code) {
        OqlBuilder query = OqlBuilder.from(CollectSwitchState.class);
        query.where("user.id = :userid", userId);
        query.where("collectSwitch.code = :code", code);
        List<CollectSwitchState> list = entityDao.search(query);
        return list.isEmpty() ? null : list.get(0);
    }
}
