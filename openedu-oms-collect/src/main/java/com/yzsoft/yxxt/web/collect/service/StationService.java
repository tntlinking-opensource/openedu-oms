package com.yzsoft.yxxt.web.collect.service;

import com.yzsoft.yxxt.web.collect.model.Station;
import com.yzsoft.yxxt.web.collect.model.StationDate;
import com.yzsoft.yxxt.web.collect.model.StationReason;
import com.yzsoft.yxxt.web.collect.model.StationTime;
import org.beangle.website.system.model.DictData;

import java.util.List;

public interface StationService {

	StationDate getDate();

	List<Station> findStation();

	List<StationReason> findReason();

    List<DictData> findVehicle();
}
