package com.yzsoft.yxxt.finance.importer;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Student;
import org.springframework.util.Assert;

import java.util.List;

public class FinanceStatusImporter extends ItemImporterListener {

    private YxxtService yxxtService;

    public FinanceStatusImporter() {
        yxxtService = getBean(YxxtService.class);
    }

    public void onItemFinish(TransferResult tr) {
        FinanceStudent source = (FinanceStudent) importer.getCurrent();
        FinanceStudent status = getObject(source);
        if (status == null) {
            status = new FinanceStudent();
            status.setYear(yxxtService.getYear());
            status.setStudent(get(Student.class, source.getStudent(),"cardcode", null));
            Assert.notNull(status.getStudent(), "身份证号有误");
        }
        source.setStudent(null);
        source.setYear(null);
        source.setItems(null);
        copyPropertiesIgnoreNull(source, status);
        saveOrUpdate(status);
    }

    private FinanceStudent getObject(FinanceStudent source) {
        Assert.isTrue(source.getStudent() != null && source.getStudent().getCardcode() != null, "身份证号不能为空");
        OqlBuilder query = OqlBuilder.from(FinanceStudent.class);
        query.where("student.cardcode = :cardcode", source.getStudent().getCardcode());
        List<FinanceStudent> list = entityDao.search(query);
        return list.isEmpty() ? null : list.get(0);
    }
}
