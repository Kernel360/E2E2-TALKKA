package com.talkka.server.subway.dto;

import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.enums.Line;

import lombok.Builder;

@Builder
public record SubwayConfusionRespDto(
	Long stationId,
	Line line,
	String dayType,
	String updown,
	String timeSlot,
	Double confusion
) {
	public static SubwayConfusionRespDto of(SubwayConfusionEntity subwayConfusionEntity) {
		return new SubwayConfusionRespDto(
			subwayConfusionEntity.getId(),
			subwayConfusionEntity.getLine(),
			subwayConfusionEntity.getDayType().getCode(),
			subwayConfusionEntity.getUpdown().getCode(),
			subwayConfusionEntity.getTimeSlot().getCode(),
			subwayConfusionEntity.getConfusion()
		);
	}
}
