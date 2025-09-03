package com.yzsoft.yxxt.prepare.action;

import com.yzsoft.yxxt.prepare.importer.StudentEnrollImportListener;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.prepare.service.PrepareService;

import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.core.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentEnrollAction extends StudentActionParent {

    @Autowired
    private PrepareService prepareService;
    @Autowired
	protected BspBaseService bspBaseService;

    @Override
    protected void indexSetting() {
        super.indexSetting();
        put("enrollTypes", studentService.findEnrollType());
        put("educations", dictDataService.findDictData(StudentService.XLCC));
    }

    @Override
    protected Map<String, String> getPropMap() {
        Map<String, String> map = super.getPropMap();
        map.put("是否通知", "noticedStr");
        map.put("是否录取", "enrolledStr");
        return map;
    }

    public String notice() {
        Long[] studentIds = getEntityIds(getShortName());
        Boolean noticed = getBoolean("noticed");
        List<StudentEnroll> students = entityDao.get(StudentEnroll.class, studentIds);
        for (StudentEnroll student : students) {
            student.setNoticed(noticed);
        }
        entityDao.saveOrUpdate(students);
        return redirect("search", "info.save.success");
    }

    public String enroll() {
        Long[] studentIds = getEntityIds(getShortName());
        boolean enrolled = getBool("enrolled");
        List<StudentEnroll> enrolls = entityDao.get(StudentEnroll.class, studentIds);
        List<Student> students = new ArrayList<Student>();
        for (StudentEnroll enroll : enrolls) {
            if (enrolled) {
                enroll.setNoticed(true);
            }
            enroll.setEnrolled(enrolled);
            Student student = enroll.getStudent();
            student.setRegisted(enrolled);
            if (enrolled) {
            	prepareService.createStudent(enroll);
//				创建学生用户
//            	User user = bspBaseService.createUser(student.getCode(), student.getName(), getCurrentUser(), 1L, "学生");
//            	student.setUser(user);
            }
            students.add(student);
        }
        entityDao.saveOrUpdate(enrolls, students);
        return redirect("search", "info.save.success");
    }
    
    public User getCurrentUser() {
    	Long userId = null;
		try {
			userId = SecurityUtils.getUserId();
		} catch (Exception e) {
		}
		if (userId != null) {
			return entityDao.get(User.class, userId);
		}
		return null;
    }

    @Override
    public ItemImporterListener getImporterListener() {
        StudentEnrollImportListener importListener = new StudentEnrollImportListener();
        return importListener;
    }
}
