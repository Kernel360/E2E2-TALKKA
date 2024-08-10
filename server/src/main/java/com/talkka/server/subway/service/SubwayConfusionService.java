package com.talkka.server.subway.service;

import org.springframework.stereotype.Service;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.common.util.EnumCodeConverterUtils;
import com.talkka.server.subway.dao.SubwayConfusionEntity;
import com.talkka.server.subway.dao.SubwayConfusionRepository;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayConfusionRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayConfusionService {

	private final SubwayConfusionRepository confusionRepository;

	private final SubwayStationRepository stationRepository;

	// TODO 추후 통계쪽으로 넘길지 논의 필요
	public SubwayConfusionRespDto getSubwayConfusion(
		Long stationId, String dayTypeCode, String updowncode, String typeSlotCode
	) {
		if (!stationRepository.existsById(stationId)) {
			throw new NotFoundException("존재하지 않는 지하철 역입니다.");
		}

		SubwayConfusionEntity optionalConfusionEntity = confusionRepository.findBySubwayStationIdAndDayTypeAndUpdownAndTimeSlot(
			stationId,
			EnumCodeConverterUtils.fromCode(DayType.class, dayTypeCode),
			EnumCodeConverterUtils.fromCode(Updown.class, updowncode),
			EnumCodeConverterUtils.fromCode(TimeSlot.class, typeSlotCode)
		).orElseThrow(() -> new NotFoundException("조회 가능한 혼잡도 정보가 없습니다."));

		return SubwayConfusionRespDto.of(optionalConfusionEntity);
	}
}
