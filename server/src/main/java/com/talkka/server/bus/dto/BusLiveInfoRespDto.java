package com.talkka.server.bus.dto;

import jakarta.validation.constraints.NotNull;

public record BusLiveInfoRespDto(
	@NotNull
	Long routeId,
	@NotNull
	String routeName,
	@NotNull
	BusRouteStationRespDto routeStation,
	BusArrivalRespDto arrivalInfo,
	BusStaticsDto statics,
	Integer predictSeatsNow) {

	public static BusLiveInfoRespDto create(
		Long routeId,
		String routeName,
		BusRouteStationRespDto routeStation,
		BusArrivalRespDto arrivalInfo,
		BusStaticsDto statics,
		Integer predictSeatsNow) {
		return new BusLiveInfoRespDto(routeId, routeName, routeStation, arrivalInfo, statics, predictSeatsNow);
	}
}
