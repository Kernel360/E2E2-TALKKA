package com.talkka.server.bus.dto;

import jakarta.validation.constraints.NotNull;

public record BusLiveInfoRespDto(
	@NotNull
	Short seq,
	@NotNull
	Long routeId,
	@NotNull
	String routeName,
	@NotNull
	BusRouteStationRespDto routeStation,
	BusArrivalRespDto arrivalInfo) {

	public static BusLiveInfoRespDto of(
		Short seq,
		Long routeId,
		String routeName,
		BusRouteStationRespDto routeStation,
		BusArrivalRespDto arrivalInfo) {
		return new BusLiveInfoRespDto(seq, routeId, routeName, routeStation, arrivalInfo);
	}
}
