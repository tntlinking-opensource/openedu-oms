package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.prepare.model.Batch;
import com.yzsoft.yxxt.prepare.model.StudentEnroll;
import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.prepare.service.BatchService;
import org.beangle.product.core.model.Campus;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.web.util.GeneratePrint;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentEnrollAction extends SyncAction {

    @Resource
    private YxxtService yxxtService;
    @Resource
    private BatchService batchService;
    @Resource
    private DictDataService dictDataService;

    /**
     * http://127.0.0.1:9000/yxxt/generate/student-enroll.action
     *
     * @return
     * @throws Exception
     */
    public String index() {
        Batch batch = batchService.getBatch();
        List<Campus> campuses = entityDao.getAll(Campus.class);
        List<DictData> genders = dictDataService.findDictData("GENDER");
        List<DictData> enrollTypes = dictDataService.findDictData("STD_ENROLL_TYPE");
        List<DictData> educations = dictDataService.findDictData("XLCC");
        DictData nation = dictDataService.getDictData("MZ_CODE_01");
//        String grade = yxxtService.getGrade();
        Random random = new Random();
        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        for (int i = 1; i <= 100; i++) {
            Student student = new Student();
            student.setGrade(batch.getYear());
            student.setCode(student.getGrade() + String.format("%03d", i));
            student.setCardcode("SFZ" + student.getCode());
            student.setName("姓名" + String.format("%03d", i));
            student.setPhone("021-12345" + String.format("%03d", i));
            student.setCampus(campuses.get(random.nextInt(campuses.size())));
            student.setEducation(educations.get(random.nextInt(educations.size())));
            student.setGender(genders.get(random.nextInt(genders.size())));
            student.setEnrollType(enrollTypes.get(random.nextInt(enrollTypes.size())));
            student.setRegisted(false);
            student.setInSchooled(false);
//            entityDao.saveOrUpdate(student);
            StudentInfo info = new StudentInfo();
//            info.setStudent(student);
            info.setNation(nation);
//            entityDao.saveOrUpdate(info);
            student.setInfo(info);
//            entityDao.saveOrUpdate(student);
            StudentEnroll studentEnroll = new StudentEnroll();
            studentEnroll.setBatch(batch);
            studentEnroll.setCode(student.getCode());
            studentEnroll.setEnrollCode(student.getCode());
            studentEnroll.setStudent(student);
            entityDao.saveOrUpdate(info, student, studentEnroll);
            print.write(student.getName());
        }
        print.end();
        return null;
    }
}
