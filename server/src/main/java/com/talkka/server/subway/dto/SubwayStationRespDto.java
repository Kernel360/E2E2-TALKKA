package com.talkka.server.subway.dto;

import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;

import lombok.Builder;

@Builder
public record SubwayStationRespDto(
	Long stationId,
	String stationName,
	String frCode,
	Line line
) {
	public static SubwayStationRespDto of(SubwayStationEntity subwayStationEntity) {
		return new SubwayStationRespDto(
			subwayStationEntity.getId(),
			subwayStationEntity.getStationName(),
			subwayStationEntity.getFrCode(),
			subwayStationEntity.getLine()
		);
	}
}
