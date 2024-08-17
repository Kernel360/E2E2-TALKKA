package com.talkka.server.subway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.dao.SubwayConfusionRepository;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayConfusionRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.ConfusionNotFoundException;
import com.talkka.server.subway.exception.StationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayConfusionService {

	private final SubwayConfusionRepository confusionRepository;
	private final SubwayStationRepository stationRepository;

	// TODO 추후 통계쪽으로 넘길지 논의 필요
	public SubwayConfusionRespDto getConfusion(
		Long stationId, String dayType, String updown, String timeSlot
	) throws StationNotFoundException, ConfusionNotFoundException, InvalidTypeException {
		isExisted(stationId);

		SubwayConfusionEntity entity = confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
			stationId,
			DayType.valueOfEnumString(dayType),
			Updown.valueOfEnumString(updown),
			TimeSlot.valueOfEnumString(timeSlot)
		).orElseThrow(ConfusionNotFoundException::new);

		return SubwayConfusionRespDto.of(entity);
	}

	public List<SubwayConfusionRespDto> getConfusionList(
		Long stationId, String dayType, String updown, String startTimeSlot, String endTimeSlot
	) throws StationNotFoundException, InvalidTypeException {
		isExisted(stationId);

		List<SubwayConfusionEntity> entityList = confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlotBetween(
			stationId,
			DayType.valueOfEnumString(dayType),
			Updown.valueOfEnumString(updown),
			TimeSlot.valueOfEnumString(startTimeSlot),
			TimeSlot.valueOfEnumString(endTimeSlot)
		);

		return entityList.stream()
			.map(SubwayConfusionRespDto::of)
			.toList();
	}

	private void isExisted(Long stationId) throws StationNotFoundException {
		if (!stationRepository.existsById(stationId)) {
			throw new StationNotFoundException(stationId);
		}
	}

}
