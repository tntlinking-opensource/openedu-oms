package com.yzsoft.yxxt.generate.action;

import com.yzsoft.yxxt.web.collect.model.*;
import com.yzsoft.yxxt.web.collect.service.ClothesService;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.beangle.product.sync.action.SyncAction;
import org.beangle.web.util.GeneratePrint;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ClothesStudentAction extends SyncAction {

    @Resource
    private ClothesService clothesService;

    /**
     * http://127.0.0.1:9000/yxxt/generate/clothes-student.action
     *
     * @return
     * @throws Exception
     */
    public String index() {
        List<Student> students = entityDao.getAll(Student.class);
        List<ClothesSize> clothesSizes = clothesService.findClothesSize();
        List<ShoesSize> shoesSizes = clothesService.findShoesSize();
        Random random = new Random();

        GeneratePrint print = new GeneratePrint(getResponse());
        print.start();
        try {
            for (int i = 0; i < students.size(); i++) {
                Student student = students.get(i);
                ClothesStudent ss = new ClothesStudent();
                ss.setStudent(student);
//                ss.setUser(student.getUser());
//				ss.setHeight(150 + random.nextInt(40));
//				ss.setWeight(40 + random.nextInt(30));
//				ss.setBust(70 + random.nextInt(30));
//				ss.setWaist(60 + random.nextInt(30));
//				ss.setHip(70 + random.nextInt(30));
                ss.setClothesSize(clothesSizes.get(random.nextInt(clothesSizes.size())).getName());
                ss.setShoesSize(shoesSizes.get(random.nextInt(shoesSizes.size())).getName());
                entityDao.saveOrUpdate(ss);
                print.write(student.getName());
            }
        } catch (Exception e) {

        }
        print.end();
        return null;
    }
}
