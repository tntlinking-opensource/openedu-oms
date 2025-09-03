package com.yzsoft.yxxt.statistics.dto;

public class ItemLogStatusCountItem extends ItemLogStatusCountParent {
	private long enabledMaleNum;
	private long enabledFemaleNum;
	private long enabledNum;
	private long disabledMaleNum;
	private long disabledFemaleNum;
	private long disabledNum;

	public long getEnabledNum() {
		return enabledNum;
	}

	public void setEnabledNum(long enabledNum) {
		this.enabledNum = enabledNum;
	}

	public long getEnabledMaleNum() {
		return enabledMaleNum;
	}

	public void setEnabledMaleNum(long enabledMaleNum) {
		this.enabledMaleNum = enabledMaleNum;
	}

	public long getEnabledFemaleNum() {
		return enabledFemaleNum;
	}

	public void setEnabledFemaleNum(long enabledFemaleNum) {
		this.enabledFemaleNum = enabledFemaleNum;
	}

	public long getDisabledNum() {
		return disabledNum;
	}

	public void setDisabledNum(long disabledNum) {
		this.disabledNum = disabledNum;
	}

	public long getDisabledMaleNum() {
		return disabledMaleNum;
	}

	public void setDisabledMaleNum(long disabledMaleNum) {
		this.disabledMaleNum = disabledMaleNum;
	}

	public long getDisabledFemaleNum() {
		return disabledFemaleNum;
	}

	public void setDisabledFemaleNum(long disabledFemaleNum) {
		this.disabledFemaleNum = disabledFemaleNum;
	}
}
