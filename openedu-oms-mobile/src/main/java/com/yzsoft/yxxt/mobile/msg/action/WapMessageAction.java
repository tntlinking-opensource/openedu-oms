package com.yzsoft.yxxt.mobile.msg.action;

import com.yzsoft.yxxt.msg.model.Message;
import com.yzsoft.yxxt.msg.service.MessageService;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class WapMessageAction extends BaseAction {

	@Autowired
	private MessageService messageService;

	public String index() {
		return forward();
	}

	public String edit() {
		Long userId = SecurityUtils.getUserId();
		Long friendId = getLong("friendId");
		User friend = entityDao.get(User.class, friendId);
		PageLimit pageLimit = getPageLimit();
		pageLimit.setPageSize(5);
		messageService.findMessageTemp(userId, friendId);
		List<Message> messages = messageService.findMessage(userId, friendId, pageLimit);
		put("friendId", friendId);
		put("friend", friend);
		put("userId", userId);
		put("messages", messages);
		return forward();
	}

}
