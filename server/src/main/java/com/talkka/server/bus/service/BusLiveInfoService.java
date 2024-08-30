package com.talkka.server.bus.service;

import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.common.enums.TimeSlot;

public interface BusLiveInfoService {
	BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, String timeSlot, Long week)
		throws BusRouteStationNotFoundException;

	BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, TimeSlot timeSlot, Long week)
		throws BusRouteStationNotFoundException;

	BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, Integer stationNum, TimeSlot time,
		Integer timeRangeMinute, Long week)
		throws BusRouteStationNotFoundException;
}
