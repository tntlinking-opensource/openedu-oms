package com.yzsoft.yxxt.prepare.service;


import com.yzsoft.yxxt.prepare.model.Batch;

import java.util.List;

public interface BatchService {

    Batch getBatch();

    Batch getLatestBatch();

    List<Batch> findBatch();
}
