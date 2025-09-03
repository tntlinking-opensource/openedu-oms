package com.yzsoft.yxxt.welcome.process.action;

import org.beangle.struts2.action.BaseAction;
import org.springframework.stereotype.Controller;

@Controller
public class ErrorStudentNotFountdAction extends BaseAction{

	public String index(){
		put("code", get("code"));
		return forward();
	}

}
