package com.yzsoft.yxxt.web.view;

import com.opensymphony.xwork2.util.ValueStack;
import com.yzsoft.yxxt.web.model.Column;
import com.yzsoft.yxxt.web.service.ColumnService;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.struts2.helper.Params;
import org.beangle.struts2.view.component.ClosingUIBean;


public class ColumnLeft extends ClosingUIBean {

	private Long columnId;

	public ColumnLeft(ValueStack stack) {
		super(stack);
	}

	@Override
	protected void evaluateParams() {
		super.evaluateParams();
		if (columnId == null) {
			columnId = Params.getLong("columnId");
		}
		EntityDao entityDao = getBean(EntityDao.class, "entityDao");
		Column column = entityDao.get(Column.class, columnId);
		if (column.getParent() != null) {
			columnId = column.getParent().getId();
			put("column", column);
		}
		ColumnService columnService = getBean(ColumnService.class);
		put("leftColumns", columnService.findColumn(columnId));
	}

	public Long getColumnId() {
		return columnId;
	}

	public void setColumnId(Long columnId) {
		this.columnId = columnId;
	}
}
