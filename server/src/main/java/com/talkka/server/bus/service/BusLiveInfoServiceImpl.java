package com.talkka.server.bus.service;

import java.time.LocalDateTime;

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
	private final BusStaticsService busStaticsService;
	private final BusRouteStationRepository busRouteStationRepository;

	/**
	 * 버스 실시간 정보를 조회합니다.
	 *
	 * STATION_NUM = 2 (앞 뒤로 캡쳐할 정거장 수)
	 * TIME_RANGE_MINUTE = 20 (앞 뒤로 캡쳐할 시간)
	 * WEEK = 2 (몇 주 전 데이터까지 조회할지)
	 * @param routeStationId 기준 노선정거장 ID
	 * @return BusLiveInfoRespDto
	 */
	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId) {
		final int STATION_NUM = 2;
		final int TIME_RANGE_MINUTE = 20;
		final long WEEK = 2;
		// final LocalDateTime now = LocalDateTime.now();
		// 08.28 07:00
		final LocalDateTime now = LocalDateTime.of(2024, 8, 28, 7, 0);

		return this.getBusLiveInfo(routeStationId, STATION_NUM, now, TIME_RANGE_MINUTE, WEEK);
	}

	@Override
	public BusLiveInfoRespDto getBusLiveInfo(Long routeStationId, Integer stationNum, LocalDateTime time,
		Integer timeRangeMinute, Long week) {
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
		var statics = busStaticsService.getRouteStationStatics(routeStationId, stationNum, time, timeRangeMinute, week);

		return BusLiveInfoRespDto.create(
			routeId,
			routeName,
			BusRouteStationRespDto.of(busRouteStation),
			arrivalInfo,
			statics);
	}
}
