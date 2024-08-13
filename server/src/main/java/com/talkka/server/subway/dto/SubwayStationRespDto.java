package com.talkka.server.subway.dto;

import com.talkka.server.subway.dao.SubwayStationEntity;

import lombok.Builder;

@Builder
public record SubwayStationRespDto(
	Long stationId,
	String stationName,
	String stationCode,
	String lineCode
) {
	public static SubwayStationRespDto of(SubwayStationEntity subwayStationEntity) {
		return new SubwayStationRespDto(
			subwayStationEntity.getId(),
			subwayStationEntity.getStationName(),
			subwayStationEntity.getStationCode(),
			subwayStationEntity.getLine().getCode()
		);
	}
}
