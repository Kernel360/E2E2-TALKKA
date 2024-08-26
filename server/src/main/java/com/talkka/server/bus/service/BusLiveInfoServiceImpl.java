package com.talkka.server.bus.service;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusLiveInfoServiceImpl implements BusLiveInfoService {
	private final BusArrivalService busArrivalService;
	private final BusRouteStationRepository busRouteStationRepository;

	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId)
		throws BusRouteStationNotFoundException {
		var busRouteStation = busRouteStationRepository.findById(routeStationId)
			.orElseThrow(() -> new BusRouteStationNotFoundException(routeStationId));
		var route = busRouteStation.getRoute();
		var station = busRouteStation.getStation();
		var routeId = route.getId();
		var routeName = route.getRouteName();
		var apiRouteId = route.getApiRouteId();
		var apiStationId = station.getApiStationId();
		var arrivalInfo = busArrivalService.getBusArrivalInfo(routeStationId, apiRouteId, apiStationId)
			.orElse(null);

		return BusLiveInfoRespDto.of(
			busRouteStation.getStationSeq(),
			routeId,
			routeName,
			BusRouteStationRespDto.of(busRouteStation),
			arrivalInfo);
	}
}
