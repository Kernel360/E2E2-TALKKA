package com.talkka.server.bus.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.dto.BusArrivalRespDto;
import com.talkka.server.common.util.CachedStorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CachedBusArrivalService implements BusArrivalService {
	private final BusApiService busApiService;
	private final CachedStorage<Long, BusArrivalRespDto> arrivalCache;

	@Override
	public Optional<BusArrivalRespDto> getBusArrivalInfo(Long routeStationId, String apiRouteId,
		String apiStationId) {
		try {
			var cached = arrivalCache.get(routeStationId);
			if (cached.isPresent()) {
				return cached;
			}

			var arrivalInfo = busApiService.getBusArrival(apiRouteId, apiStationId)
				.flatMap(BusArrivalRespDto::of);
			arrivalInfo.ifPresent(busLiveArrivalRespDto -> arrivalCache.put(routeStationId, busLiveArrivalRespDto));
			return arrivalInfo;
		} catch (ApiClientException exception) {
			log.error("Failed to get bus arrival info", exception);
			return Optional.empty();
		}
	}
}
