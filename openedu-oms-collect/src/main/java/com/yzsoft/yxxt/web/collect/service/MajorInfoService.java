package com.yzsoft.yxxt.web.collect.service;

import com.yzsoft.yxxt.web.collect.model.MajorInfo;
import org.beangle.product.core.model.Student;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface MajorInfoService {

    List<MajorInfo> find();

    List<MajorInfo> find(Student student);

    List<DictData> findGender();
}
