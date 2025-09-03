package com.yzsoft.yxxt.process.service.impl;

import com.yzsoft.yxxt.process.service.YxxtProcessService;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class YxxtProcessServiceImpl extends EntityDaoSupport implements YxxtProcessService {

    @Override
    public Long getStudentNum(ProcessBatch batch) {
        if (batch == null || batch.getEducations().isEmpty()) {
            return 0L;
        }
        OqlBuilder query = OqlBuilder.from(Student.class);
        query.select("count(*)");
        query.where("registed = true");
        query.where("grade = :grade", batch.getYear());
        query.where("grade = :grade", batch.getYear());
        query.where("education in (:education)", batch.getEducations());
        List<Long> datas = entityDao.search(query);
        return datas.get(0);
    }

    @Override
    public ProcessBatch getBatch(Student student) {
        OqlBuilder query = OqlBuilder.from(ProcessBatch.class, "batch");
        query.where("batch.year = :grade", student.getGrade());
        query.join("batch.educations", "education");
        query.where("education = :education", student.getEducation());
        query.cacheable();
        List<ProcessBatch> list = entityDao.search(query);
        return list.isEmpty() ? null : list.get(0);
    }
}
