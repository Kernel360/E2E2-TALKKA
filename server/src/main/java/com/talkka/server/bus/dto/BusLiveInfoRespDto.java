package com.talkka.server.bus.dto;

import jakarta.validation.constraints.NotNull;

public record BusLiveInfoRespDto(
	@NotNull
	Long routeId,
	@NotNull
	String routeName,
	@NotNull
	BusRouteStationRespDto routeStation,
	BusArrivalRespDto arrivalInfo) {

	public static BusLiveInfoRespDto of(
		Long routeId,
		String routeName,
		BusRouteStationRespDto routeStation,
		BusArrivalRespDto arrivalInfo) {
		return new BusLiveInfoRespDto(routeId, routeName, routeStation, arrivalInfo);
	}
}
