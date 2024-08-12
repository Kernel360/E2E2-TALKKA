package com.talkka.server.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dto.BusRouteCreateDto;
import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.common.exception.http.BadRequestException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusRouteService {
	private final BusRouteRepository busRouteRepository;

	public BusRouteRespDto getRouteById(Long routeId) {
		BusRouteEntity busRouteEntity = busRouteRepository.findById(routeId)
			.orElseThrow(() -> new BadRequestException("존재하지 않는 노선입니다."));
		return BusRouteRespDto.of(busRouteEntity);
	}

	public List<BusRouteRespDto> getRoutes() {
		return busRouteRepository.findAll().stream()
			.map(BusRouteRespDto::of)
			.toList();
	}

	public List<BusRouteRespDto> getRoutesByRouteName(String routeName) {
		return busRouteRepository.findAllByRouteNameLikeOrderByRouteNameAsc(routeName).stream()
			.map(BusRouteRespDto::of)
			.toList();
	}

	public BusRouteRespDto createRoute(BusRouteCreateDto busRouteCreateDto) {
		if (!busRouteRepository.existsByApiRouteId(busRouteCreateDto.apiRouteId())) {
			throw new BadRequestException("이미 등록된 버스 노선입니다.");
		}
		return BusRouteRespDto.of(busRouteRepository.save(busRouteCreateDto.toEntity()));
	}

}
