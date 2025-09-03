package com.yzsoft.yxxt.welcome.process.action;

import java.util.List;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.service.CollectService;

import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.process.model.AuditGroup;
import org.beangle.process.model.ProcessBatch;
import org.beangle.process.model.ProcessLinkItem;
import org.beangle.process.model.ProcessLinkItemForm;
import org.beangle.process.model.ProcessLinkItemFormValue;
import org.beangle.process.model.ProcessLinkItemLog;
import org.beangle.process.model.ProcessLinkItemPrint;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.StudentInfo;
import org.beangle.product.core.model.Teacher;
import org.beangle.struts2.convention.route.Action;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesAction extends ProcessLinkActionSupport {

    @Resource
    private CollectService collectService;
    @Resource
    private YxxtService yxxtService;

    @Override
	protected String getEntityName() {
		return Student.class.getName();
	}
    
    @Override
	protected OqlBuilder<?> getOqlBuilder() {
		OqlBuilder query = super.getOqlBuilder();
		if(StringUtils.isNotEmpty(get("agree"))){
			if(getBool("agree")){
				query.where("exists (from "+ SuppliesStd.class.getName() +" su where su.student.id = student.id and su.agree = 1)");
			}else{
				query.where("not exists (from "+ SuppliesStd.class.getName() +" su where su.student.id = student.id and su.agree = 1)");
			}
		}
		entityDao.search(query);
		return query;
	}
    
    protected void save(Long batchId, Student student) {
    	super.save(batchId, student);
    	SuppliesStd suppliesStd = entityDao.getEntity(SuppliesStd.class, "student", student);
    	suppliesStd.setUsed(true);
    	entityDao.saveOrUpdate(suppliesStd);
	}
    
    protected void cancel(Long batchId, Student student) {
    	super.cancel(batchId, student);
    	SuppliesStd suppliesStd = entityDao.getEntity(SuppliesStd.class, "student", student);
    	suppliesStd.setUsed(false);
    	entityDao.saveOrUpdate(suppliesStd);
	}
}
