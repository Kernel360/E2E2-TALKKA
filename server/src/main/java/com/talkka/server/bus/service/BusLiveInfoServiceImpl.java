package com.talkka.server.bus.service;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.exception.GetBusLiveArrivalInfoFailedException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusLiveInfoServiceImpl implements BusLiveInfoService {
	private final BusArrivalService busArrivalService;
	private final BusRouteStationRepository busRouteStationRepository;

	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId)
		throws BusRouteStationNotFoundException, GetBusLiveArrivalInfoFailedException {
		var busRouteStation = busRouteStationRepository.findById(routeStationId)
			.orElseThrow(() -> new BusRouteStationNotFoundException(routeStationId));
		var apiRouteId = busRouteStation.getRoute().getApiRouteId();
		var apiStationId = busRouteStation.getStation().getApiStationId();
		var arrivalInfo = busArrivalService.getBusArrivalInfo(routeStationId, apiRouteId, apiStationId)
			.orElse(null);

		return BusLiveInfoRespDto.of(
			busRouteStation.getStationSeq(),
			BusRouteStationRespDto.of(busRouteStation),
			arrivalInfo);
	}
}
