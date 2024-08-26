package com.talkka.server.bus.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.dto.BusLiveArrivalRespDto;
import com.talkka.server.bus.exception.GetBusLiveArrivalInfoFailedException;
import com.talkka.server.common.util.CachedStorage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusArrivalServiceImpl implements BusArrivalService {
	private final BusApiService busApiService;
	private final CachedStorage<Long, BusLiveArrivalRespDto> arrivalCache;

	@Override
	public Optional<BusLiveArrivalRespDto> getBusArrivalInfo(Long routeStationId, String apiRouteId,
		String apiStationId)
		throws GetBusLiveArrivalInfoFailedException {
		try {
			var cached = arrivalCache.get(routeStationId);
			if (cached.isPresent()) {
				return cached;
			}

			var arrivalInfo = busApiService.getBusArrival(apiRouteId, apiStationId)
				.flatMap(BusLiveArrivalRespDto::of);
			arrivalInfo.ifPresent(busLiveArrivalRespDto -> arrivalCache.put(routeStationId, busLiveArrivalRespDto));
			return arrivalInfo;
		} catch (ApiClientException exception) {
			throw new GetBusLiveArrivalInfoFailedException();
		}
	}
}
