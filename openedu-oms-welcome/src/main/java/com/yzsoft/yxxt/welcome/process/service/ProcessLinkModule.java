package com.yzsoft.yxxt.welcome.process.service;

import com.yzsoft.yxxt.welcome.process.action.*;
import org.beangle.process.service.ProcessLinkModuleSupport;
import org.springframework.stereotype.Component;

@Component
public class ProcessLinkModule extends ProcessLinkModuleSupport {

	@Override
	protected void addAction() {
		setDefaultAction(ProcessLinkCommAction.class);
		addAction("G01", RegisteredAction.class);
		addAction("G02", FinanceAction.class);
		addAction("G03", FinanceGreenAction.class);
		addAction("G031", FinanceGreen2Action.class);
		addAction("G04", DormAllocAction.class);
		addAction("G042", DormAction.class);
//        addAction("G05", PrintAction.class);
		addAction("G06", PhotoAction.class);
		addAction("T03", SuppliesAction.class);
	}

}
