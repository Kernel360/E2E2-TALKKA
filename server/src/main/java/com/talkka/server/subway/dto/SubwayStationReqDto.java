package com.talkka.server.subway.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayStationReqDto(
	@NotNull String stationName,
	@NotNull String stationCode,
	@NotNull String line
) {
}
