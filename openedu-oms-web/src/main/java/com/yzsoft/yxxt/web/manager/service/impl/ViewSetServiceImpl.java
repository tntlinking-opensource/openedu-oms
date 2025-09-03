package com.yzsoft.yxxt.web.manager.service.impl;

import com.yzsoft.yxxt.web.manager.bean.ViewSetIndex;
import com.yzsoft.yxxt.web.model.ViewSet;
import com.yzsoft.yxxt.web.manager.service.ViewSetService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViewSetServiceImpl extends BaseServiceImpl implements ViewSetService {

	public <T> T get(String name, Class<T> cls) {
		ViewSet vs = getViewSet(name);
		JSONObject json = JSONObject.fromObject(vs.getConfig());
		try {
			@SuppressWarnings("unchecked")
			T t = (T) JSONObject.toBean(json, cls);
			return t;
		} catch (Exception e) {
		}
		try {
			return cls.newInstance();
		} catch (Exception e) {
		}
		return null;
	}

	@Override
	public <T> T get(Class<T> cls) {
		return get(cls.getName(), cls);
	}

	public ViewSet getViewSet(String name) {
		OqlBuilder<ViewSet> query = OqlBuilder.from(ViewSet.class, "vs");
		query.where("vs.name = :name", name);
		query.cacheable();
		List<ViewSet> list = entityDao.search(query);
		ViewSet vs = null;
		if (list.isEmpty()) {
			vs = new ViewSet();
			vs.setName(name);
		} else {
			vs = list.get(0);
		}
		if (StringUtils.isEmpty(vs.getConfig())) {
			vs.setConfig("{}");
		}
		return vs;
	}

	public void save(String name, Object obj) {
		JSONObject json = null;
		if (obj instanceof JSONObject) {
			json = (JSONObject) obj;
		} else {
			json = JSONObject.fromObject(obj);
		}
		ViewSet vs = getViewSet(name);
		vs.setConfig(json.toString());
		entityDao.saveOrUpdate(vs);
	}

	@Override
	public <T> void save(T obj) {
		save(obj.getClass().getName(), obj);
	}

	@Override
	public ViewSetIndex getViewSetIndex() {
		return get(VIEW_SET_INDEX, ViewSetIndex.class);
	}

	public JSONObject get(String name) {
		ViewSet vs = getViewSet(name);
		return JSONObject.fromObject(vs.getConfig());
	}

}
