package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.finance.model.DormSetting;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.DormService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("YxxtWelcomeDormService")
public class DormServiceImpl extends EntityDaoSupport implements DormService {

    @Autowired
    protected FinanceStudentService financeStudentService;

    @Override
    public List<DormSetting> findSetting() {
        OqlBuilder query = OqlBuilder.from(DormSetting.class);
        query.orderBy("item.code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public void accommodationed(Student student, boolean accommodationed) {
        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        if (financeStudent != null) {
            List<DormSetting> dormSettings = findSetting();
            for (DormSetting setting : dormSettings) {
                financeStudentService.updateFinanceStudentItem(financeStudent, setting.getItem(), setting.getMoney(), accommodationed);
            }
            financeStudentService.count(financeStudent);
            entityDao.saveOrUpdate(financeStudent);
        }
    }

}
