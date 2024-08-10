package com.talkka.server.subway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.common.exception.http.BadRequestException;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayStationReqDto;
import com.talkka.server.subway.dto.SubwayStationRespDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayStationService {

	private final SubwayStationRepository stationRepository;

	public SubwayStationRespDto findByStationId(Long stationId) {
		SubwayStationEntity entity = stationRepository.findById(stationId)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 역입니다."));

		return SubwayStationRespDto.of(entity);
	}

	public List<SubwayStationRespDto> findByStationName(String stationName) {
		return stationRepository.findByStationNameLikeOrderByStationNameAsc(stationName).stream()
			.map(SubwayStationRespDto::of)
			.toList();
	}

	public SubwayStationRespDto saveStation(SubwayStationReqDto reqDto) {
		if (stationRepository.existsByApiStationId(reqDto.apiStationId())) {
			throw new BadRequestException("이미 존재하는 지하철 역입니다");
		}

		return SubwayStationRespDto.of(stationRepository.save(SubwayStationReqDto.toEntity(reqDto)));
	}
}
