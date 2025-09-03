package com.yzsoft.yxxt.welcome.service;

import org.beangle.process.model.ProcessBatch;

import java.util.List;

public interface WelcomeService {

    List<String> findYear();

    /**
     * 在环节通过时，如果该环节是报到环节，则将该学生标记为已报到
     * @param batchId
     * @param itemId
     * @param studentId
     */
    void processLinkPassed(Long batchId, Long itemId, Long studentId);

    void register(Long studentId);

    void register(Long studentId, Boolean registered);

    ProcessBatch getBatch(Long studentId);
}
