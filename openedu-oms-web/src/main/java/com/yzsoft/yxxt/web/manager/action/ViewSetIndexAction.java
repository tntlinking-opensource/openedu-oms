package com.yzsoft.yxxt.web.manager.action;

import com.yzsoft.yxxt.web.manager.bean.ViewSetIndex;
import com.yzsoft.yxxt.web.manager.service.ViewSetService;
import org.beangle.struts2.action.BaseAction;
import org.beangle.website.common.util.WebuploaderUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ViewSetIndexAction extends BaseAction {

	@Resource
	private ViewSetService viewSetService;

	private ViewSetIndex getConfig() {
		return viewSetService.getViewSetIndex();
	}

	private void putConfig() {
		put("config", getConfig());
	}

	private String saveConfig(ViewSetIndex config) {
		viewSetService.save(ViewSetService.VIEW_SET_INDEX, config);
		return redirect("refresh");
	}

	public String index() {
		return forward();
	}

	public String refresh() {
		return forward();
	}

	public String footInfo() {
		ViewSetIndex config = getConfig();
		if (config.getFootInfo() != null) {
			config.setFootInfo(config.getFootInfo().replaceAll("<br/>", "\n"));
		}
		put("config", config);
		return forward();
	}

	public String footInfoSave() {
		ViewSetIndex config = getConfig();
		config.setFootInfo(get("footInfo").replaceAll("\n", "<br/>"));
		config.setFootBgImg(WebuploaderUtils.updateTempFile(get("footBgImg"), config.getFootBgImg()));
		return saveConfig(config);
	}

	public String app() {
		putConfig();
		return forward();
	}

	public String appSave() {
		ViewSetIndex config = getConfig();
		config.setAppImg(WebuploaderUtils.updateTempFile(get("appImg"), config.getAppImg()));
		config.setAppName(get("appName"));
		return saveConfig(config);
	}

	public String processImg() {
		putConfig();
		put("type",getBool("type"));
		return forward();
	}

	public String processImgSave() {
		ViewSetIndex config = getConfig();
		if(getBool("type")){
			config.setProcessGzImg(WebuploaderUtils.updateTempFile(get("processImg"), config.getProcessGzImg()));
		}else{
			config.setProcessZzImg(WebuploaderUtils.updateTempFile(get("processImg"), config.getProcessZzImg()));
		}
		return saveConfig(config);
	}
}
