package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudent;
import com.yzsoft.yxxt.finance.model.FinanceGreenStudentItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Service
public class FinanceGreenServiceImpl extends EntityDaoSupport implements FinanceGreenService {

    @Resource
    private FinanceStudentService financeStudentService;

    @Override
    public List<FinanceGreen> find() {
        OqlBuilder query = OqlBuilder.from(FinanceGreen.class);
        query.where("enabled = true");
        query.orderBy("code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<FinanceGreen> find(DictData education) {
        OqlBuilder query = OqlBuilder.from(FinanceGreen.class, "o");
        query.where("o.enabled = true");
        query.join("o.educations", "e");
        query.where("e.id = :educationId", education.getId());
        query.orderBy("o.code");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public List<FinanceGreenStudentItem> find(Long batchId, Long studentId) {
        OqlBuilder query = OqlBuilder.from(FinanceGreenStudentItem.class);
        if (batchId != null) {
            query.where("student.batch.id = :batchId", batchId);
        }
        query.where("student.student.id = :studentId", studentId);
        return entityDao.search(query);
    }

    @Override
    @Transactional
    public void save(Long batchId, Long studentId, List<FinanceGreenStudentItem> financeGreenStudentItems, String remark,FinanceStudent financeStu) {
        Student student = new Student(studentId);
        FinanceGreenStudent financeGreenStudent = getFinanceGreenStudent(studentId);
        if (financeGreenStudent.getId() == null) {
            financeGreenStudent = new FinanceGreenStudent();
            financeGreenStudent.setBatch(new ProcessBatch(batchId));
            financeGreenStudent.setStudent(student);
        }
        financeGreenStudent.setRemark(remark);
        financeGreenStudent.getItems().clear();
        Double greenMoney = 0D;
        for (FinanceGreenStudentItem fgs : financeGreenStudentItems) {
            greenMoney += fgs.getMoney();
            fgs.setStudent(financeGreenStudent);
        }
        financeGreenStudent.getItems().addAll(financeGreenStudentItems);
        financeGreenStudent.setMoney(greenMoney);

        FinanceStudent financeStudent = financeStudentService.getOrCreateFinanceStudent(student);
        financeStudent.setGreenMoney(greenMoney);
        financeStudent.countFeeOdd();
//		Assert.isTrue(financeStudent.getFeeOdd() >= 0, "减免金额大于应缴金额，操作失败！");
        if (financeStudent.getFeeOdd() < 0) {
            financeStudent.setFeeOdd(0D);
        }
        entityDao.saveOrUpdate(financeStudent);
//		entityDao.saveOrUpdate(financeGreenStudentItems);
        entityDao.saveOrUpdate(financeGreenStudent);
    }

    public FinanceGreenStudent getFinanceGreenStudent(Long studentId) {
        FinanceGreenStudent financeGreenStudent = entityDao.getEntity(FinanceGreenStudent.class, "student.id", studentId);
        if (financeGreenStudent == null) {
            financeGreenStudent = new FinanceGreenStudent();
        }
        return financeGreenStudent;
    }
}
