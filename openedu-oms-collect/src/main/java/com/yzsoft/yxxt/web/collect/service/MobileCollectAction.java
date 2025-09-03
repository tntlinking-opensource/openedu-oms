package com.yzsoft.yxxt.web.collect.service;

import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class MobileCollectAction {

    private static Map<String, Class> map = new HashMap<String, Class>();
    private static Map<String, Class> defaultMap = new HashMap<String, Class>();

    public static Class getAction(String code) {
        Class clazz = getClazz(code);
        Assert.notNull(clazz, "not class found for code “" + code + "”");
        return clazz;
    }

    public static void addAction(String code, Class clazz) {
        map.put(code, clazz);
    }

    public static void addDefaultAction(String code, Class clazz) {
        defaultMap.put(code, clazz);
    }

    public static boolean hasAction(String code) {
        return getClazz(code) != null;
    }

    private static Class getClazz(String code) {
        Class clazz = map.get(code);
        if (clazz == null) {
            clazz = defaultMap.get(code);
        }
        return clazz;
    }
}
