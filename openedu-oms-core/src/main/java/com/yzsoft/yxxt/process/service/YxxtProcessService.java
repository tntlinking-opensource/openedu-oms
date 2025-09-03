package com.yzsoft.yxxt.process.service;

import org.beangle.process.model.ProcessBatch;
import org.beangle.product.core.model.Student;

public interface YxxtProcessService {

	Long getStudentNum(ProcessBatch batch);

	ProcessBatch getBatch(Student student);
}
