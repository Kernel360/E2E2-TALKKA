package com.talkka.server.subway.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayConfusionRespDto(
	@NotNull
	Long stationId,
	@NotNull
	String stationName,
	@NotNull
	@Schema(implementation = Line.class)
	Line line,
	@NotNull
	@Schema(implementation = DayType.class)
	DayType dayType,
	@NotNull
	@Schema(implementation = Updown.class)
	Updown updown,
	@NotNull
	@Schema(implementation = TimeSlot.class)
	TimeSlot timeSlot,
	@NotNull
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
