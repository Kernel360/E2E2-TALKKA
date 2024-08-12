package com.talkka.server.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.dto.BusStationCreateDto;
import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.common.exception.http.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusStationService {
	private final BusStationRepository busStationRepository;

	public BusStationRespDto getStationById(Long stationId) {
		BusStationEntity busStationEntity = busStationRepository.findById(stationId)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 정거장입니다."));
		return BusStationRespDto.of(busStationEntity);
	}

	public List<BusStationRespDto> getStationsByStationName(String stationName) {
		return busStationRepository.findByStationNameLikeOrderByStationNameAsc(stationName).stream()
			.map(BusStationRespDto::of)
			.toList();
	}

	public List<BusStationRespDto> getStations() {
		return busStationRepository.findAll().stream()
			.map(BusStationRespDto::of)
			.toList();
	}

	public BusStationRespDto createStation(BusStationCreateDto busStationCreateDto) {
		if (busStationRepository.existsByApiStationId(busStationCreateDto.apiStationId())) {
			throw new BadRequestException("이미 등록된 정거장입니다.");
		}
		return BusStationRespDto.of(busStationRepository.save(busStationCreateDto.toEntity()));
	}
}
