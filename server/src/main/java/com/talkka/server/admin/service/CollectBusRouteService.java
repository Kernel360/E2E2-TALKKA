package com.talkka.server.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.admin.dao.CollectBusRouteRepository;
import com.talkka.server.admin.dto.CollectBusRouteCreateDto;
import com.talkka.server.admin.dto.CollectBusRouteRespDto;
import com.talkka.server.admin.exception.CollectBusRouteAlreadyExistsException;
import com.talkka.server.admin.exception.CollectBusRouteNotFoundException;
import com.talkka.server.admin.util.CollectedRouteProvider;
import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.exception.BusRouteNotFoundException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CollectBusRouteService {
	private final CollectedRouteProvider collectedRouteProvider;
	private final CollectBusRouteRepository collectBusRouteRepository;
	private final BusRouteRepository busRouteRepository;

	public CollectBusRouteRespDto createCollectBusRoute(CollectBusRouteCreateDto dto) throws
		CollectBusRouteNotFoundException,
		CollectBusRouteAlreadyExistsException {
		if (collectBusRouteRepository.existsByRouteId(dto.routeId())) {
			throw new CollectBusRouteAlreadyExistsException();
		}
		BusRouteEntity route = busRouteRepository.findById(dto.routeId())
			.orElseThrow(() -> new BusRouteNotFoundException(dto.routeId()));
		collectedRouteProvider.getTargetIdList().add(route.getApiRouteId());
		return CollectBusRouteRespDto.of(collectBusRouteRepository.save(dto.toEntity(route)));
	}

	public List<CollectBusRouteRespDto> findAllCollectBusRoutes() {
		return collectBusRouteRepository.findAll().stream().map(CollectBusRouteRespDto::of).toList();
	}

	public void deleteCollectBusRoute(Long collectRouteId) throws CollectBusRouteNotFoundException {
		var entity = collectBusRouteRepository.findById(collectRouteId);
		if (entity.isPresent()) {
			collectBusRouteRepository.delete(entity.get());
			collectedRouteProvider.getTargetIdList().remove(entity.get().getApiRouteId());
		} else {
			throw new CollectBusRouteNotFoundException();
		}
	}
}
