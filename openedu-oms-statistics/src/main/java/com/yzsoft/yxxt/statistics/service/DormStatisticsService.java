package com.yzsoft.yxxt.statistics.service;

import com.yzsoft.yxxt.statistics.dto.CountData;

public interface DormStatisticsService {

	CountData studentCurrent();

	CountData studentHistory();

	CountData roomCurrent();

	CountData roomHistory();

	CountData bedCurrent();

	CountData bedHistory();
}
