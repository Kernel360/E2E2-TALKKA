package com.talkka.server.admin.dto;

import com.talkka.server.bus.dto.BusRouteStationRespDto;

public record BookmarkStatRespDto(
	BusRouteStationRespDto busRouteStation,
	Integer count
) {
}
