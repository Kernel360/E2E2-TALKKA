package com.talkka.server.bus.service;

import java.util.Optional;

import com.talkka.server.bus.dto.BusLiveArrivalRespDto;
import com.talkka.server.bus.exception.GetBusLiveArrivalInfoFailedException;

public interface BusArrivalService {
	Optional<BusLiveArrivalRespDto> getBusArrivalInfo(Long routeStationId, String apiRouteId,
		String apiStationId) throws
		GetBusLiveArrivalInfoFailedException;
}
