package com.talkka.server.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.bus.dao.BusStationEntity;
import com.talkka.server.bus.dao.BusStationRepository;
import com.talkka.server.bus.dto.BusRouteStationCreateDto;
import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.common.exception.http.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusRouteStationService {

	private final BusRouteRepository routeRepository;
	private final BusStationRepository stationRepository;
	private final BusRouteStationRepository routeStationRepository;

	public BusRouteStationRespDto createRouteStation(BusRouteStationCreateDto busRouteStationCreateDto) {
		BusRouteEntity routeEntity = routeRepository.findByApiRouteId(busRouteStationCreateDto.apiRouteId())
			.orElseThrow(() -> new BadRequestException("존재하지 않는 노선입니다."));
		BusStationEntity stationEntity = stationRepository.findByApiStationId(
				busRouteStationCreateDto.apiStationId())
			.orElseThrow(() -> new BadRequestException("존재하지 않는 정류장입니다."));
		BusRouteStationEntity busRouteStationEntity = busRouteStationCreateDto.toEntity(routeEntity, stationEntity);
		return BusRouteStationRespDto.of(routeStationRepository.save(busRouteStationEntity));
	}

	public BusRouteStationRespDto getRouteStationById(Long id) {
		BusRouteStationEntity busRouteStationEntity = routeStationRepository.findById(id)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 노선정류장입니다."));
		return BusRouteStationRespDto.of(busRouteStationEntity);
	}

	public List<BusRouteStationRespDto> getRouteStationsByRouteIdAndStationId(Long routeId, Long stationId) {
		return routeStationRepository.findAllByRouteIdAndStationId(routeId, stationId).stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}

	public List<BusRouteStationRespDto> getRouteStationsByRouteId(Long routeId) {
		return routeStationRepository.findAllByRouteId(routeId).stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}

	public List<BusRouteStationRespDto> getRouteStationsByStationId(Long stationId) {
		return routeStationRepository.findAllByStationId(stationId).stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}

	public List<BusRouteStationRespDto> getRouteStations() {
		return routeStationRepository.findAll().stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}
}
