package com.yzsoft.yxxt.finance.service.impl;

import com.yzsoft.yxxt.finance.model.FinanceItem;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.model.FinanceStudentItem;
import com.yzsoft.yxxt.finance.service.FinanceStatusExportDetailService;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.*;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Major;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class FinanceStatusExportDetailServiceImpl extends EntityDaoSupport implements FinanceStatusExportDetailService {

	@Override
	public HSSFWorkbook getWorkbook(OqlBuilder query) {
		List<FinanceItem> items = findItem(query);

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");

		String[] titles = new String[]{"学号", "姓名", "专业类别", "总学费", "实收金额", "减免金额"};
		HSSFRow titleRow = sheet.createRow(0);
		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		for (int i = 0; i < titles.length; i++) {
			String title = titles[i];
			HSSFCell cell = titleRow.createCell(i);
			cell.setCellValue(title);
			cell.setCellStyle(cellStyle);
		}
		for (int i = 0; i < items.size(); i++) {
			String title = items.get(i).getName();
			HSSFCell cell = titleRow.createCell(i + titles.length);
			cell.setCellValue(title);
		}

		int page = 1, rowNum = 1;
		while (true) {
			query.limit(page++, 100);
			List<FinanceStudent> financeStudents = entityDao.search(query);
			if (financeStudents.isEmpty()) {
				break;
			}
			for (FinanceStudent financeStudent : financeStudents) {
				HSSFRow row = sheet.createRow(rowNum++);
				int colNum = 0;
				row.createCell(colNum++).setCellValue(financeStudent.getStudent().getCode());
				row.createCell(colNum++).setCellValue(financeStudent.getStudent().getName());
				Major major = financeStudent.getStudent().getMajor();
				row.createCell(colNum++).setCellValue(major == null || major.getType() == null ? "" : major.getType().getName());
				row.createCell(colNum++).setCellValue(financeStudent.getFeeTotal());
				row.createCell(colNum++).setCellValue(financeStudent.getFeePaid());
				row.createCell(colNum++).setCellValue(financeStudent.getGreenMoney());
				for (FinanceItem item : items) {
					row.createCell(colNum++).setCellValue(getMoney(item, financeStudent));
				}
				for (int i = 0; i < row.getLastCellNum(); i++) {
					row.getCell(i).setCellStyle(cellStyle);
				}
			}
		}

		return wb;
	}

	private double getMoney(FinanceItem item, FinanceStudent financeStudent) {
		for (FinanceStudentItem sitem : financeStudent.getItems()) {
			if (sitem.getItem().equals(item)) {
				return sitem.getFeeTotal();
			}
		}
		return 0;
	}

	private List<FinanceItem> findItem(OqlBuilder sourceQuery) {
		try {
			OqlBuilder query = OqlBuilder.from(FinanceStudent.class);
			BeanUtils.copyProperties(query, sourceQuery);
			query.limit(null);
			query.join("financeStudent.items", "sfitem");
			query.select("distinct sfitem.item");
			query.orderBy("sfitem.item.code");
			return entityDao.search(query);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
