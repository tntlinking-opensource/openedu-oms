package com.yzsoft.yxxt.welcome.service;

import com.yzsoft.yxxt.welcome.dto.*;
import org.beangle.process.model.ProcessBatch;

import java.util.List;

public interface WelcomeCountService {

    List<BaseCount> countByDepartment(ProcessBatch batch);

    List<BaseCount> countByMajor(ProcessBatch batch);

    List<NationCount> countByNation(ProcessBatch batch);

    List<BaseCount> countByProvince(ProcessBatch batch);

    List<BaseCount> countByCity(ProcessBatch batch);
}
