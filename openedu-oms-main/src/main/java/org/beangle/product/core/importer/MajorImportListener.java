package org.beangle.product.core.importer;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.system.model.DictData;
import org.springframework.util.Assert;

import java.util.List;

/**
 * 专业导入监听器
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年9月18日 上午10:32:04
 */
public class MajorImportListener extends ItemImporterListener {

    private EntityDao entityDao;
    private DepartmentService departmentService;

    public MajorImportListener(EntityDao entityDao, DepartmentService departmentService) {
        super();
        this.entityDao = entityDao;
        this.departmentService = departmentService;
    }

    public void onItemFinish(TransferResult tr) {
        Major oMajor = (Major) importer.getCurrent();
        Major major = getMajor(oMajor);
        copyPropertiesIgnoreNull(oMajor, major);
        if (null != oMajor.getDepartment()) {
            Assert.isTrue(StringUtils.isNotBlank(oMajor.getDepartment().getCode()), "院系代码不能为空");
            major.setDepartment(departmentService.getDepartmentByCode(oMajor.getDepartment().getCode()));
        }
        DictData education = getDictData(oMajor.getEducation().getName());
        if (education == null) {
            tr.addFailure("学历层次设置有问题", "当前系统中未找到名称为" + oMajor.getEducation().getName() + "的学历层次");
        }
        major.setEducation(education);
        entityDao.saveOrUpdate(major);
    }


    private Major getMajor(Major oMajor) {
        Assert.isTrue(StringUtils.isNotBlank(oMajor.getCode()), "专业代码不能为空");
        Major major = entityDao.getEntity(Major.class, "code", oMajor.getCode());
        if (major == null) {
            major = new Major();
        }
        return major;
    }

    //学历层次
    protected DictData getDictData(String name) {
        OqlBuilder<DictData> oql = OqlBuilder.from(DictData.class, "dd");
        oql.where("dd.enabled = true");
        oql.where("dd.dictType.code = :code", "XLCC");
        oql.orderBy("dd.code");
        oql.cacheable();
        for (DictData dictData : entityDao.search(oql)) {
            if (dictData.getName().equals(name)) {
                return dictData;
            }
        }
        return null;
    }
}
