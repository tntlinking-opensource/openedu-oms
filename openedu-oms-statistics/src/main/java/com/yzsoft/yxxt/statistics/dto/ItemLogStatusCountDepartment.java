package com.yzsoft.yxxt.statistics.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemLogStatusCountDepartment extends ItemLogStatusCountParent {

	private List<ItemLogStatusCountMajor> majors = new ArrayList<ItemLogStatusCountMajor>();

	public List<ItemLogStatusCountMajor> getMajors() {
		return majors;
	}

	public void setMajors(List<ItemLogStatusCountMajor> majors) {
		this.majors = majors;
	}
}
