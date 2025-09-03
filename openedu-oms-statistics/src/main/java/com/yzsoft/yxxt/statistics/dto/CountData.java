package com.yzsoft.yxxt.statistics.dto;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.ComparatorUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountData {

	private List<String> years;
	private List<String> names;
	private List<CountItem> items;

	public CountData(List<CountItem> items) {
		if (items != null && !items.isEmpty()) {
			Collections.sort(items,
					ComparatorUtils.reversedComparator(new BeanComparator("num")));
		}
		if (items != null && !items.isEmpty() && items.get(0).getYear() != null) {
			years = new ArrayList<String>();
			names = new ArrayList<String>();
			for (CountItem item : items) {
				add(years, item.getYear());
				add(names, item.getName());
			}
			Collections.sort(years);
			Collections.sort(names);
		}
		setItems(items);
	}

	public static void add(List<String> names, String name) {
		if (names.indexOf(name) < 0) {
			names.add(name);
		}
	}

	public List<String> getYears() {
		return years;
	}

	public void setYears(List<String> years) {
		this.years = years;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<CountItem> getItems() {
		return items;
	}

	public void setItems(List<CountItem> items) {
		this.items = items;
	}
}


