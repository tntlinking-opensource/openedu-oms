package com.yzsoft.yxxt.web.collect.freemarker;

import freemarker.core.Environment;
import freemarker.ext.beans.StringModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class Text implements TemplateMethodModelEx {

	@Override
	public Object exec(List arguments) throws TemplateModelException {
		Environment env = Environment.getCurrentEnvironment();
		String name = ((SimpleScalar) arguments.get(0)).toString();
		String objName = StringUtils.substringBefore(name, ".");
		StringModel model = (StringModel) env.getVariable(objName);
		Object obj = model == null ? null : model.getWrappedObject();
		return getString(env, obj, StringUtils.substringAfter(name, "."));
	}

	private String getString(Environment env, Object obj, String propertyName) {
		if (obj == null) {
			return null;
		}
		if (propertyName.indexOf(".") > 0) {
			try {
				obj = PropertyUtils.getProperty(obj, StringUtils.substringBefore(propertyName, "."));
				return getString(env, obj, StringUtils.substringAfter(propertyName, "."));
			} catch (Exception e) {
			}
		} else {
			try {
				Object val = PropertyUtils.getProperty(obj, propertyName);
				if (PropertyUtils.isReadable(val, "name")) {
					return BeanUtils.getProperty(val, "name");
				}
				if (val instanceof Boolean) {
					return (Boolean) val == true ? "是" : "否";
				}
				if (val != null) {
					return val.toString();
				}
			} catch (Exception e) {
			}
		}
		return null;
	}
}
