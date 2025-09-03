package com.yzsoft.yxxt.msg.service.impl;

import com.yzsoft.yxxt.msg.model.Message;
import com.yzsoft.yxxt.msg.model.MessageGroup;
import com.yzsoft.yxxt.msg.model.MessageTemp;
import com.yzsoft.yxxt.msg.service.MessageService;
import com.yzsoft.yxxt.msg.vo.MessageContactVO;
import org.apache.commons.beanutils.BeanUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.ems.security.model.UserBean;
import org.beangle.model.persist.hibernate.EntityDaoSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.*;

@Service
public class MessageServiceImpl extends EntityDaoSupport implements MessageService {

	@Override
	public Message send(Long fromUserId, Long toUserId, String content) {
		Assert.notNull(fromUserId, "发信人ID不能为空");
		Assert.notNull(toUserId, "收信人ID不能为空");
		Assert.notNull(content, "内容不能为空");
		Assert.isTrue(content.length() < 100, "内容不能超过100字");
		Message message = new Message();
		message.setFrom(new UserBean(fromUserId));
		message.setTo(new UserBean(toUserId));
		message.setContent(content);
		message.setCreateTime(new Date());
		MessageTemp messageTemp = new MessageTemp();
		try {
			BeanUtils.copyProperties(messageTemp, message);
		} catch (Exception e) {
		}
		messageTemp.setId(null);
		MessageGroup messageGroup = getMessageGroup(fromUserId, toUserId);
		messageGroup.setUpdateTime(new Date());
		entityDao.saveOrUpdate(message, messageTemp, messageGroup);
		return message;
	}

	private MessageGroup getMessageGroup(Long fromId, Long toId) {
		OqlBuilder query = OqlBuilder.from(MessageGroup.class, "o");
		query.where("(o.from.id = :fromId and o.to.id = :toId) or (o.from.id = :toId and o.to.id = :fromId)", fromId, toId);
		List<MessageGroup> messageGroups = entityDao.search(query);
		if (messageGroups.isEmpty()) {
			MessageGroup messageGroup = new MessageGroup();
			messageGroup.setFrom(new UserBean(fromId));
			messageGroup.setTo(new UserBean(toId));
			return messageGroup;
		}
		if (messageGroups.size() > 1) {
			MessageGroup messageGroup = messageGroups.remove(0);
			entityDao.remove(messageGroups);
			return messageGroup;
		}
		return messageGroups.get(0);
	}

	@Override
	public List<MessageContactVO> findMessageContactVO(Long userId, PageLimit pageLimit) {
		OqlBuilder query = OqlBuilder.from(MessageGroup.class, "o");
		query.where("o.to.id = :userId or o.from.id = :userId", userId);
		query.orderBy("o.updateTime desc");
		query.limit(pageLimit);
		List<MessageGroup> messageGroups = entityDao.search(query);
		List<MessageContactVO> messageContactVOS = new ArrayList<MessageContactVO>();
		List<Student> students = findStudent(messageGroups);
		for (MessageGroup messageGroup : messageGroups) {
			MessageContactVO messageContactVO = new MessageContactVO();
			Long friendId = messageGroup.getFrom().getId().equals(userId) ? messageGroup.getTo().getId() :
					messageGroup.getFrom().getId();
			Student student = getStudentByMessageGroup(students, friendId);
			if (student != null) {
				messageContactVO.setUserId(friendId);
				messageContactVO.setName(student.getName());
				messageContactVO.setGender(student.getGender().getName());
				messageContactVO.setMajor(student.getMajor().getName());
				messageContactVO.setAdminClass(student.getAdminClass().getName());
				messageContactVOS.add(messageContactVO);
			}
		}
		return new SinglePage<MessageContactVO>(pageLimit.getPageNo(),
				pageLimit.getPageSize(),
				((SinglePage) messageGroups).getTotal(),
				messageContactVOS);
	}

	private List<Student> findStudent(List<MessageGroup> messageGroups) {
		if (messageGroups.isEmpty()) {
			return new ArrayList<Student>();
		}
		Set<Long> userIds = new HashSet<Long>();
		for (MessageGroup messageGroup : messageGroups) {
			userIds.add(messageGroup.getFrom().getId());
			userIds.add(messageGroup.getTo().getId());
		}
		OqlBuilder query = OqlBuilder.from(Student.class, "o");
		query.where("o.user.id in (:userIds)", userIds);
		return entityDao.search(query);
	}

	private Student getStudentByMessageGroup(List<Student> students, Long friendId) {
		for (Student student : students) {
			if (student.getUser().getId().equals(friendId)) {
				return student;
			}
		}
		return null;
	}

	@Override
	public List<MessageTemp> findMessageTemp(Long toId, Long fromId) {
		Assert.notNull(toId, "用户ID不能为空");
		Assert.notNull(fromId, "好友ID不能为空");
		OqlBuilder query = OqlBuilder.from(MessageTemp.class, "o");
		query.where("o.to.id = :userId", toId);
		query.where("o.from.id = :fromId", fromId);
		query.orderBy("o.createTime");
		List<MessageTemp> messageTemps = entityDao.search(query);
		entityDao.remove(messageTemps);
		return messageTemps;
	}

	@Override
	public List<Message> findMessage(Long userId, Long friendId, PageLimit pageLimit) {
		Assert.notNull(userId, "用户ID不能为空");
		Assert.notNull(friendId, "好友ID不能为空");
		OqlBuilder query = OqlBuilder.from(Message.class, "o");
		query.where("(o.to.id = :toId and o.from.id = :fromId) or (o.to.id = :fromId and o.from.id = :toId)",
				userId, friendId);
		query.orderBy("o.createTime desc");
		query.limit(pageLimit);
		List<Message> messages = entityDao.search(query);
		return messages;
	}
}
