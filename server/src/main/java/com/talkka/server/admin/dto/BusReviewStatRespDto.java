package com.talkka.server.admin.dto;

import com.talkka.server.bus.dto.BusRouteStationRespDto;

public record BusReviewStatRespDto(
	BusRouteStationRespDto busRouteStation,
	Integer count
) {
}
