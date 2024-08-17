package com.talkka.server.subway.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.dto.SubwayStationDto;
import com.talkka.server.subway.dto.SubwayStationRespDto;
import com.talkka.server.subway.exception.StationAlreadyExistsException;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.exception.enums.InvalidLineEnumException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayStationService {

	private final SubwayStationRepository stationRepository;

	public SubwayStationRespDto getStation(Long stationId) throws StationNotFoundException {
		SubwayStationEntity entity = stationRepository.findById(stationId)
			.orElseThrow(() -> new StationNotFoundException(stationId));

		return SubwayStationRespDto.of(entity);
	}

	public List<SubwayStationRespDto> getStationListByStationName(String stationName) {
		return stationRepository.findAllByStationNameStartingWithOrderByStationNameAsc(stationName).stream()
			.map(SubwayStationRespDto::of)
			.toList();
	}

	public List<SubwayStationRespDto> getStationList() {
		return stationRepository.findAllByOrderByStationNameAsc().stream()
			.map(SubwayStationRespDto::of)
			.toList();
	}

	public SubwayStationRespDto createStation(SubwayStationDto stationDto)
		throws StationAlreadyExistsException, InvalidLineEnumException {
		if (stationRepository.existsByStationCode(stationDto.stationCode())) {
			throw new StationAlreadyExistsException(stationDto.stationCode());
		}

		return SubwayStationRespDto.of(stationRepository.save(SubwayStationDto.toEntity(stationDto)));
	}
}
