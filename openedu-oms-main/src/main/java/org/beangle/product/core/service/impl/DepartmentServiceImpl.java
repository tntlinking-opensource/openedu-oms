package org.beangle.product.core.service.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Department;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.sync.service.SyncBaseImpl;
import org.beangle.website.system.model.DictDataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yzsoft.bpcg.ws.service.DepartmentWsService;

/**
 * 部门院系接口
 * 
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年9月13日 下午2:39:13
 */
@Service
public class DepartmentServiceImpl extends SyncBaseImpl implements DepartmentService {

	@Autowired
	private DepartmentWsService departmentWsService;
	
	public List<Department> findAllDepartment() {
		return entityDao.get(Department.class, "enabled",true);
	}

	public List<Department> findTeachingDepartment() {
		OqlBuilder<Department> query = OqlBuilder.from(Department.class, "department");
//		query.where("department.departmentType.code =:departmentTypeCode",DictDataUtils.DEPARTMENT_TYPE_YX);//查询部门类型院系的院系部门
		query.where("department.enabled is true");
		query.orderBy("code");
		query.cacheable();
		return entityDao.search(query);
	}
	
	public Department getDepartmentById(Long id) {
		return entityDao.get(Department.class, id);
	}
	
	public Department getDepartmentByCode(String code) {
		return entityDao.getEntity(Department.class, "code", code);
	}

	public List<Department> findDepartmentByCodes(String[] codes) {
		return entityDao.get(Department.class, "code", Arrays.asList(codes));
	}
	
	public void sync(HttpSession session){
		try {
			List<com.yzsoft.bpcg.ws.model.Department> list = departmentWsService.findAllDepartment();
			setSession(session);
			init(list.size());
			Map<String,Department> campusMap = getDepartmentMap();
			
			//循环学校数据
			for (int i = 0; i < list.size(); i++) {
				try {
					com.yzsoft.bpcg.ws.model.Department odepartment = list.get(i);
					Department department = campusMap.get(odepartment.getCode());
					//判断学校数据与本地数据是否重复
					if(department == null){//不重复，新建一条数据
						department = new Department();
					}
					
					Long id = department.getId();
					//保存
					try {
						copyPropertiesIgnoreNull(odepartment,department);
					} catch (Exception e) {
					}
					department.setId(id);
					//是否可用
					if(null == odepartment.getEnabled()){
						department.setEnabled(true);
					}else{
						department.setEnabled(odepartment.getEnabled());
					}
					entityDao.saveOrUpdate(department);
				} catch (Exception e) {
					System.out.println(list.get(i).getName());
					e.printStackTrace();
				}
				//用于计数
				setProgress(i+1, list.size());
			}
		} catch (Exception e) 	{		
			e.printStackTrace();
			init(0);
		}
	}
}
