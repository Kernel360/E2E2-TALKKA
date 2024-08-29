package com.talkka.server.bus.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.dao.BusPlateStatisticEntity;
import com.talkka.server.bus.dao.BusPlateStatisticRepository;
import com.talkka.server.bus.dto.BusArrivalRespDto;
import com.talkka.server.bus.enums.PlateType;
import com.talkka.server.common.util.CachedStorage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CachedBusArrivalService implements BusArrivalService {
	private final BusApiService busApiService;
	private final BusPlateStatisticRepository busPlateStatisticRepository;
	private final CachedStorage<Long, BusArrivalRespDto> arrivalCache;

	@Override
	public Optional<BusArrivalRespDto> getBusArrivalInfo(Long routeStationId, String apiRouteId,
		String apiStationId) {
		try {
			var cached = arrivalCache.get(routeStationId);
			if (cached.isPresent()) {
				return cached;
			}

			var arrivalInfo = busApiService.getBusArrival(apiRouteId, apiStationId).orElse(null);
			if (arrivalInfo == null) {
				return Optional.empty();
			}

			var result = BusArrivalRespDto.of(arrivalInfo, getPlateType(arrivalInfo.plateNo1()),
				getPlateType(arrivalInfo.plateNo2()));
			arrivalCache.put(routeStationId, result);
			return Optional.of(result);
		} catch (ApiClientException exception) {
			log.error("Failed to get bus arrival info", exception);
			return Optional.empty();
		}
	}

	private PlateType getPlateType(String plateNo) {
		return busPlateStatisticRepository.findFirstByPlateNo(plateNo)
			.map(BusPlateStatisticEntity::getPlateType)
			.orElse(PlateType.UNKNOWN);
	}
}
