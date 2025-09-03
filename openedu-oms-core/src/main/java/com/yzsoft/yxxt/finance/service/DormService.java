package com.yzsoft.yxxt.finance.service;


import com.yzsoft.yxxt.finance.model.DormSetting;
import org.beangle.product.core.model.Student;

import java.util.List;

public interface DormService {

	List<DormSetting> findSetting();

    /**
     * 是否住宿，根据是否住宿更新学费
     * @param student
     * @param accommodationed
     */
    void accommodationed(Student student, boolean accommodationed);
}
