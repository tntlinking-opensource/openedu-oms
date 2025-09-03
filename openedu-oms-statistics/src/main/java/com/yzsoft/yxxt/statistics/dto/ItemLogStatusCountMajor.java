package com.yzsoft.yxxt.statistics.dto;

import java.util.ArrayList;
import java.util.List;

public class ItemLogStatusCountMajor extends ItemLogStatusCountParent {

	private long maleNum;
	private long femaleNum;

	private List<ItemLogStatusCountItem> items = new ArrayList<ItemLogStatusCountItem>();

	public List<ItemLogStatusCountItem> getItems() {
		return items;
	}

	public void setItems(List<ItemLogStatusCountItem> items) {
		this.items = items;
	}

	public long getMaleNum() {
		return maleNum;
	}

	public void setMaleNum(long maleNum) {
		this.maleNum = maleNum;
	}

	public long getFemaleNum() {
		return femaleNum;
	}

	public void setFemaleNum(long femaleNum) {
		this.femaleNum = femaleNum;
	}
}
