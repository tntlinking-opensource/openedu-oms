package org.beangle.product.restriction.action;

import java.util.List;

import javax.annotation.Resource;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.action.BaseProductAction;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.model.Teacher;
import org.beangle.product.core.service.TeacherService;
import org.beangle.product.restriction.help.RestrictionHelper;
import org.beangle.product.restriction.util.DataRealmLimit;
import org.beangle.website.common.action.BaseCommonAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.opensymphony.xwork2.ActionContext;

/**
 * 数据级权限支持类
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2017年8月11日 下午3:41:12
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RestrictionSupportAction extends BaseProductAction {

	public static int hasCollege = 1;

	public static int hasAdminDepart = 2;

	@Resource
	protected RestrictionHelper restrictionHelper;
	
	@Resource
	protected TeacherService teacherService;

	/**
	 * 加在数据权限
	 */
	public void setDataRealm(int realmScope) {
		restrictionHelper.setDataRealm(realmScope);
	}
	
	@SuppressWarnings("unchecked")
	protected List<Department> getTeachDeparts() {
		return restrictionHelper.getTeachDeparts();
	}

	@SuppressWarnings("unchecked")
	protected List<Department> getDeparts() {
		List<Department> departments = restrictionHelper.getDeparts();
		if(departments.isEmpty()) {
			Teacher teacher = this.getTeacher();
			if(null!=teacher&&null!=teacher.getDepartment()) {
				departments.add(teacher.getDepartment());
			}
		}
		return departments;
	}

	@SuppressWarnings("unchecked")
	protected List<Department> getAdminDeparts() {
		return restrictionHelper.getAdminDeparts();
	}

	/**
	 * 查询部门类别权限<br>
	 * 首先查询session中是否已经存储了于moduleName对应的DataRealm<br>
	 * 如果没有查询到，则查询数据库，等放入session后，在返回使用者<br>
	 * 如果查询的部门权限为空,则抛出部门权限异常<br>
	 * 
	 * @param
	 * @param moduleDefine
	 * @return
	 */
	protected String getDepartmentIdSeq() {
		return restrictionHelper.getDepartmentIdSeq();
	}

	/**
	 * 获得数据权限
	 * 
	 * @deprecated 返回的东西里面只有院系、学生类别、分页信息。
	 *             已经不能满足现在的按照项目、院系、学生类别的数据级权限的要求
	 * @return
	 */
	@Deprecated
	protected DataRealmLimit getDataRealmLimit() {
		DataRealmLimit limit = new DataRealmLimit();
		limit.getDataRealm().setDepartmentIdSeq(getDepartmentIdSeq());
		limit.setPageLimit(getPageLimit());
		return limit;
	}

	@SuppressWarnings("rawtypes")
	protected List getDataRealms() {
		return restrictionHelper.getDataRealms();
	}

	public void setRestrictionHelper(RestrictionHelper dataRealmHelper) {
		this.restrictionHelper = dataRealmHelper;
	}
	
	@Override
	protected OqlBuilder<?> getOqlBuilder() {
		OqlBuilder<?> query = super.getOqlBuilder();
		putDepartmentQuery(query);
		return query;
	}
	
	protected OqlBuilder<?> putDepartmentQuery(OqlBuilder<?> query) {
		List<?> departments = getDeparts();
		if(departments.size()>0){
			query.where(getShortName()+".teacher.department in(:departments)",departments);
		}else{
			query.where("1=2");
		}
		return query;
	}
	
	@SuppressWarnings("static-access")
	protected Teacher getTeacher(){
		Teacher teacher = (Teacher) ActionContext.getContext().getSession().get("teacher");
		if(null==teacher){
			teacher = teacherService.getTeacherByCode(getUsername());
			if(null!=teacher&&teacher.getDepartment()!=null) {
				System.out.println(teacher.getDepartment().getName());
			}
			ActionContext.getContext().getSession().put("teacher", teacher);
		}
//		Teacher teacher = teacherService.getTeacherByCode(getUsername());
		return teacher;
	}
}
