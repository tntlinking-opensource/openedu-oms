package com.yzsoft.yxxt.finance.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.model.FinanceTemplate;
import com.yzsoft.yxxt.finance.model.FinanceTemplateItem;
import com.yzsoft.yxxt.finance.service.FinanceService;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.service.MajorService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.*;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceTemplateAction extends EntityDrivenAction {

    @Resource
    private YxxtService yxxtService;
	@Resource
	private FinanceService financeService;
	@Resource
	private DictDataService dictDataService;
	@Resource
	private MajorService majorService;

	@Override
	protected String getEntityName() {
		return FinanceTemplate.class.getName();
	}

	@Override
	public String index() {
        put("year", yxxtService.getYear());
		put("years", financeService.findYear());
		return super.index();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		FinanceTemplate tempalte = (FinanceTemplate) entity;
		if (tempalte.getYear() == null) {
			tempalte.setYear(yxxtService.getYear());
		}
		Set<Department> departments = new LinkedHashSet<Department>();
		for(Major major : tempalte.getMajors()){
			departments.add(major.getDepartment());
		}
		put("educations", dictDataService.findDictData("XLCC"));
		put("items", financeService.findItem());
		put("departments", departments);
		List<DictData> majorTypes = majorService.findTypes();
		majorTypes.add(0, new DictData(0L, "全部"));
		put("majorTypes", majorTypes);
		super.editSetting(entity);
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		FinanceTemplate tempalte = (FinanceTemplate) entity;
		tempalte.getMajors().clear();
		if (tempalte.isLimitMajor()) {
			Long[] majorIds = StrUtils.splitToLong(get("majorIds"));
			List<Major> majors = entityDao.get(Major.class, majorIds);
			tempalte.getMajors().addAll(majors);
		}

		tempalte.getItems().clear();
		double money = 0;
		tempalte.getItems().addAll(getAllEntity(FinanceTemplateItem.class, "item"));
		for (Iterator<FinanceTemplateItem> itor = tempalte.getItems().iterator(); itor.hasNext(); ) {
			FinanceTemplateItem item = itor.next();
			if (item.getMoney() == 0) {
				itor.remove();
			}
		}
		for (FinanceTemplateItem item : tempalte.getItems()) {
			money += item.getMoney();
			item.setTemplate(tempalte);
		}
		tempalte.setMoney(money);
		if(tempalte.getMajorType() != null && tempalte.getMajorType().getId() == 0){
			tempalte.setMajorType(null);
		}
		return super.saveAndForward(entity);
	}

	public String copy() {
		financeService.copy();
		return redirect("search", "info.save.success");
	}
}
