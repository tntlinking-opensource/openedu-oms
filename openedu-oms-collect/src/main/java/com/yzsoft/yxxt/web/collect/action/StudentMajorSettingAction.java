package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.prepare.model.StudentMajorConfig;
import com.yzsoft.yxxt.prepare.service.MajorPlanService;
import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import com.yzsoft.yxxt.web.collect.service.MajorInfoService;
import org.beangle.commons.lang.StrUtils;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.ems.security.User;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.product.core.model.Major;
import org.beangle.product.core.service.StudentService;
import org.beangle.struts2.action.EntityDrivenAction;
import org.beangle.website.common.service.UploadFileService;
import org.beangle.website.system.model.DictData;
import org.beangle.website.system.service.DictDataService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StudentMajorSettingAction extends EntityDrivenAction {

	@Resource
	private UploadFileService uploadFileService;
	@Resource
	private MajorInfoService majorInfoService;
	@Resource
	private MajorPlanService majorPlanService;
	@Resource
	private StudentService studentService;

	@Override
	protected String getEntityName() {
		return MajorInfo.class.getName();
	}

	@Override
	protected String getDefaultOrderString() {
		return "isTop desc, sort";
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		put("genders", majorInfoService.findGender());
//        put("enrollTypes", dictDataService.findDictData("STD_ENROLL_TYPE"));
		put("enrollTypes", studentService.findEnrollType());
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		MajorInfo majorInfo = (MajorInfo) entity;
		majorInfo.getEnrollTypes().clear();
		Long[] enrollTypeIds = StrUtils.splitToLong(get("enrollTypeId"));
		majorInfo.getEnrollTypes().addAll(entityDao.get(DictData.class, enrollTypeIds));
		majorInfo.setLogo(uploadFileService.update(get("newimg"), get("oldimg")));
		if (majorInfo.getGender().getId() == 0) {
			majorInfo.setGender(null);
		}
		Assert.isTrue(!existMajor(majorInfo), "专业重复，无法保存。");
		return super.saveAndForward(entity);
	}

	private boolean existMajor(MajorInfo majorInfo) {
		OqlBuilder query = OqlBuilder.from(MajorInfo.class);
		if (majorInfo.getId() != null) {
			query.where("id <> :id", majorInfo.getId());
		}
		query.where("major = :major", majorInfo.getMajor());
		return !entityDao.search(query).isEmpty();
	}

	public String option() {
		StudentMajorConfig config = majorPlanService.getConfig();
		put("config", config);
		return forward();
	}

	public String optionSave() {
		StudentMajorConfig config = populateEntity(StudentMajorConfig.class, "config");
		entityDao.saveOrUpdate(config);
		return redirect("search", "info.save.success");
	}

	public String department() {
		Long majorInfoId = getLong("majorInfoId");
		OqlBuilder query = OqlBuilder.from(Major.class, "major");
		query.select("distinct major.department");
		query.where("major.id not in (" +
				"select o.major.id from " + MajorInfo.class.getName() + " o" +
				(majorInfoId == null ? "" : " where o.id <> " + majorInfoId) +
				")");
		query.orderBy("major.department.code");
		query.cacheable();
		put("datas", entityDao.search(query));
		return forward("datas");
	}

	public String major() {
		Long departmentId = getLong("id");
		Long majorInfoId = getLong("majorInfoId");
		OqlBuilder query = OqlBuilder.from(Major.class, "major");
		query.where("major.department.id = :id", departmentId);
		query.where("major.id not in (" +
				"select o.major.id from " + MajorInfo.class.getName() + " o" +
				(majorInfoId == null ? "" : " where o.id <> " + majorInfoId) +
				")");
		query.orderBy("major.code");
		query.cacheable();
		put("datas", entityDao.search(query));
		return forward("datas");
	}
}