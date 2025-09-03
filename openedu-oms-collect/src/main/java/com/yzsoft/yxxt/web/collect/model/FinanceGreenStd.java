package com.yzsoft.yxxt.web.collect.model;

import com.yzsoft.yxxt.finance.model.FinanceGreen;
import org.beangle.product.core.model.StudentObject;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class FinanceGreenStd extends StudentObject {

	private Boolean handle = false;

	@ManyToMany
	@JoinTable(name = "YXW_FINANCE_GREEN_STD_ITEMS")
	private List<FinanceGreen> financeGreens = new ArrayList<FinanceGreen>();

	public Boolean getHandle() {
		return handle;
	}

	public void setHandle(Boolean handle) {
		this.handle = handle;
	}

	public List<FinanceGreen> getFinanceGreens() {
		return financeGreens;
	}

	public void setFinanceGreens(List<FinanceGreen> financeGreens) {
		this.financeGreens = financeGreens;
	}

	public String getHandleStr(){
		return Boolean.TRUE.equals(handle) ? "是" : "否";
	}
	public String getItems(){
		if(financeGreens == null || financeGreens.isEmpty()){
			return null;
		}
		StringBuilder sb = new StringBuilder();
		for(FinanceGreen financeGreen : financeGreens){
			if(sb.length() > 0){
				sb.append("、");
			}
			sb.append(financeGreen.getName());
		}
		return sb.toString();
	}
}
