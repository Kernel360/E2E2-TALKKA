package com.talkka.server.admin.dto;

import com.talkka.server.admin.dao.CollectBusRouteEntity;
import com.talkka.server.bus.dao.BusRouteEntity;

// 이후에 필드가 추가될 것을 고려해 dto로 생성
public record CollectBusRouteCreateDto(
	Long routeId
) {
	public CollectBusRouteEntity toEntity(BusRouteEntity route) {
		return CollectBusRouteEntity.builder()
			.route(route)
			.build();
	}
}
