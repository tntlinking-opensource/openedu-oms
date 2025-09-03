package com.yzsoft.yxxt.msg.action;

import com.yzsoft.yxxt.msg.model.Message;
import com.yzsoft.yxxt.msg.model.MessageTemp;
import com.yzsoft.yxxt.msg.service.MessageService;
import com.yzsoft.yxxt.msg.vo.MessageContactVO;
import com.yzsoft.yxxt.msg.vo.MessageVO;
import net.sf.json.JSONObject;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.struts2.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MessageAction extends BaseAction {

	@Autowired
	private MessageService messageService;

	public void send() {
		Long fromUserId = SecurityUtils.getUserId();
		Long toUserId = getLong("toUserId");
		String content = get("content");
		JSONObject json = new JSONObject();
		try {
			Message message = messageService.send(fromUserId, toUserId, content);
			json.put("data", MessageVO.fromMessage(message).get(0));
			json.put("success", "OK");
		} catch (Exception e) {
			json.put("error", e.getMessage());
			logger.error(e.getMessage(), e);
		}
		try {
			getResponse().getWriter().write(json.toString());
			getResponse().flushBuffer();
		} catch (IOException e) {
		}
	}

	public void friends() {
		PageLimit pageLimit = getPageLimit();
		Long userId = SecurityUtils.getUserId();
		JSONObject json = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			List<MessageContactVO> messageContactVOS = messageService.findMessageContactVO(userId, pageLimit);
			data.put("items", messageContactVOS);
			data.put("total", ((SinglePage) messageContactVOS).getTotal());
			json.put("data", data);
			json.put("success", "OK");
		} catch (Exception e) {
			json.put("error", e.getMessage());
		}
		try {
			getResponse().getWriter().write(json.toString());
			getResponse().flushBuffer();
		} catch (IOException e) {
		}
	}

	public void messageTemp() {
		Long userId = SecurityUtils.getUserId();
		Long friendId = getLong("friendId");
		JSONObject json = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			List<MessageTemp> messageTemps = messageService.findMessageTemp(userId, friendId);
			data.put("items", MessageVO.fromMessage(messageTemps));
			json.put("data", data);
			json.put("success", "OK");
		} catch (Exception e) {
			json.put("error", e.getMessage());
		}
		try {
			getResponse().getWriter().write(json.toString());
			getResponse().flushBuffer();
		} catch (IOException e) {
		}
	}

	public void message() {
		Long userId = SecurityUtils.getUserId();
		Long friendId = getLong("friendId");
		PageLimit pageLimit = getPageLimit();
		JSONObject json = new JSONObject();
		try {
			JSONObject data = new JSONObject();
			List<Message> messageTemps = messageService.findMessage(userId, friendId, pageLimit);
			data.put("items", MessageVO.fromMessage(messageTemps));
			json.put("data", data);
			json.put("success", "OK");
		} catch (Exception e) {
			json.put("error", e.getMessage());
		}
		try {
			getResponse().getWriter().write(json.toString());
			getResponse().flushBuffer();
		} catch (IOException e) {
		}
	}

}
