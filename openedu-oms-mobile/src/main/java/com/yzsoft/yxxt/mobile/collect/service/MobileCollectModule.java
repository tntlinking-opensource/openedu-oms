package com.yzsoft.yxxt.mobile.collect.service;

import com.yzsoft.yxxt.mobile.collect.action.*;
import com.yzsoft.yxxt.web.collect.clothes.action.ClothesCollectMobileAction;
import com.yzsoft.yxxt.web.collect.service.MobileCollectModuleSupport;
import com.yzsoft.yxxt.web.collect.supplies.action.SuppliesCollectMobileAction;
import org.springframework.stereotype.Component;

@Component
public class MobileCollectModule extends MobileCollectModuleSupport {

	@Override
	protected void addAction() {
		addDefaultAction(StudentInfoAction.class);
		addDefaultAction(StationAction.class);
		addDefaultAction(ClothesAction.class);
		addDefaultAction(SuppliesAction.class);
		addDefaultAction(FinanceGreenAction.class);
		addDefaultAction(MajorAction.class);
		addDefaultAction(DormAction.class);
		addDefaultAction(DormBedMobileAction.class);
		addDefaultAction(DormMobileAction.class);
		addDefaultAction(ClothesCollectMobileAction.class);
		addDefaultAction(SuppliesCollectMobileAction.class);
	}

}
