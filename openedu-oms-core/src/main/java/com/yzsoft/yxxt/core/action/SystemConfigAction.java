package com.yzsoft.yxxt.core.action;

import com.yzsoft.yxxt.core.model.SystemConfig;
import com.yzsoft.yxxt.core.service.YxxtInitService;
import com.yzsoft.yxxt.core.service.YxxtService;
import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * 系统参数设置
 *
 * @作者：周建明
 * @公司：上海彦致信息技术有限公司
 * @创建日期：2016年12月19日 下午3:22:51
 */
@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SystemConfigAction extends EntityDrivenAction {

	@Resource
	private YxxtService yxxtService;

	@Resource
	private YxxtInitService yxxtInitService;

	@Override
	public String index() {
		return redirect("info");
	}

	@Override
	public String search() {
		return redirect("info");
	}

	@Override
	public String edit() {
//		put("systemYear", yxxtService.getYear());//学校代码
		put("systemConfig", yxxtService.getSystemConfig());
		return forward();
	}

	/**
	 * 保存修改后的标准
	 *
	 * @return
	 */
	public String save() {
//		Integer systemYear = getInteger("systemYear");
//		yxxtService.saveYear(systemYear);
		SystemConfig systemConfig = populate(SystemConfig.class, "systemConfig");
		entityDao.saveOrUpdate(systemConfig);
		return redirect("index", "保存成功");
	}

	@Override
	public String info() {
//		put("systemYear", yxxtService.getYear());
		put("systemConfig", yxxtService.getSystemConfig());
		return forward();
	}

	public String init() {
		yxxtInitService.init();
		return forward();
	}
}
