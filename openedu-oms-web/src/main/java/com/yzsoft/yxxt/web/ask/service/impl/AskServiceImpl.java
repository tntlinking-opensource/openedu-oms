package com.yzsoft.yxxt.web.ask.service.impl;

import com.yzsoft.yxxt.web.ask.model.*;
import com.yzsoft.yxxt.web.ask.service.AskService;
import org.apache.commons.lang3.StringUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.UserBean;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class AskServiceImpl implements AskService {

	@Resource
	private EntityDao entityDao;
	@Resource
	private StudentService studentService;
	@Resource
	private TeacherService teacherService;

	@Override
	public List<AskPlate> findPlate() {
		OqlBuilder query = OqlBuilder.from(AskPlate.class);
		query.where("enabled = true");
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}

	@Override
	public List<AskCommon> findAskCommon(String key, PageLimit limit) {
		OqlBuilder query = OqlBuilder.from(AskCommon.class);
		if (StringUtils.isNotEmpty(key)) {
			query.where("content like :key or replyContent like :key", "%" + key + "%");
		}
        query.where("enabled = true");
        query.orderBy("top desc, sort");
		query.cacheable();
		query.limit(limit);
		return entityDao.search(query);
	}

	@Override
	public MsgGroup findStuClassGroup(Long userId) {
		User user = entityDao.get(User.class, userId);
		Student student = studentService.getStudentByCode(user.getName());
		if (student == null || student.getAdminClass() == null) {
			return null;
		}
		String code = student.getAdminClass().getCode();
		MsgGroup msgGroup = getGroup(code);
		if (msgGroup == null) {
			msgGroup = new MsgGroup();
			msgGroup.setName(student.getAdminClass().getName());
			msgGroup.setCode(code);
			msgGroup.setType(MsgGroup.TYPE_CLASS);
			entityDao.saveOrUpdate(msgGroup);
		}
		MsgGroupMember member = getMember(msgGroup.getId(), userId);
		if (member == null) {
			member = new MsgGroupMember();
			member.setGroup(msgGroup);
			member.setUser(user);
			member.setLastTime(new Date());
			entityDao.saveOrUpdate(member);
		}
		return msgGroup;
	}

	@Override
	public List<MsgGroupMsg> findMsgGroupHistoryMsg(Long msgGroupId, Long lastMsgId) {
		PageLimit limit = new PageLimit(1, 10);
		if (lastMsgId == null) {
			limit.setPageSize(5);
		}
		OqlBuilder query = OqlBuilder.from(MsgGroupMsg.class, "o");
		query.where("o.group.id = :groupId", msgGroupId);
		if (lastMsgId != null) {
			query.where("o.id < :lastMsgId", lastMsgId);
		}
		query.orderBy("o.id desc");
		query.limit(limit);
		List<MsgGroupMsg> list = entityDao.search(query);
		Collections.reverse(list);
		return list;
	}

	@Override
	public List<MsgGroupMsg> findMsgGroupMsg(Long groupId, Long userId, Long lastMsgId) {
		MsgGroupMember member = getMember(groupId, userId);
		Assert.notNull(groupId);
		PageLimit limit = new PageLimit(1, 20);
		OqlBuilder query = OqlBuilder.from(MsgGroupMsg.class, "o");
		query.where("o.group.id = :groupId", groupId);
		if (lastMsgId == null) {
			if (member.getLastTime() != null) {
				query.where("o.createTime >= :lastTime", member.getLastTime());
			}
		} else {
			query.where("o.id > :lastMsgId", lastMsgId);
		}
		query.orderBy("o.id");
		query.limit(limit);
		List<MsgGroupMsg> list = entityDao.search(query);
		if (!list.isEmpty()) {
			member.setLastTime(new Date());
			entityDao.saveOrUpdate(member);
		}
		return list;
	}

	@Override
	public void sendGroupMsg(Long userId, Long msgGroupId, String content) {
		MsgGroupMsg msg = new MsgGroupMsg();
		msg.setGroup(new MsgGroup(msgGroupId));
		msg.setUser(new UserBean(userId));
		msg.setContent(content);
		msg.setCreateTime(new Date());
		entityDao.saveOrUpdate(msg);
	}

	private MsgGroupMember getMember(Long groupId, Long userId) {
		OqlBuilder query = OqlBuilder.from(MsgGroupMember.class, "o");
		query.where("o.group.id = :groupId", groupId);
		query.where("o.user.id = :userId", userId);
		List<MsgGroupMember> list = entityDao.search(query);
		return list.isEmpty() ? null : list.get(0);
	}

	private MsgGroup getGroup(String code) {
		OqlBuilder query = OqlBuilder.from(MsgGroup.class);
		query.where("code = :code", code);
		query.cacheable();
		List<MsgGroup> list = entityDao.search(query);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public List<AskStudent> findAsk(User user, String key, PageLimit limit) {

		Department department = null;
		if (user != null) {
			Student student = studentService.getStudentByCode(user.getName());
			if (student != null) {
				department = student.getDepartment();
			} else {
				Teacher teacher = teacherService.getTeacherByCode(user.getName());
				if (teacher != null) {
					department = teacher.getDepartment();
				}
			}
		}

		OqlBuilder query = OqlBuilder.from(AskStudent.class);
		query.where("replyContent is not null");
		query.where("scope = 2 and department = :department", department);
//		query.where("reply = true");
//		query.where("hidden = false");
//		if (plateId != null) {
//			query.where("plate.id = :plateId", plateId);
//		}
		if (StringUtils.isNotEmpty(key)) {
			query.where("content like :content", "%" + key + "%");
		}
		query.orderBy("replyTime desc");
		query.cacheable();
		query.limit(limit);
		return entityDao.search(query);
	}

	@Override
	public List<AskStudent> findAskByStudentId(Long studentId, String key, PageLimit limit) {
		OqlBuilder query = OqlBuilder.from(AskStudent.class);
		query.where("student.id = :studentId", studentId);
		if (StringUtils.isNotEmpty(key)) {
			query.where("content like :content", "%" + key + "%");
		}
		query.orderBy("createTime desc");
		query.cacheable();
		query.limit(limit);
		return entityDao.search(query);
	}

	@Override
	public void moveMsg(Long askPlateSourceId, Long askPlateTargetId) {
		if (askPlateSourceId == null || askPlateTargetId == null || askPlateSourceId.equals(askPlateTargetId)) {
			return;
		}
		String hql = "update " + AskStudent.class.getName() + " set plate.id = ? where plate.id = ?";
		entityDao.executeUpdateHql(hql, askPlateTargetId, askPlateSourceId);
	}
}
