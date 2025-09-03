package com.yzsoft.yxxt.finance.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.model.query.builder.OqlBuilder;

public interface FinanceStatusExportDetailService {

	HSSFWorkbook getWorkbook(OqlBuilder query);

}
