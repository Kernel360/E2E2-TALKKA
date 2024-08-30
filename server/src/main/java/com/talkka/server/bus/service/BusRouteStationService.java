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
import com.talkka.server.bus.exception.BusRouteNotFoundException;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.exception.BusStationNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusRouteStationService {

	private final BusRouteRepository routeRepository;
	private final BusStationRepository stationRepository;
	private final BusRouteStationRepository routeStationRepository;

	// TODO controller에 추가 로직 없음, 추후 관리자가 추가할 수 있도록 추가 필요
	public BusRouteStationRespDto createRouteStation(BusRouteStationCreateDto busRouteStationCreateDto)
		throws BusRouteNotFoundException, BusStationNotFoundException {
		Long routeId = busRouteStationCreateDto.routeId();
		Long stationId = busRouteStationCreateDto.stationId();

		BusRouteEntity routeEntity = routeRepository.findById(routeId)
			.orElseThrow(() -> new BusRouteNotFoundException(routeId));
		BusStationEntity stationEntity = stationRepository.findById(stationId)
			.orElseThrow(() -> new BusStationNotFoundException(stationId));

		BusRouteStationEntity busRouteStationEntity = busRouteStationCreateDto.toEntity(routeEntity, stationEntity);
		return BusRouteStationRespDto.of(routeStationRepository.save(busRouteStationEntity));
	}

	public BusRouteStationRespDto getRouteStationById(Long id)
		throws BusRouteStationNotFoundException {
		BusRouteStationEntity busRouteStationEntity = routeStationRepository.findById(id)
			.orElseThrow(() -> new BusRouteStationNotFoundException(id));
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
