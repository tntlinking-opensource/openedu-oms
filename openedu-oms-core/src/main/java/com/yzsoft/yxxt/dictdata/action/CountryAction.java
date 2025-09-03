package com.yzsoft.yxxt.dictdata.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CountryAction extends DictDataAction {
	@Override
	protected String getDictTypeCode() {
		return "COUNTRY";
	}
}
