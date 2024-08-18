package com.talkka.server.subway.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;

import lombok.Builder;

@Builder
public record SubwayConfusionRespDto(
	Long stationId,
	String stationName,
	Line line,
	DayType dayType,
	Updown updown,
	TimeSlot timeSlot,
	Double confusion
) {
	public static SubwayConfusionRespDto of(SubwayConfusionEntity subwayConfusionEntity) {
		return new SubwayConfusionRespDto(
			subwayConfusionEntity.getId(),
			subwayConfusionEntity.getStationName(),
			subwayConfusionEntity.getLine(),
			subwayConfusionEntity.getDayType(),
			subwayConfusionEntity.getUpdown(),
			subwayConfusionEntity.getTimeSlot(),
			subwayConfusionEntity.getConfusion()
		);
	}
}
