package com.talkka.server.subway.dto;

import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.exception.enums.InvalidLineEnumException;

import lombok.Builder;

@Builder
public record SubwayStationDto(
	String stationName,
	String stationCode,
	Line line
) {
	public static SubwayStationDto of(SubwayStationReqDto subwayStationReqDto)
		throws InvalidLineEnumException {
		return new SubwayStationDto(
			subwayStationReqDto.stationName(),
			subwayStationReqDto.stationCode(),
			Line.valueOfEnumString(subwayStationReqDto.line())
		);
	}

	public static SubwayStationEntity toEntity(SubwayStationDto subwayStationDto) {
		return SubwayStationEntity.builder()
			.stationName(subwayStationDto.stationName)
			.stationCode(subwayStationDto.stationCode)
			.line(subwayStationDto.line)
			.build();
	}
}
