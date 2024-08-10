package com.talkka.server.subway.dto;

import com.talkka.server.common.util.EnumCodeConverterUtils;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;

import lombok.Builder;

@Builder
public record SubwayStationReqDto(
	String apiStationId,
	String stationName,
	String frCode,
	String lineId
) {
	public static SubwayStationEntity toEntity(SubwayStationReqDto subwayStationReqDto) {
		return SubwayStationEntity.builder()
			.apiStationId(subwayStationReqDto.apiStationId)
			.stationName(subwayStationReqDto.stationName)
			.frCode(subwayStationReqDto.frCode)
			.line(EnumCodeConverterUtils.fromCode(Line.class, subwayStationReqDto.lineId))
			.build();
	}
}
