package org.beangle.product.core.service.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.model.GroupMemberBean;
import org.beangle.ems.security.model.UserBean;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Student;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.BspBaseService;
import org.beangle.product.core.service.StudentService;
import org.beangle.product.core.service.TeacherService;
import org.beangle.security.codec.EncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基础服务接口实现
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2015年4月8日 下午1:57:38
 */
@Service
public class BspBaseServiceImpl extends BaseServiceImpl implements BspBaseService{

	@Autowired
	protected StudentService studentService;
	
	@Autowired
	protected TeacherService teacherService;
	
	@Autowired
	protected UserService userService;
	
	public Department getDepartmentByUserName(String userName) {
		Teacher teacher = teacherService.getTeacherByCode(userName);
		if(null!=teacher){
			return teacher.getDepartment();
		}else{
			Student std = studentService.getStudentByCode(userName);
			if(null!=std){
				return std.getDepartment();
			}
		}
		return null;
	}
	
	public String getDepartmentNameByUserName(String userName) {
		Department department = getDepartmentByUserName(userName);
		if(department != null){
			return department.getName();
		}
		return null;
	}
	
	/**
	 * 创建用户（判断用户是否存在，不存在创建用户）
	 * @param code 帐号
	 * @param name 姓名
	 * @param creator 创建人
	 * @param categoryId  身份
	 * @param groupName 用户组
	 * @return 用户
	 * @author 周建明
	 * @createDate 2015年4月9日 上午11:26:13
	 */
	public User createUser(String code,String name,User creator,Long categoryId,String groupName){
		//创建用户
		User user = userService.get(code);
		user = createNewUser(user, code, name, creator, categoryId, groupName);
		return user;
	}
	
	
	/**
	 * 同步创建用户
	 * @param user 用户
	 * @param code 帐号
	 * @param name 姓名
	 * @param creator 创建人
	 * @param categoryId  身份
	 * @param groupName 用户组
	 * @return 用户
	 * @author 周建明
	 * @createDate 2015年4月9日 上午11:26:13
	 */
	public User createNewUser(User user,String code,String name,User creator,Long categoryId,String groupName){
		//创建用户
		if(user == null){
			user = new UserBean();
			user.setName(code);
			user.setFullname(name);
			user.setEnabled(true);
			
			//设置身份
			Category c = entityDao.get(Category.class, categoryId);
			user.setDefaultCategory(c);
			user.getCategories().add(c);
			
			//不可为空
			user.setEffectiveAt(new Date());
			user.setEnabled(true);
			user.setCreatedAt(new Date());
			user.setUpdatedAt(new Date());
			user.setCreator(creator);
			user.setPassword(EncryptUtil.encode(StringUtils.substring(code, -6).toUpperCase()));//设置默认密码
			entityDao.saveOrUpdate(user);
			setUserGroupByGroupName(user, groupName);//设置用户组
		}else{
			entityDao.executeUpdateHql("update " + User.class.getName() + " set enabled = true where id = ?", user.getId());
		}
		return user;
	}
	
	
	public void setUserGroupByGroupName(User user, String groupName) {
		if(!isdqGroupByGroupName(user, groupName)){
			Group group = entityDao.getEntity(Group.class, "name", groupName);
			if(group!=null){
				GroupMember gm = new GroupMemberBean(group, user, GroupMember.Ship.MEMBER);
				user.getGroups().add(gm);
				entityDao.saveOrUpdate(user);
			}
		}
	}
	
	public boolean isdqGroupByGroupName(User user,String groupName) {
		if(user!=null && StringUtils.isNotBlank(groupName)){
			for(GroupMember gm : user.getGroups()){
				if(gm.getGroup().getName().equals(groupName) && gm.isMember()){
					return true;
				}
			}
		}
		return false;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public void setTeacherService(TeacherService teacherService) {
		this.teacherService = teacherService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
