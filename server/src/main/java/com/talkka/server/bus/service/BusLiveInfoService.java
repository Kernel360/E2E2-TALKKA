package com.talkka.server.bus.service;

import java.time.LocalDateTime;

import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;

public interface BusLiveInfoService {

	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId)
		throws BusRouteStationNotFoundException;

	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, Integer stationNum, LocalDateTime time,
		Integer timeRangeMinute, Long week)
		throws BusRouteStationNotFoundException;
}
