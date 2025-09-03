package com.yzsoft.yxxt.core.service.impl;

import java.util.Properties;

public class PropertiesConfig {

    private Properties properties;

    public PropertiesConfig(String name) {
        properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(name));
        } catch (Exception e) {
        }
    }

    protected boolean getBoolean(String key, boolean defaultValue) {
        try {
            if (properties.containsKey(key)) {
                return Boolean.parseBoolean(properties.getProperty(key));
            }
        } catch (Exception e) {
        }
        return defaultValue;
    }

}
