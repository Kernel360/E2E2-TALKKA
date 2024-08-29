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
	BusStaticsDto statics) {

	public static BusLiveInfoRespDto create(
		Long routeId,
		String routeName,
		BusRouteStationRespDto routeStation,
		BusArrivalRespDto arrivalInfo,
		BusStaticsDto statics) {
		return new BusLiveInfoRespDto(routeId, routeName, routeStation, arrivalInfo, statics);
	}
}
