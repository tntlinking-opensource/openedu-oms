package com.yzsoft.yxxt.msg.vo;

import com.yzsoft.yxxt.msg.model.MessageBase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MessageVO {

	private Long id;
	private Long fromId;
	private Long toId;
	private String content;
	private String createTime;

	public static List<MessageVO> fromMessage(List<? extends MessageBase> messageTemps) {
		List<MessageVO> messageVOS = new ArrayList<MessageVO>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (MessageBase messageBase : messageTemps) {
			MessageVO messageVO = new MessageVO();
			messageVO.setId(messageBase.getId());
			messageVO.setFromId(messageBase.getFrom().getId());
			messageVO.setToId(messageBase.getTo().getId());
			messageVO.setContent(messageBase.getContent());
			messageVO.setCreateTime(sdf.format(messageBase.getCreateTime()));
			messageVOS.add(messageVO);
		}
		return messageVOS;
	}

	public static List<MessageVO> fromMessage(MessageBase message) {
		return fromMessage(Arrays.asList(message));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFromId() {
		return fromId;
	}

	public void setFromId(Long fromId) {
		this.fromId = fromId;
	}

	public Long getToId() {
		return toId;
	}

	public void setToId(Long toId) {
		this.toId = toId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
