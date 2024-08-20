package com.talkka.server.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayReviewReqDto(
	@NotNull Long stationId,
	@NotNull String line,
	@NotNull String updown,
	String content,
	@NotNull String timeSlot,
	@NotNull String rating
) {
}
