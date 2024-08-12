package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusRouteStationEntity;

import lombok.Builder;

@Builder
public record BusRouteStationRespDto(
	Long busRouteStationId,
	Short stationSeq,
	String stationName,
	Long routeId,
	String routeName,
	Long stationId,
	LocalDateTime createdAt
) {
	public static BusRouteStationRespDto of(BusRouteStationEntity busRouteStationEntity) {
		return new BusRouteStationRespDto(
			busRouteStationEntity.getId(),
			busRouteStationEntity.getStationSeq(),
			busRouteStationEntity.getStation().getStationName(),
			busRouteStationEntity.getRoute().getId(),
			busRouteStationEntity.getRoute().getRouteName(),
			busRouteStationEntity.getStation().getId(),
			busRouteStationEntity.getCreatedAt()
		);
	}
}
