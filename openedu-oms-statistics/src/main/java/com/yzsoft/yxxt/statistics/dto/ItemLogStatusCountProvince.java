package com.yzsoft.yxxt.statistics.dto;

public class ItemLogStatusCountProvince extends ItemLogStatusCountParent {
	private long num;
	private long maleNum;
	private long femaleNum;

	public long getNum() {
		return num;
	}

	public void setNum(long num) {
		this.num = num;
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
