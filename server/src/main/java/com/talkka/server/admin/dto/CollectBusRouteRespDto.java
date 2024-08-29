package com.talkka.server.admin.dto;

import java.time.LocalDateTime;

import com.talkka.server.admin.dao.CollectBusRouteEntity;
import com.talkka.server.bus.dto.BusRouteRespDto;

public record CollectBusRouteRespDto(
	Long id,
	BusRouteRespDto routeRespDto,
	LocalDateTime createdAt
) {
	public static CollectBusRouteRespDto of(CollectBusRouteEntity entity) {
		return new CollectBusRouteRespDto(
			entity.getId(),
			BusRouteRespDto.of(entity.getRoute()),
			entity.getCreatedAt()
		);
	}
}
