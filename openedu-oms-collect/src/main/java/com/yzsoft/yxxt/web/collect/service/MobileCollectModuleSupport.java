package com.yzsoft.yxxt.web.collect.service;

import com.yzsoft.yxxt.web.collect.CollectAction;

import javax.annotation.PostConstruct;

public abstract class MobileCollectModuleSupport {

    @PostConstruct
    public void init() {
        addAction();
    }

    protected void addAction(String code, Class clazz) {
        MobileCollectAction.addAction(code, clazz);
    }

    protected void addAction(Class<? extends CollectAction> clazz) {
        CollectAction collectAction = null;
        try {
            collectAction = clazz.newInstance();
        } catch (Exception e) {
        }
        MobileCollectAction.addAction(collectAction.getCode(), clazz);
    }

    protected void addDefaultAction(Class<? extends CollectAction> clazz) {
        CollectAction collectAction = null;
        try {
            collectAction = clazz.newInstance();
        } catch (Exception e) {
        }
        MobileCollectAction.addDefaultAction(collectAction.getCode(), clazz);
    }

    abstract protected void addAction();
}
