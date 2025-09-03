package com.yzsoft.yxxt.web.collect.service.impl;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import com.yzsoft.yxxt.web.collect.model.StudentInfoCollectState;
import com.yzsoft.yxxt.web.collect.service.StudentInfoCollectService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentFamily;
import org.beangle.product.student.property.model.StdPropertyConfig;
import org.beangle.product.student.property.model.StdPropertyType;
import org.beangle.product.student.property.service.StdPropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;

@Service
public class StudentInfoCollectServiceImpl extends EntityDaoSupport implements StudentInfoCollectService {

    @Resource
    private StdPropertyService stdPropertyService;
    @Autowired
    protected YxxtConfig yxxtConfig;

    @Override
    public boolean finished(Student student) {
        Long educationId = student.getEducation().getId();
        List<StdPropertyType> types = stdPropertyService.findType(educationId);
        List<StdPropertyConfig> configs = stdPropertyService.findConfig(educationId);
        List<StudentInfoCollectState> states = findState(student);
        for (StdPropertyType type : types) {
            String code = "TYPE_" + type.getId();
            if (hasEditConfig(type, configs) && !hasCode(states, code)) {
                return false;
            }
        }
        if (!hasCode(states, CODE_FAMILY)) {
            return false;
        }
        if (yxxtConfig.isStudentInfoEducationEnabled() && !hasCode(states, CODE_FAMILY)) {
            return false;
        }
        return true;
    }

    private boolean hasEditConfig(StdPropertyType type, List<StdPropertyConfig> configs) {
        for (StdPropertyConfig config : configs) {
            if (type.equals(config.getType()) && config.isEnabled() && config.isEditable()) {
                return true;
            }
        }
        return false;
    }

    private boolean hasCode(List<StudentInfoCollectState> states, String code) {
        for (StudentInfoCollectState state : states) {
            if (state.getCode().equals(code)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void saveState(Student student, String code) {
        StudentInfoCollectState state = getState(student, code);
        if (state == null) {
            state = new StudentInfoCollectState();
            state.setStudent(student);
            state.setCode(code);
            entityDao.saveOrUpdate(state);
        }
    }

    @Override
    public void initFamily(StudentFamily family) {
        if (family.getAge() != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) - family.getAge());
            family.setBirthday(calendar.getTime());
        } else {
            family.setBirthday(null);
        }
    }

    private StudentInfoCollectState getState(Student student, String code) {
        OqlBuilder query = OqlBuilder.from(StudentInfoCollectState.class);
        query.where("student = :student", student);
        query.where("code = :code", code);
        List<StudentInfoCollectState> list = entityDao.search(query);
        if (list.size() > 1) {
            StudentInfoCollectState state = list.remove(0);
            entityDao.remove(list);
            return state;
        }
        return list.isEmpty() ? null : list.get(0);
    }

    public List<StudentInfoCollectState> findState(Student student) {
        OqlBuilder query = OqlBuilder.from(StudentInfoCollectState.class);
        query.where("student = :student", student);
        return entityDao.search(query);
    }
}
