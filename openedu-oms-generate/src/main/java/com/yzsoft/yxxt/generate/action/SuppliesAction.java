package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import com.yzsoft.yxxt.web.collect.supplies.service.SuppliesService;
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
public class SuppliesAction extends SyncAction {

    @Resource
    private SuppliesService suppliesService;

    /**
     * http://127.0.0.1:9000/yxxt/generate/supplies.action
     *
     * @return
     * @throws Exception
     */
    public String index() {
        List<Student> students = entityDao.getAll(Student.class);
        List<Supplies> suppliess = suppliesService.find();
        Random random = new Random();

        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        try {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
//				SuppliesStd ss = suppliesService.get(student.getUser().getId());
                SuppliesStd ss = null;
                if (ss == null) {
                    ss = new SuppliesStd();
//                    ss.setUser(student.getUser());
                    ss.setStudent(student);
                }
                ss.getItems().clear();
                int num = 0;
                double total = 0D;
                for (Supplies supplies : suppliess) {
                    if (random.nextInt(10) > 7) {
                        continue;
                    }
                    SuppliesStdItem item = new SuppliesStdItem();
                    item.setSuppliesStd(ss);
                    item.setSupplies(supplies);
                    item.setNum(1);
                    item.setPrice(supplies.getPrice());
                    item.setTotal(supplies.getPrice());
                    num++;
                    if (item.getPrice() != null) {
                        total += item.getPrice();
                    }
                    ss.getItems().add(item);
                }
                ss.setNum(num);
                ss.setTotal(total);
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
