package com.talkka.server.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusReviewReqDto(
	@NotNull Long routeId,
	@NotNull Long busRouteStationId,
	String content,
	@NotNull String timeSlot,
	@NotNull String rating
) {
}
