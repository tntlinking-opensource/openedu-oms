package org.beangle.product.core.importer;

import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.User;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.*;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.website.system.service.DictDataService;
import org.springframework.util.Assert;

public class StudentImportListener extends ItemImporterListener {

    private EntityDao entityDao;
    /**
     * 服务平台基础接口
     */
    private BspBaseService bspBaseService;
    private DictDataService dictDataService;
    private User currentUser;

    public StudentImportListener(User currentUser, EntityDao entityDao, DictDataService dictDataService, BspBaseService bspBaseService) {
        super();
        this.currentUser = currentUser;
        this.entityDao = entityDao;
        this.bspBaseService = bspBaseService;
        this.dictDataService = dictDataService;
    }

    public void onItemFinish(TransferResult tr) {
        Student ostudent = (Student) importer.getCurrent();
        ostudent.setFamilies(null);
        Student student = getStudent(ostudent);
        copyPropertiesIgnoreNull(ostudent, student);
        student.setGender(dictDataService.getDictData("GENDER", ostudent.getGender() == null ? "" : ostudent.getGender().getName()));
        student.setEducation(dictDataService.getDictData("XLCC", ostudent.getEducation() == null ? "" : ostudent.getEducation().getName()));
        student.setCampus(get(Campus.class, ostudent.getCampus(), student.getCampus()));
        student.setDepartment(get(Department.class, ostudent.getDepartment(), student.getDepartment()));
        student.setMajor(get(Major.class, ostudent.getMajor(), student.getMajor()));
        student.setDirection(get(Direction.class, ostudent.getDirection(), student.getDirection()));
        student.setAdminClass(get(AdminClass.class, ostudent.getAdminClass(), student.getAdminClass()));
        User user = createUser(student);
        student.setUser(user);
        if (null == student.getInfo()) {
            StudentInfo studentInfo = new StudentInfo();
//			studentInfo = new StudentInfo();
//			studentInfo.setStudent(student);
//			studentInfo.setUser(student.getUser());
            student.setInfo(studentInfo);
            entityDao.saveOrUpdate(studentInfo);
        }
        saveOrUpdate(student);
    }

    private User createUser(Student student) {
        Assert.isTrue(StringUtils.isNotBlank(student.getCode()), "学号不能为空");
        User user = entityDao.getEntity(User.class, "name", student.getCardcode());
        if (user == null) {
            return bspBaseService.createNewUser(null, student.getCode(), student.getName(), currentUser, 1L, "学生");
        }
        return user;
    }

    private Student getStudent(Student ostudent) {
        Assert.isTrue(StringUtils.isNotBlank(ostudent.getCode()), "学号不能为空");
        Student student = entityDao.getEntity(Student.class, "code", ostudent.getCode());
        if (student == null) {
            student = new Student();
        }
        return student;
    }
}
