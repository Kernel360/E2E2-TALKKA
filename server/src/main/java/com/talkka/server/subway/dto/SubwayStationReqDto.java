package com.talkka.server.subway.dto;

import com.talkka.server.common.util.EnumCodeConverterUtils;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayStationReqDto(
	@NotNull String stationName,
	@NotNull String stationCode,
	@NotNull String lineId
) {
	public static SubwayStationEntity toEntity(SubwayStationReqDto subwayStationReqDto) {
		return SubwayStationEntity.builder()
			.stationName(subwayStationReqDto.stationName)
			.stationCode(subwayStationReqDto.stationCode)
			.line(EnumCodeConverterUtils.fromCode(Line.class, subwayStationReqDto.lineId))
			.build();
	}
}
