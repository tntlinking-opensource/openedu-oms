package com.yzsoft.yxxt.web.ask.action;

import com.yzsoft.yxxt.web.ask.model.MsgGroup;
import com.yzsoft.yxxt.web.ask.model.MsgGroupMsg;
import com.yzsoft.yxxt.web.ask.service.AskService;
import org.beangle.ems.security.model.SystemConfig;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StuClassAction extends SecurityActionSupport {

	@Resource
	private AskService askService;

	public String index() {
		MsgGroup msgGroup = askService.findStuClassGroup(getUserId());
		put("msgGroup", msgGroup);
		setToken();
		put("page_time", System.currentTimeMillis());
		return forward();
	}

	public String history() {
		Long msgGroupId = getLong("msgGroupId");
		Long lastMsgId = getLong("lastMsgId");
		List<MsgGroupMsg> msgs = askService.findMsgGroupHistoryMsg(msgGroupId, lastMsgId);
		put("msgs", msgs);
		put("userId", getUserId());
		return forward();
	}

	public String search() {
		Long msgGroupId = getLong("msgGroupId");
		Long lastMsgId = getLong("lastMsgId");
		Long start = System.currentTimeMillis();
		List<MsgGroupMsg> msgs = null;
//		while (System.currentTimeMillis() - start < 60000) {
//			msgs = askService.findMsgGroupMsg(msgGroupId, getUserId(), lastMsgId);
//			if (!msgs.isEmpty()) {
//				break;
//			} else {
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//				}
//			}
//		}
		msgs = askService.findMsgGroupMsg(msgGroupId, getUserId(), lastMsgId);
		put("msgs", msgs);
		put("userId", getUserId());
		return forward();
	}

	public String edit() {
		return forward();
	}

	public String save() {
		if (!checkToken()) {
			return redirect("index");
		}
		MsgGroup msgGroup = askService.findStuClassGroup(getUserId());
		String content = get("content");
		askService.sendGroupMsg(getUserId(), msgGroup.getId(), content);
		return null;
	}

}
