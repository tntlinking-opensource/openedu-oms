package com.yzsoft.yxxt.finance.action;

import com.yzsoft.yxxt.finance.model.FinanceItem;
import org.beangle.ems.security.nav.Menu;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceItemAction extends EntityDrivenAction {

	@Override
	protected String getEntityName() {
		return FinanceItem.class.getName();
	}

	public String activate() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("isActivate");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<FinanceItem> items = entityDao.get(FinanceItem.class, menuIds);
		for (FinanceItem item : items) {
			item.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(items);
		return redirect("search", "info.save.success");
	}

	public String loanable() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean loanable = getBoolean("loanable");
		if (null == loanable) {
			loanable = Boolean.TRUE;
		}
		List<FinanceItem> items = entityDao.get(FinanceItem.class, menuIds);
		for (FinanceItem item : items) {
			item.setLoanable(loanable);
		}
		entityDao.saveOrUpdate(items);
		return redirect("search", "info.save.success");
	}

	public String delayable() {
		Long[] menuIds = getEntityIds(getShortName());
		Boolean delayable = getBoolean("delayable");
		if (null == delayable) {
			delayable = Boolean.TRUE;
		}
		List<FinanceItem> items = entityDao.get(FinanceItem.class, menuIds);
		for (FinanceItem item : items) {
			item.setDelayable(delayable);
		}
		entityDao.saveOrUpdate(items);
		return redirect("search", "info.save.success");
	}

}
