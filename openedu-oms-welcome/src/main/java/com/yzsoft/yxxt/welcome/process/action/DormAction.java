package com.yzsoft.yxxt.welcome.process.action;

import com.yzsoft.yxxt.finance.model.DormSetting;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.DormService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DormAction extends ProcessLinkActionSupport {

    @Resource
    protected FinanceStudentService financeStudentService;

    @Resource
    protected DormService dormService;

    @Override
    protected void editSetting(Long batchId, Student student) {
        super.editSetting(batchId, student);
        StudentInfo studentInfo = student.getInfo();
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        put("studentInfo", studentInfo);
        put("financeStudent", financeStudent);
        put("dormSettings", dormService.findSetting());
    }

    @Override
    protected void save(Long batchId, Student student) {
        super.save(batchId, student);
        boolean accommodationed = getBool("accommodationed");
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        List<DormSetting> dormSettings = dormService.findSetting();
        for (DormSetting setting : dormSettings) {
            financeStudentService.updateFinanceStudentItem(financeStudent, setting.getItem(), setting.getMoney(), accommodationed);
        }
        financeStudentService.count(financeStudent);
        entityDao.saveOrUpdate(financeStudent);
        StudentInfo studentInfo = student.getInfo();
        studentInfo.setAccommodationed(accommodationed);
        entityDao.saveOrUpdate(studentInfo);
    }

    @Override
    protected void cancel(Long batchId, Student student) {
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        List<DormSetting> dormSettings = dormService.findSetting();
        for (DormSetting setting : dormSettings) {
            financeStudentService.updateFinanceStudentItem(financeStudent, setting.getItem(), setting.getMoney(), false);
        }
        financeStudentService.count(financeStudent);
        entityDao.saveOrUpdate(financeStudent);
        super.cancel(batchId, student);
    }

}
