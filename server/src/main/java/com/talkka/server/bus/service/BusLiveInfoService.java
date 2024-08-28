package com.talkka.server.bus.service;

import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;

public interface BusLiveInfoService {

	BusLiveInfoRespDto getBusLiveInfo(Long routeStationId) throws
		BusRouteStationNotFoundException;
}
