package com.yzsoft.yxxt.msg.service;

import com.yzsoft.yxxt.msg.model.Message;
import com.yzsoft.yxxt.msg.model.MessageTemp;
import com.yzsoft.yxxt.msg.vo.MessageContactVO;
import org.beangle.commons.collection.page.PageLimit;

import java.util.List;

public interface MessageService {

	Message send(Long formUserId, Long toUserId, String content);

	List<MessageContactVO> findMessageContactVO(Long userId, PageLimit pageLimit);

	List<MessageTemp> findMessageTemp(Long toId, Long fromId);

	List<Message> findMessage(Long userId, Long friendId, PageLimit pageLimit);
}
