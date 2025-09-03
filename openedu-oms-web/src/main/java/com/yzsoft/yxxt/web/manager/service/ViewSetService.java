package com.yzsoft.yxxt.web.manager.service;

import com.yzsoft.yxxt.web.manager.bean.ViewSetIndex;
import net.sf.json.JSONObject;


public interface ViewSetService {

	String VIEW_SET_INDEX = "首页";

	JSONObject get(String name);

	<T> T get(String name, Class<T> cls);

	<T> T get(Class<T> cls);

	void save(String name, Object obj);

	<T> void save(T obj);

	ViewSetIndex getViewSetIndex();
}
