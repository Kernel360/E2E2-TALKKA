package com.talkka.server.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.dto.BusStationCreateDto;
import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.bus.exception.BusStationAlreadyExistsException;
import com.talkka.server.bus.exception.BusStationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusStationService {
	private final BusStationRepository busStationRepository;

	public BusStationRespDto getStationById(Long stationId) throws BusStationNotFoundException {
		BusStationEntity busStationEntity = busStationRepository.findById(stationId)
			.orElseThrow(() -> new BusStationNotFoundException(stationId));
		return BusStationRespDto.of(busStationEntity);
	}

	public List<BusStationRespDto> getStationsByStationName(String stationName) {
		return busStationRepository.findByStationNameStartingWithOrderByStationNameAsc(stationName).stream()
			.map(BusStationRespDto::of)
			.toList();
	}

	public List<BusStationRespDto> getStations() {
		return busStationRepository.findAll().stream()
			.map(BusStationRespDto::of)
			.toList();
	}

	// TODO 아직 controller 없음
	public BusStationRespDto createStation(BusStationCreateDto busStationCreateDto)
		throws BusStationAlreadyExistsException {
		String apiStationId = busStationCreateDto.apiStationId();
		if (busStationRepository.existsByApiStationId(apiStationId)) {
			throw new BusStationAlreadyExistsException(apiStationId);
		}

		return BusStationRespDto.of(busStationRepository.save(busStationCreateDto.toEntity()));
	}
}
