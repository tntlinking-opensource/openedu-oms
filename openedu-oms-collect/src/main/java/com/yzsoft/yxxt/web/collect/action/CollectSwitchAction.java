package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.CollectSwitch;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.product.core.service.StudentService;
import org.beangle.website.common.service.UploadFileService;
import org.beangle.website.system.model.DictData;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CollectSwitchAction extends SecurityActionSupport {

	@Resource
	private UploadFileService uploadFileService;
	@Resource
	private StudentService studentService;

	@Override
	protected String getEntityName() {
		return CollectSwitch.class.getName();
	}

	@Override
	public String search() {
		put("userid", getUserId());
		return super.search();
	}

	@Override
	protected String getDefaultOrderString() {
		return "code";
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		CollectSwitch collectSwitch = (CollectSwitch) entity;
		if (collectSwitch.getRemark() != null) {
			collectSwitch.setRemark(collectSwitch.getRemark().replaceAll("<br/>", "\n"));
		}
		put("userid", getUserId());
		put("educations", studentService.findEducation());
		put("enrollTypes", studentService.findEnrollType());
		//20201106增加是否取消宿舍功能 begin
		if(collectSwitch.isAdflag()!=null&&collectSwitch.isAdflag()==1){
			put("isAdflag", true);
		}else if(collectSwitch.isAdflag()!=null&&collectSwitch.isAdflag()==0){
			put("isAdflag", false);
		}
		//20201106增加是否取消宿舍功能 end
		super.editSetting(entity);
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		CollectSwitch collectSwitch = (CollectSwitch) entity;
		//20201106增加是否取消宿舍功能 begin
		Integer isAdflag = getInteger("isAdflag");
		if(isAdflag!=null){
			collectSwitch.setAdflag(isAdflag);
		}
		//20201106增加是否取消宿舍功能 end
		collectSwitch.setIcon(uploadFileService.update(get("newImg"), get("oldImg")));
		Long[] educationIds = StrUtils.splitToLong(get("educationId"));
		collectSwitch.getEducations().clear();
		collectSwitch.getEducations().addAll(entityDao.get(DictData.class, educationIds));
		Long[] enrollTypeIds = StrUtils.splitToLong(get("enrollTypeId"));
		collectSwitch.getEnrollTypes().clear();
		collectSwitch.getEnrollTypes().addAll(entityDao.get(DictData.class, enrollTypeIds));
		if (collectSwitch.getRemark() != null) {
			collectSwitch.setRemark(collectSwitch.getRemark().replaceAll("\n", "<br/>"));
		}
		return super.saveAndForward(entity);
	}

	public String enabled() {
		Long[] collectSwitchIds = getEntityIds(getShortName());
		Boolean enabled = getBoolean("enabled");
		if (null == enabled) {
			enabled = Boolean.TRUE;
		}
		List<CollectSwitch> collectSwitches = entityDao.get(CollectSwitch.class, collectSwitchIds);
		for (CollectSwitch collectSwitch : collectSwitches) {
			collectSwitch.setEnabled(enabled);
		}
		entityDao.saveOrUpdate(collectSwitches);
		return redirect("search", "info.save.success");
	}

	public String editable() {
		Long[] collectSwitchIds = getEntityIds(getShortName());
		Boolean editable = getBoolean("editable");
		if (null == editable) {
			editable = Boolean.TRUE;
		}
		List<CollectSwitch> collectSwitches = entityDao.get(CollectSwitch.class, collectSwitchIds);
		for (CollectSwitch collectSwitch : collectSwitches) {
			collectSwitch.setEditable(editable);
		}
		entityDao.saveOrUpdate(collectSwitches);
		return redirect("search", "info.save.success");
	}

	public String required() {
		Long[] collectSwitchIds = getEntityIds(getShortName());
		Boolean required = getBoolean("required");
		if (null == required) {
			required = Boolean.TRUE;
		}
		List<CollectSwitch> collectSwitches = entityDao.get(CollectSwitch.class, collectSwitchIds);
		for (CollectSwitch collectSwitch : collectSwitches) {
			collectSwitch.setRequired(required);
		}
		entityDao.saveOrUpdate(collectSwitches);
		return redirect("search", "info.save.success");
	}

	public String payment() {
		Long[] collectSwitchIds = getEntityIds(getShortName());
		Boolean payment = getBoolean("payment");
		if (null == payment) {
			payment = Boolean.TRUE;
		}
		List<CollectSwitch> collectSwitches = entityDao.get(CollectSwitch.class, collectSwitchIds);
		for (CollectSwitch collectSwitch : collectSwitches) {
			collectSwitch.setPayment(payment);
		}
		entityDao.saveOrUpdate(collectSwitches);
		return redirect("search", "info.save.success");
	}
}
