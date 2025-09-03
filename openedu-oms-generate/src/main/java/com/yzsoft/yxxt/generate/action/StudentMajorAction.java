package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.prepare.model.StudentMajor;
import com.yzsoft.yxxt.prepare.model.StudentMajorDetail;
import org.beangle.ems.security.User;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.MajorService;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.web.util.GeneratePrint;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentMajorAction extends SyncAction {

    @Resource
    private MajorService majorService;

    /**
     * http://127.0.0.1:9000/yxxt/generate/student-major.action
     *
     * @return
     * @throws Exception
     */
    public String index() {
        List<Major> majors = majorService.findAllMajor();
        List<Student> students = entityDao.getAll(Student.class);
        Random random = new Random();
        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        for (Student student : students) {
            if (random.nextInt(100) < 20) {
                continue;
            }
            StudentMajor studentMajor = entityDao.getEntity(StudentMajor.class, "student.id", student.getId());
            if (studentMajor == null) {
                studentMajor = new StudentMajor();
//                studentMajor.setUser(entityDao.getEntity(User.class, "name", student.getCode()));
                studentMajor.setStudent(student);
            }
            for (int i = 1; i <= 3; i++) {
                StudentMajorDetail detail = new StudentMajorDetail();
                detail.setStudentMajor(studentMajor);
                detail.setSort(i);
                detail.setMajor(majors.get(random.nextInt(majors.size())));
                studentMajor.getDetails().add(detail);
            }
            entityDao.saveOrUpdate(studentMajor);
            print.write(student.getName());
        }
        print.end();
        return null;
    }
}
