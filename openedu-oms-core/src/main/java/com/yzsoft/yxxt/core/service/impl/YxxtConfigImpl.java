package com.yzsoft.yxxt.core.service.impl;

import com.yzsoft.yxxt.core.service.YxxtConfig;
import org.springframework.stereotype.Service;

@Service
public class YxxtConfigImpl implements YxxtConfig {

    private PropertiesConfig config;

    public YxxtConfigImpl() {
        config = new PropertiesConfig("yxxt.properties");
    }

    @Override
    public boolean isProcessEnabled() {
        return config.getBoolean("yxxt.process.enabled", true);
    }

    @Override
    public boolean isStudentInfoEducationEnabled() {
        return config.getBoolean("yxxt.collect.student_info.educations.enabled", true);
    }
}
