package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

import com.talkka.server.bus.dao.BusRouteStationEntity;

import lombok.Builder;

@Builder
public record BusRouteStationRespDto(
	Long busRouteStationId,
	BusRouteRespDto route,
	BusStationRespDto station,
	Short stationSeq,
	String stationName,
	LocalDateTime createdAt
) {
	public static BusRouteStationRespDto of(BusRouteStationEntity busRouteStationEntity) {
		return new BusRouteStationRespDto(
			busRouteStationEntity.getId(),
			BusRouteRespDto.of(busRouteStationEntity.getRoute()),
			BusStationRespDto.of(busRouteStationEntity.getStation()),
			busRouteStationEntity.getStationSeq(),
			busRouteStationEntity.getStationName(),
			busRouteStationEntity.getCreatedAt()
		);
	}
}
