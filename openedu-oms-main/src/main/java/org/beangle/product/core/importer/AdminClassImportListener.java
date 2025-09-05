package org.beangle.product.core.importer;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.transfer.TransferResult;
import org.beangle.model.transfer.importer.listener.ItemImporterListener;
import org.beangle.product.core.model.AdminClass;
import org.beangle.product.core.service.DepartmentService;
import org.beangle.product.core.service.MajorService;
import org.beangle.website.system.model.DictData;
import org.springframework.util.Assert;

/**
 * 班级导入监听器
 * 
 * @作者：周建明
 * @创建日期：2016年9月18日 上午10:32:04
 */
public class AdminClassImportListener extends ItemImporterListener {

	private EntityDao entityDao;
	private DepartmentService departmentService;
	private MajorService majorService;

	public AdminClassImportListener(EntityDao entityDao,DepartmentService departmentService,MajorService majorService) {
		super();
		this.entityDao = entityDao;
		this.departmentService = departmentService;
		this.majorService = majorService;
	}

	public void onItemFinish(TransferResult tr) {
		String enrollName = (String)importer.getCurData().get("enrollName");
		Assert.isTrue(StringUtils.isNotBlank(enrollName), "招生类型不能为空");
		AdminClass oAdminClass = (AdminClass) importer.getCurrent();
		AdminClass adminClass = getAdminClass(oAdminClass);
		copyPropertiesIgnoreNull(oAdminClass, adminClass);
		if(null!=oAdminClass.getDepartment()){
			Assert.isTrue(StringUtils.isNotBlank(oAdminClass.getDepartment().getCode()), "院系代码不能为空");
			adminClass.setDepartment(departmentService.getDepartmentByCode(oAdminClass.getDepartment().getCode()));
		}
		
		if(null!=oAdminClass.getMajor()){
			Assert.isTrue(StringUtils.isNotBlank(oAdminClass.getMajor().getCode()), "专业代码不能为空");
			adminClass.setMajor(majorService.getMajorByCode(oAdminClass.getMajor().getCode()));;
		}
		String[] name = enrollName.split("、");
		List<DictData> d = entityDao.get(DictData.class, "name", name);
		adminClass.getEnrolls().clear();
		adminClass.getEnrolls().addAll(d);
		saveOrUpdate(adminClass);
	}
	
	
	private AdminClass getAdminClass(AdminClass oAdminClass) {
		Assert.isTrue(StringUtils.isNotBlank(oAdminClass.getCode()), "班级代码不能为空");
		Assert.isTrue(StringUtils.isNotBlank(oAdminClass.getName()), "班级代码不能为空");
		AdminClass adminClass = entityDao.getEntity(AdminClass.class, "code", oAdminClass.getCode());
		if (adminClass == null) {
			adminClass = new AdminClass();
		}
		return adminClass;
	}
}
