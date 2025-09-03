package com.yzsoft.yxxt.welcome.service;

import com.yzsoft.yxxt.welcome.model.WelcomeStudent;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.service.ProcessBatchService;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class WelcmeServiceImpl implements WelcomeService {

    @Resource
    private EntityDao entityDao;
    @Resource
    private ProcessBatchService processBatchService;

    @Override
    public List<String> findYear() {
        OqlBuilder query = OqlBuilder.from(ProcessBatch.class);
        query.select("year");
        query.groupBy("year");
        query.orderBy("year desc");
        query.cacheable();
        return entityDao.search(query);
    }

    @Override
    public void processLinkPassed(Long batchId, Long itemId, Long studentId) {
        ProcessLinkItem item = entityDao.get(ProcessLinkItem.class, itemId);
        if (item.isKeyLinked()) {
            register(studentId);
        }
    }

    public void register(Long studentId) {
        register(studentId, true);
    }

    @Override
    public void register(Long studentId, Boolean registered) {
        WelcomeStudent welcomeStudent = getWelcomeStudent(studentId);
        welcomeStudent.setRegistered(registered);
        welcomeStudent.setReason(null);
        entityDao.saveOrUpdate(welcomeStudent);
    }

    @Override
    public ProcessBatch getBatch(Long studentId) {
        List<ProcessBatch> batches = processBatchService.findBatch();
        Student student = entityDao.get(Student.class, studentId);
        for (ProcessBatch batch : batches) {
            if (batch.getYear().equals(student.getGrade())) {
                for (DictData education : batch.getEducations()) {
                    if (education.equals(student.getEducation())) {
                        return batch;
                    }
                }
            }
        }
        return null;
    }

    private WelcomeStudent getWelcomeStudent(Long studentId) {
        WelcomeStudent welcomeStudent = entityDao.getEntity(WelcomeStudent.class, "student.id", studentId);
        if (welcomeStudent == null) {
            welcomeStudent = new WelcomeStudent();
            welcomeStudent.setStudent(new Student(studentId));
        }
        return welcomeStudent;
    }
}
