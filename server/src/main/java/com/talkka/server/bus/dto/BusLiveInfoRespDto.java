package com.talkka.server.bus.dto;

import jakarta.validation.constraints.NotNull;

public record BusLiveInfoRespDto(
	@NotNull
	Short seq,
	@NotNull
	BusRouteStationRespDto routeStation,
	BusLiveArrivalRespDto arrivalInfo) {

	public static BusLiveInfoRespDto of(
		Short seq,
		BusRouteStationRespDto routeStation,
		BusLiveArrivalRespDto arrivalInfo) {
		return new BusLiveInfoRespDto(seq, routeStation, arrivalInfo);
	}
}
