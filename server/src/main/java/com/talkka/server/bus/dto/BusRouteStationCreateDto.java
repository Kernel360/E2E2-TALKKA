package com.talkka.server.bus.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusStationEntity;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusRouteStationCreateDto(
	@NotNull
	Long routeId,
	@NotNull
	Long stationId,
	@NotNull
	Short stationSeq,
	@NotNull
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
