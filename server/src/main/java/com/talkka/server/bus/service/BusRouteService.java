package com.talkka.server.bus.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dto.BusRouteCreateDto;
import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.bus.exception.BusRouteNotFoundException;
import com.talkka.server.bus.exception.RouteAlreadyExistsException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusRouteService {
	private final BusRouteRepository busRouteRepository;

	public BusRouteRespDto getRouteById(Long routeId) throws BusRouteNotFoundException {
		BusRouteEntity busRouteEntity = busRouteRepository.findById(routeId)
			.orElseThrow(() -> new BusRouteNotFoundException(routeId));
		return BusRouteRespDto.of(busRouteEntity);
	}

	public List<BusRouteRespDto> getRoutes() {
		return busRouteRepository.findAll().stream()
			.map(BusRouteRespDto::of)
			.toList();
	}

	public List<BusRouteRespDto> getRoutesByRouteName(String routeName) {
		return busRouteRepository.findAllByRouteNameStartingWithOrderByRouteNameAsc(routeName).stream()
			.map(BusRouteRespDto::of)
			.toList();
	}

	// TODO 아직 컨트롤러 없음, 관리자만 가능한 로직 필요
	public BusRouteRespDto createRoute(BusRouteCreateDto busRouteCreateDto)
		throws RouteAlreadyExistsException {
		String apiRouteId = busRouteCreateDto.apiRouteId();
		if (!busRouteRepository.existsByApiRouteId(apiRouteId)) {
			throw new RouteAlreadyExistsException(apiRouteId);
		}

		return BusRouteRespDto.of(busRouteRepository.save(busRouteCreateDto.toEntity()));
	}

}
