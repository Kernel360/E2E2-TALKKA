package com.talkka.server.subway.dto;

import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayStationRespDto(
	@NotNull
	Long stationId,
	@NotNull
	String stationName,
	@NotNull
	String stationCode,
	@NotNull
	@Schema(implementation = Line.class)
	Line line
) {
	public static SubwayStationRespDto of(SubwayStationEntity subwayStationEntity) {
		return new SubwayStationRespDto(
			subwayStationEntity.getId(),
			subwayStationEntity.getStationName(),
			subwayStationEntity.getStationCode(),
			subwayStationEntity.getLine()
		);
	}
}
