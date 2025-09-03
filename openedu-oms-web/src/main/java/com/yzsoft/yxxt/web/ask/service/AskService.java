package com.yzsoft.yxxt.web.ask.service;

import com.yzsoft.yxxt.web.ask.model.*;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.User;

import java.util.List;

public interface AskService {

	List<AskPlate> findPlate();

	List<AskStudent> findAsk(User user, String key, PageLimit limit);

	void moveMsg(Long askPlateSourceId, Long askPlateTargetId);

	List<AskStudent> findAskByStudentId(Long studentId, String key, PageLimit limit);

	List<AskCommon> findAskCommon(String key, PageLimit limit);

	MsgGroup findStuClassGroup(Long userId);

	List<MsgGroupMsg> findMsgGroupHistoryMsg(Long groupId, Long lastMsgId);
	List<MsgGroupMsg> findMsgGroupMsg(Long groupId, Long userId, Long lastMsgId);

	void sendGroupMsg(Long userId, Long msgGroupId, String content);
}
