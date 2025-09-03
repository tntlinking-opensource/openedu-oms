package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.importer.StudentRegisterImportListener;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 学生注册
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentRegisterAction extends StudentActionParent {

    @Override
    protected void querySetting(OqlBuilder query) {
        super.querySetting(query);
        query.where("studentEnroll.enrolled = true");
    }

    public String registed() {
        Long[] studentIds = getEntityIds(getShortName());
        Boolean registed = getBoolean("registed");
        List<StudentEnroll> studentEnrolls = entityDao.get(StudentEnroll.class, studentIds);
        List<Student> students = new ArrayList<Student>();
        for (StudentEnroll studentEnroll : studentEnrolls) {
            Student student = studentEnroll.getStudent();
            student.setRegisted(registed);
            students.add(student);
        }
        entityDao.saveOrUpdate(students);
        return redirect("search", "info.save.success");
    }

    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = super.getPropMap();
        map.put("是否注册", "registedStr");
        return map;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        return new StudentRegisterImportListener();
    }

}
