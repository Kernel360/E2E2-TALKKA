package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusRouteStationEntity;

import lombok.Builder;

@Builder
public record BusRouteStationRespDto(
	Long busRouteStationId,
	Long routeId,
	Long stationId,
	Short stationSeq,
	String stationName,
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
