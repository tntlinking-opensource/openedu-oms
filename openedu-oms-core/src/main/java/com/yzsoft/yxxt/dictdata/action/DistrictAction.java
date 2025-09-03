package com.yzsoft.yxxt.dictdata.action;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * 行政区划代码
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class DistrictAction extends DictDataAction {
	@Override
	protected String getDictTypeCode() {
		return "DISTRICT";
	}
}
