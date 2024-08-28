package com.talkka.server.subway.dto;

import java.time.LocalTime;

import com.talkka.server.subway.dao.SubwayTimetableEntity;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Express;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayTimetableRespDto(
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
	@Schema(implementation = Express.class)
	Express express,
	@NotNull
	LocalTime arrivalTime,
	@NotNull
	String startStationName,
	@NotNull
	String endStationName
) {
	public static SubwayTimetableRespDto of(SubwayTimetableEntity subwayTimetableEntity) {
		return new SubwayTimetableRespDto(
			subwayTimetableEntity.getSubwayStation().getId(),
			subwayTimetableEntity.getStationName(),
			subwayTimetableEntity.getLine(),
			subwayTimetableEntity.getDayType(),
			subwayTimetableEntity.getUpdown(),
			subwayTimetableEntity.getExpress(),
			subwayTimetableEntity.getArrivalTime(),
			subwayTimetableEntity.getStartStationName(),
			subwayTimetableEntity.getEndStationName());
	}
}
