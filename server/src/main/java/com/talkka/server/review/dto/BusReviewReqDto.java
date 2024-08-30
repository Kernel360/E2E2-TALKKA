package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusReviewReqDto(
	@NotNull Long routeId,
	@NotNull Long busRouteStationId,
	String content,
	@NotNull
	@Schema(implementation = TimeSlot.class)
	String timeSlot,
	@NotNull Integer rating
) {
}
