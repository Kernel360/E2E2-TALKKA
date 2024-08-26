package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusRouteStationEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusRouteStationRespDto(
	@NotNull
	Long busRouteStationId,
	@NotNull
	Long routeId,
	@NotNull
	Long stationId,
	@NotNull
	Short stationSeq,
	@NotNull
	String stationName,
	@NotNull
	LocalDateTime createdAt
) {
	public static BusRouteStationRespDto of(BusRouteStationEntity busRouteStationEntity) {
		return new BusRouteStationRespDto(
			busRouteStationEntity.getId(),
			busRouteStationEntity.getRoute().getId(),
			busRouteStationEntity.getStation().getId(),
			busRouteStationEntity.getStationSeq(),
			busRouteStationEntity.getStation().getStationName(),
			busRouteStationEntity.getCreatedAt()
		);
	}
}
