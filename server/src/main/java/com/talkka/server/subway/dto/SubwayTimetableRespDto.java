package com.talkka.server.subway.dto;

import java.time.LocalTime;

import com.talkka.server.subway.dao.SubwayTimetableEntity;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Express;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;

import lombok.Builder;

@Builder
public record SubwayTimetableRespDto(
	Long stationId,
	String stationName,
	Line line,
	DayType dayType,
	Updown updown,
	Express express,
	LocalTime arrivalTime,
	String startStationName,
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
