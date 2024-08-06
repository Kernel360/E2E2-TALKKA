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

	public BusRouteStationRespDto createBusRouteStation(BusRouteStationCreateDto busRouteStationCreateDto) {
		BusRouteEntity routeEntity = routeRepository.findByApiRouteId(busRouteStationCreateDto.getApiRouteId())
			.orElseThrow(() -> new BadRequestException("존재하지 않는 노선입니다."));
		BusStationEntity stationEntity = stationRepository.findByApiStationId(
				busRouteStationCreateDto.getApiStationId())
			.orElseThrow(() -> new BadRequestException("존재하지 않는 정류장입니다."));
		BusRouteStationEntity busRouteStationEntity = BusRouteStationEntity.builder()
			.route(routeEntity)
			.station(stationEntity)
			.stationSeq(busRouteStationCreateDto.getStationSeq())
			.stationName(busRouteStationCreateDto.getStationName())
			.build();
		return BusRouteStationRespDto.of(routeStationRepository.save(busRouteStationEntity));
	}

	public BusRouteStationRespDto findById(Long id) {
		BusRouteStationEntity busRouteStationEntity = routeStationRepository.findById(id)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 노선정류장입니다."));
		return BusRouteStationRespDto.of(busRouteStationEntity);
	}

	public List<BusRouteStationRespDto> findByRouteId(Long routeId) {
		return routeStationRepository.findByRouteId(routeId).stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}

	public List<BusRouteStationRespDto> findByStationId(Long stationId) {
		return routeStationRepository.findByStationId(stationId).stream()
			.map(BusRouteStationRespDto::of)
			.toList();
	}
}
