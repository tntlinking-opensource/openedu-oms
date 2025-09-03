package com.yzsoft.yxxt.finance.action;

import com.yzsoft.yxxt.core.service.YxxtService;
import com.yzsoft.yxxt.finance.importer.FinanceStatusImporter;
import com.yzsoft.yxxt.finance.model.FinanceStudent;
import com.yzsoft.yxxt.finance.service.FinanceStatusExportDetailService;
import com.yzsoft.yxxt.finance.service.FinanceStudentService;
import org.apache.commons.collections.map.LinkedMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class FinanceStatusAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;
	@Resource
	private FinanceStudentService financeStudentService;
	@Resource
	private FinanceStatusExportDetailService financeStatusExportDetailService;

	@Override
	protected String getEntityName() {
		return FinanceStudent.class.getName();
	}

	@Override
	protected void indexSetting() {
		put("year", yxxtService.getYear());
		super.indexSetting();
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder query = (OqlBuilder) super.getQueryBuilder();
		Boolean finished = getBoolean("finished");
		if (finished != null) {
			if (finished) {
				query.where("feeOdd = 0");
			} else {
				query.where("feeOdd > 0");
			}
		}
		put("userid", SecurityUtils.getUserId());
		return query;
	}

	public String create(){
		financeStudentService.create();
		return redirect("search", "生成成功！");
	}

	@Override
	protected Map<String, String> getPropMap() {
		Map<String, String> map = new LinkedMap();
		map.put("学号", "student.code");
		map.put("身份证号", "student.cardcode");
		map.put("姓名", "student.name");
		map.put("院系", "student.major.department.name");
		map.put("专业", "student.major.name");
		map.put("班级", "student.adminClass.name");
		map.put("应缴金额", "feeTotal");
		map.put("已缴金额", "feePaid");
		map.put("未缴金额", "feeOdd");
		map.put("减免金额", "greenMoney");
		map.put("备注", "remark");
		return map;
	}

	@Override
	public ItemImporterListener getImporterListener() {
		FinanceStatusImporter importListener = new FinanceStatusImporter();
		return importListener;
	}

	public void exportDetail() {
		OqlBuilder query = getOqlBuilder();
		HSSFWorkbook workbook = financeStatusExportDetailService.getWorkbook(query);
		try {
			HttpServletResponse response = getResponse();
			response.setContentType("application/x-msdownload");
			String fileName = "财务缴费清单.xls";
			String userAgent = getRequest().getHeader("User-Agent");
			if (userAgent.indexOf("Safari") < 0) {
				response.setHeader("Content-disposition", "attachment; filename*=utf-8''" + URLEncoder.encode(fileName, "UTF-8") + "");
			} else {
				fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
				response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);
			}
			OutputStream os = getResponse().getOutputStream();
			workbook.write(os);
			os.close();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
