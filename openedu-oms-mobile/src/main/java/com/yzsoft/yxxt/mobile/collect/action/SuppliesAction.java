package com.yzsoft.yxxt.mobile.collect.action;

import com.yzsoft.yxxt.web.collect.supplies.action.SuppliesInfoAction;
import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SuppliesAction extends SuppliesInfoAction {

	@Override
	public String index() {
		SuppliesStd suppliesStd = suppliesService.get(getUserId());
		put("suppliesStd", suppliesStd);
		put("suppliess", suppliesService.find());
		put("switch", getSwitch());
		return forward();
	}

	@Override
	public String edit() {
		Supplies supplies = entityDao.get(Supplies.class, getLong("id"));
		SuppliesStdItem suppliesStdItem = suppliesService.getItem(supplies.getId(), getUserId());
		put("supplies", supplies);
		put("suppliesStdItem", suppliesStdItem);
		put("switch", getSwitch());
		return forward();
	}

	@Override
	public String save() {
		SuppliesStd suppliesStd = suppliesService.get(getUserId());
		beforeSave(suppliesStd);
		SuppliesStdItem suppliesStdItem = null;
		Supplies supplies = entityDao.get(Supplies.class, getLong("supplies.id"));
		for (SuppliesStdItem item : suppliesStd.getItems()) {
			if (item.getSupplies().equals(supplies)) {
				suppliesStdItem = item;
			}
		}
		if (suppliesStdItem == null) {
			suppliesStdItem = new SuppliesStdItem();
			suppliesStdItem.setSupplies(supplies);
			suppliesStdItem.setSuppliesStd(suppliesStd);
			suppliesStd.getItems().add(suppliesStdItem);
		}
		suppliesStdItem.setPrice(supplies.getPrice());
		suppliesStdItem.setNum(getInteger("num"));
		if (suppliesStdItem.getNum() != null && suppliesStdItem.getPrice() != null) {
			suppliesStdItem.setTotal(suppliesStdItem.getPrice() * suppliesStdItem.getNum());
		}
		if (suppliesStdItem.getId() != null && suppliesStdItem.getNum() == 0) {
			suppliesStd.getItems().remove(suppliesStdItem);
		}
		countNumAndTotal(suppliesStd);
		entityDao.saveOrUpdate(suppliesStd);
		return redirect("index");
	}

	@Override
	public String remove() throws Exception {
		Long id = getLong("id");
		SuppliesStdItem item = entityDao.get(SuppliesStdItem.class, id);
		if (item != null) {
			Assert.isTrue(item.getSuppliesStd().getStudent().getUser().getId().equals(getUserId()));
			entityDao.remove(item);
		}
		return redirect("index");
	}
}
