package com.yzsoft.yxxt.web.collect.supplies.action;

import com.yzsoft.yxxt.web.collect.CollectAction;
import com.yzsoft.yxxt.web.collect.model.Supplies;
import com.yzsoft.yxxt.web.collect.model.SuppliesStd;
import com.yzsoft.yxxt.web.collect.model.SuppliesStdItem;
import com.yzsoft.yxxt.web.collect.supplies.service.SuppliesService;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public class SuppliesInfoParentAction extends CollectAction {

    @Resource
    protected SuppliesService suppliesService;

    @Override
    public String getCode() {
        return "04";
    }

    @Override
    public String index() {
        SuppliesStd suppliesStd = suppliesService.get(getUserId());
        if (suppliesStd == null || suppliesStd.getId() == null) {
            if (getSwitch().isEditable()) {
                return redirect("edit");
            }
        }
        put("suppliesStd", suppliesStd);
        return super.index();
    }

    @Override
    public String edit() {
        SuppliesStd suppliesStd = suppliesService.get(getUserId());
        put("suppliesStd", suppliesStd);
        put("suppliess", suppliesService.find());
        return super.edit();
    }

    @Override
    public String save() {
        SuppliesStd suppliesStd = getSuppliesStd();
        beforeSave(suppliesStd);
        entityDao.saveOrUpdate(suppliesStd);
        afterSave();
        return redirect("index");
    }

    protected SuppliesStd getSuppliesStd(){
        SuppliesStd suppliesStd = suppliesService.get(getUserId());
        populate(suppliesStd, "suppliesStd");
        suppliesStd.getItems().clear();
        List<SuppliesStdItem> items = getAllEntity(SuppliesStdItem.class, "suppliesStdItem");
        Map<Long, Supplies> map = suppliesService.getMap();
        for (SuppliesStdItem item : items) {
            Supplies supplies = map.get(item.getSupplies().getId());
            item.setSuppliesStd(suppliesStd);
            item.setPrice(supplies.getPrice());
            if (item.getPrice() != null) {
                item.setTotal(item.getNum() * item.getPrice());
            }
        }
        suppliesStd.getItems().addAll(items);
        countNumAndTotal(suppliesStd);
        return suppliesStd;
    }

    protected void countNumAndTotal(SuppliesStd suppliesStd) {
        Integer num = 0;
        Double total = 0D;
        Map<Long, Supplies> map = suppliesService.getMap();
        for (SuppliesStdItem item : suppliesStd.getItems()) {
            item.setSuppliesStd(suppliesStd);
            item.setPrice(map.get(item.getSupplies().getId()).getPrice());
            if (item.getPrice() != null) {
                item.setTotal(item.getNum() * item.getPrice());
                total += item.getTotal();
            }
            if (item.getNum() != null) {
                num += item.getNum();
            }
        }
        suppliesStd.setNum(num);
        suppliesStd.setTotal(total);
    }
}
