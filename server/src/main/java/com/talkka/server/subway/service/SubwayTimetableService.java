package com.talkka.server.subway.service;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dao.SubwayTimetableEntity;
import com.talkka.server.subway.dao.SubwayTimetableRepository;
import com.talkka.server.subway.dto.SubwayTimetableRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.exception.TimetableNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayTimetableService {

	private final SubwayTimetableRepository timetableRepository;
	private final SubwayStationRepository stationRepository;

	public SubwayTimetableRespDto getTimetable(
		Long stationId, String dayTypeCode, String updownCode, String time
	) throws DateTimeParseException, StationNotFoundException, TimetableNotFoundException, InvalidTypeException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
		LocalTime parseTime = LocalTime.parse(time, formatter);

		if (!stationRepository.existsById(stationId)) {
			throw new StationNotFoundException(stationId);
		}

		SubwayTimetableEntity entity = timetableRepository.findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTime(
			stationId, DayType.valueOfEnumString(dayTypeCode), Updown.valueOfEnumString(updownCode), parseTime
		).orElseThrow(TimetableNotFoundException::new);

		return SubwayTimetableRespDto.of(entity);
	}

	public List<SubwayTimetableRespDto> getTimetableList(
		Long stationId, String dayTypeCode, String updownCode, String startTime, String endTime
	) throws DateTimeParseException, StationNotFoundException, InvalidTypeException {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm:ss");
		LocalTime start = LocalTime.parse(startTime, formatter);
		LocalTime end = LocalTime.parse(endTime, formatter);

		if (!stationRepository.existsById(stationId)) {
			throw new StationNotFoundException(stationId);
		}

		List<SubwayTimetableEntity> entityList = timetableRepository.findBySubwayStationIdAndDayTypeAndUpdownAndArrivalTimeBetween(
			stationId, DayType.valueOfEnumString(dayTypeCode), Updown.valueOfEnumString(updownCode), start, end
		);

		return entityList.stream()
			.map(SubwayTimetableRespDto::of)
			.toList();
	}
}
