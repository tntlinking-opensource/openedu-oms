package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import org.beangle.product.core.model.Student;
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
public class FinanceGreenStdAction extends SyncAction {

    @Resource
    private FinanceGreenService financeGreenService;

    public String index() {
        List<Student> students = entityDao.getAll(Student.class);
        List<FinanceGreen> financeGreens = financeGreenService.find();

        Random random = new Random();

        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        try {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                FinanceGreenStd ss = null;
                if (ss == null) {
                    ss = new FinanceGreenStd();
//                    ss.setUser(student.getUser());
                    ss.setStudent(student);
                }
                ss.getFinanceGreens().clear();
                int num = 0;
                double total = 0D;
                for (FinanceGreen financeGreen : financeGreens) {
                    if (random.nextInt(10) > 5) {
                        continue;
                    }
                    ss.getFinanceGreens().add(financeGreen);
                }
                ss.setHandle(ss.getFinanceGreens().size() > 0);
                entityDao.saveOrUpdate(ss);
                print.write(student.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        print.end();
        return null;
    }
}
