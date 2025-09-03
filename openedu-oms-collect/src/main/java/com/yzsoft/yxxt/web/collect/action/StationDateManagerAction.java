package com.yzsoft.yxxt.web.collect.action;

import com.yzsoft.yxxt.web.collect.model.StationDate;
import com.yzsoft.yxxt.web.collect.model.StationTime;
import com.yzsoft.yxxt.web.collect.service.StationService;
import org.beangle.commons.exception.MyException;
import org.beangle.model.Entity;
import org.beangle.struts2.action.EntityDrivenAction;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;
import java.util.List;

@Controller
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class StationDateManagerAction extends EntityDrivenAction {

	@Resource
	private StationService stationService;

	@Override
	protected String getEntityName() {
		return StationDate.class.getName();
	}

	@Override
	public String index() {
		return super.index();
	}

	@Override
	protected void indexSetting() {
		StationDate stationDate = stationService.getDate();
		if (stationDate == null) {
			stationDate = new StationDate();
		}
		if (stationDate.getTimes().size() < 6) {
			for (int i = stationDate.getTimes().size(); i < 6; i++) {
				StationTime time = new StationTime();
				time.setSort(i + 1);
				stationDate.getTimes().add(time);
			}
		}
		put("stationDate", stationDate);
		super.indexSetting();
	}

	@Override
	protected void editSetting(Entity<?> entity) {
		super.editSetting(entity);
		indexSetting();
	}

	@Override
	public String save() throws Exception {
		Entity entity = populateEntity();
		try {
			return saveAndForward(entity);
		} catch (MyException e) {
			editSetting(entity);
			addActionError(e.getMessage());
			return forward("edit");
		} catch (Exception e) {
			logger.info("saveAndForwad failure", e);
			return redirect("index", "info.save.failure");
		}
	}

	@Override
	protected String saveAndForward(Entity<?> entity) {
		StationDate date = (StationDate) entity;
		List<StationTime> times = populateList(StationTime.class, "time");
		Integer sort = 1;
		for (StationTime time : times) {
			time.setSort(sort++);
			time.setDate(date);
		}
		date.getTimes().clear();
		date.getTimes().addAll(times);
		entityDao.saveOrUpdate(date);
		return redirect("index", "info.save.success");
	}
}
