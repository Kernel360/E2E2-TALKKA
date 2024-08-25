package com.talkka.server.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.admin.dao.CollectBusRouteRepository;
import com.talkka.server.admin.dto.CollectBusRouteCreateDto;
import com.talkka.server.admin.dto.CollectBusRouteRespDto;
import com.talkka.server.admin.exception.CollectBusRouteAlreadyExistsException;
import com.talkka.server.admin.exception.CollectBusRouteNotFoundException;
import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.exception.BusRouteNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectBusRouteService {
	private final CollectBusRouteRepository collectBusRouteRepository;
	private final BusRouteRepository busRouteRepository;

	public CollectBusRouteRespDto createCollectBusRoute(CollectBusRouteCreateDto dto) throws
		CollectBusRouteNotFoundException,
		CollectBusRouteAlreadyExistsException {
		if (collectBusRouteRepository.findByRouteId(dto.routeId()).isPresent()) {
			throw new CollectBusRouteAlreadyExistsException();
		}
		BusRouteEntity route = busRouteRepository.findById(dto.routeId())
			.orElseThrow(() -> new BusRouteNotFoundException(dto.routeId()));
		return CollectBusRouteRespDto.of(collectBusRouteRepository.save(dto.toEntity(route)));
	}

	public List<CollectBusRouteRespDto> findAllCollectBusRoutes() {
		return collectBusRouteRepository.findAll().stream().map(CollectBusRouteRespDto::of).toList();
	}

	public void deleteCollectBusRoute(Long collectRouteId) throws CollectBusRouteNotFoundException {
		if (collectBusRouteRepository.findById(collectRouteId).isEmpty()) {
			throw new CollectBusRouteNotFoundException();
		}
		collectBusRouteRepository.deleteById(collectRouteId);
	}
}
