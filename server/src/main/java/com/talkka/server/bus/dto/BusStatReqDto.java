package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

public record BusStatReqDto(
	String apiRouteId,
	String apiStationId,
	LocalDateTime startDateTime,
	LocalDateTime endDateTime
) {
}
