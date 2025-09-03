package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesCollectAction extends SuppliesInfoParentAction {

	@Override
	public String getCode() {
		return "04_1";
	}

	@Override
	protected void indexSetting() {
		super.indexSetting();
		put("suppliess", suppliesService.find());
	}

	@Override
	public String edit() {
		SuppliesStd suppliesStd = suppliesService.get(getUserId());
		if (suppliesStd.getItems().isEmpty()) {
			addItems(suppliesStd);
		}
		put("suppliesStd", suppliesStd);
		checkSwitch();
		return forward();
	}

	private void addItems(SuppliesStd suppliesStd) {
		List<Supplies> suppliess = suppliesService.find();
		for (Supplies supplies : suppliess) {
			SuppliesStdItem item = new SuppliesStdItem();
			item.setSuppliesStd(suppliesStd);
			item.setSupplies(supplies);
			item.setNum(supplies.getNum());
			item.setPrice(supplies.getPrice());
			if (item.getNum() != null && item.getPrice() != null) {
				item.setTotal(item.getNum() * item.getPrice());
			}
			suppliesStd.getItems().add(item);
		}
		countNumAndTotal(suppliesStd);
	}

	protected SuppliesStd getSuppliesStd() {
		SuppliesStd suppliesStd = suppliesService.get(getUserId());
		suppliesStd.getItems().clear();
		boolean agree = getBool("agree");
		if (agree) {
			addItems(suppliesStd);
		} else {
			suppliesStd.setNum(0);
			suppliesStd.setTotal(0D);
		}
		suppliesStd.setAgree(agree);
		return suppliesStd;
	}

}
