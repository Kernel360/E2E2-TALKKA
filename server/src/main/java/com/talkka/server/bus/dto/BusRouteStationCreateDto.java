package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusStationEntity;

import lombok.Builder;

@Builder
public record BusRouteStationCreateDto(
	String apiRouteId,
	String apiStationId,
	Short stationSeq,
	String stationName
) {
	public BusRouteStationEntity toEntity(BusRouteEntity routeEntity, BusStationEntity stationEntity) {
		return BusRouteStationEntity.builder()
			.route(routeEntity)
			.station(stationEntity)
			.stationSeq(stationSeq())
			.stationName(stationName())
			.build();
	}
}
