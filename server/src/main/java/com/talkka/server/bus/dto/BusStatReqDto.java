package com.talkka.server.bus.dto;

import java.time.LocalDateTime;

public record BusStatReqDto(
	Long routeId,
	Long stationId,
	LocalDateTime startDateTime,
	LocalDateTime endDateTime
) {
}
