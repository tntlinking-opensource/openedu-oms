package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import com.yzsoft.yxxt.finance.service.FinanceGreenService;
import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.FinanceGreenStd;
import com.yzsoft.yxxt.web.collect.service.FinanceGreenCollectService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceGreenStdInfoAction extends CollectAction {

	@Resource
	private FinanceGreenService financeGreenService;
	@Resource
	private FinanceGreenCollectService financeGreenCollectService;

	@Override
	public String getCode() {
		return "05";
	}

	@Override
	public String index() {
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(getUserId());
		if (financeGreenStd == null) {
			if (getSwitch().isEditable()) {
				return redirect("edit");
			}
		}
		put("financeGreenStd", financeGreenStd);
		return super.index();
	}

	@Override
	public String edit() {
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(getUserId());
		if (financeGreenStd == null) {
			financeGreenStd = new FinanceGreenStd();
		}
		put("financeGreenStd", financeGreenStd);
		put("financeGreens", financeGreenService.find());
		return super.edit();
	}

	@Override
	public String save() {
		FinanceGreenStd financeGreenStd = financeGreenCollectService.get(getUserId());
		if (financeGreenStd == null) {
			financeGreenStd = new FinanceGreenStd();
		}
		beforeSave(financeGreenStd);
		populate(financeGreenStd, "financeGreenStd");
		financeGreenStd.getFinanceGreens().clear();
		if (financeGreenStd.getHandle()) {
			List<FinanceGreen> financeGreens = entityDao.get(FinanceGreen.class, getAll("financeGreenId", Long.class));
			financeGreenStd.getFinanceGreens().addAll(financeGreens);
		}
		entityDao.saveOrUpdate(financeGreenStd);
		afterSave();
		return redirect("index");
	}
}
