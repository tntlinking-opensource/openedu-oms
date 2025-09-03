package com.yzsoft.yxxt.statistics.service;

import com.yzsoft.yxxt.statistics.dto.CountData;
import org.beangle.product.core.model.Department;

public interface StudentStatisticsService {

	CountData departmentCurrent();

	CountData departmentHistory();

	CountData majorCurrent();

	CountData majorCurrent(Department department);

	CountData majorHistory();

	CountData majorHistory(Department department);

	CountData nationCurrent();

	CountData nationCurrent(Department department);

	CountData nationHistory();

	CountData nationHistory(Department department);

	CountData cityCurrent();

	CountData cityCurrent(Department department);

	CountData cityHistory();

	CountData cityHistory(Department department);

	CountData genderCurrent();

	CountData genderCurrent(Department department);

	CountData genderHistory();

	CountData genderHistory(Department department);

	CountData dormCurrent();

	CountData dormCurrent(Department department);

	CountData dormHistory();

	CountData dormHistory(Department department);
}
