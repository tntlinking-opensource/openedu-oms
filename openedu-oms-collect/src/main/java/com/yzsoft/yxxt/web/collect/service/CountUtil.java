package com.yzsoft.yxxt.web.collect.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.beangle.model.query.builder.SqlBuilder;
import org.beangle.struts2.helper.Params;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountUtil {

	public static String where(SqlBuilder query) {
		Long campusId = Params.getLong("campusId");
		Long educationId = Params.getLong("educationId");
		Long departmentId = Params.getLong("departmentId");
		return where(query, campusId, educationId, departmentId);
	}

	public static String where(SqlBuilder query, Long campusId, Long educationId, Long departmentId) {
		String title = null;
		if (departmentId != null) {
			title = "院系";
			query.join("CP_DEPARTMENTS cc", "on s.department_id = cc.id");
		} else if (educationId != null) {
			title = "学历层次";
			query.join("SYS_DICT_DATAS cc", "on s.education_id = cc.id");
		} else if (campusId != null) {
			title = "校区";
			query.join("CP_CAMPUSES cc", "on s.campus_id = cc.id");
		} else {
			title = "校区";
			query.join("CP_CAMPUSES cc", "on s.campus_id = cc.id");
		}
		whereCondition(query, campusId, educationId, departmentId);
		return title;
	}

	public static void whereCondition(SqlBuilder query) {
		Long campusId = Params.getLong("campusId");
		Long educationId = Params.getLong("educationId");
		Long departmentId = Params.getLong("departmentId");
		whereCondition(query, campusId, educationId, departmentId);
	}

	private static void whereCondition(SqlBuilder query, Long campusId, Long educationId, Long departmentId) {
		if (departmentId != null) {
			query.where("s.department_id = :departmentId", departmentId);
		} else if (educationId != null) {
			query.where("s.education_id = :educationId", educationId);
		} else if (campusId != null) {
			if (campusId != 0) {
				query.where("s.campus_id = :campusId", campusId);
			}
		} else {
//			query.join("CP_CAMPUSES cc", "on s.campus_id = cc.id");
		}
	}

	public static List<List> convert(List<Object[]> datas, String title) {
		List<String> rowNames = new ArrayList<String>();
		List<String> colNames = new ArrayList<String>();
		for (Object[] data : datas) {
			if (colNames.indexOf(data[1]) < 0) {
				colNames.add((String) data[1]);
			}
			if (rowNames.indexOf(data[0]) < 0) {
				rowNames.add((String) data[0]);
			}
		}
		Collections.sort(rowNames);
		Collections.sort(colNames);
		List<List> newDatas = new ArrayList<List>();
		for (int i = -1; i < rowNames.size(); i++) {
			List row = new ArrayList();
			if (i == -1) {
				row.add(title);
				for (String name : colNames) {
					row.add(name);
				}
			} else {
				String rowName = rowNames.get(i);
				row.add(rowName);

				for (String colName : colNames) {
					Object o = 0;
					for (Object[] data : datas) {
						if (rowName.equals(data[0]) && colName.equals(data[1])) {
							o = data[2];
							break;
						}
					}
					row.add(o);
				}
			}
			newDatas.add(row);
		}
		return newDatas;
	}

	public static void output(HSSFWorkbook wb, String fileName) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType("application/x-msdownload");
			String userAgent = ServletActionContext.getRequest().getHeader("User-Agent");
			if (userAgent.indexOf("Safari") < 0) {
				response.setHeader("Content-disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8") + "");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
				response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			}
			OutputStream os = response.getOutputStream();
			wb.write(os);
			os.close();
		} catch (Exception e) {
		}
	}

	public static void exportExcel(String title) {
		HttpServletRequest request = ServletActionContext.getRequest();
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet1");
		int rowNum = 0;

		sheet.createRow(rowNum++).createCell(0).setCellValue(title);

		List<List> datas = (List<List>) request.getAttribute("datas");

		if (datas.size() > 0) {
			List<String> ths = datas.get(0);
			HSSFRow row = sheet.createRow(rowNum++);
			for (int i = 0; i < ths.size(); i++) {
				String th = ths.get(i);
				row.createCell(i).setCellValue(th);
			}
		}
		if (datas.size() > 1) {
			for (int i = 1; i < datas.size(); i++) {
				List<Object> oo = datas.get(i);
				HSSFRow row = sheet.createRow(rowNum++);
				for (int j = 0; j < oo.size(); j++) {
					Object o = oo.get(j);
					if (o == null) {
						row.createCell(j).setCellValue(0);
					} else if (o instanceof Number) {
						row.createCell(j).setCellValue(((Number) o).longValue());
					} else {
						row.createCell(j).setCellValue(o.toString());
					}
				}
			}
		}
		output(wb, "export.xls");

	}
}
