package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.util.ApiCallNumberProvider;
import com.talkka.server.bus.util.BusLocationCollectProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlockedApiLocationCollectService implements BusLocationCollectService {
	private final BusLocationCollectProvider busLocationCollectProvider;
	private final ApiCallNumberProvider apiCallNumberProvider;
	private final BusApiService busApiService;
	private final BusLocationService busLocationService;

	public BlockedApiLocationCollectService(
		@Qualifier("busLocationCollectProperty")
		BusLocationCollectProvider busLocationCollectProvider,
		@Qualifier("minuteApiCallNumberProvider")
		ApiCallNumberProvider apiCallNumberProvider,
		@Qualifier("simpleBusApiService")
		BusApiService busApiService,
		@Qualifier("busLocationService")
		BusLocationService busLocationService
	) {
		this.busLocationCollectProvider = busLocationCollectProvider;
		this.apiCallNumberProvider = apiCallNumberProvider;
		this.busApiService = busApiService;
		this.busLocationService = busLocationService;
	}

	@Override
	@Transactional
	public void collectLocations() {
		List<String> apiRouteIdList = busLocationCollectProvider.getTargetIdList();
		if (apiRouteIdList.isEmpty()) {
			log.info("No target routeId to collect bus locations");
			return;
		}

		Integer apiCallNo = apiCallNumberProvider.getApiCallNumber();
		LocalDateTime createdAt = LocalDateTime.now();
		for (String apiRouteId : apiRouteIdList) {
			log.info("Collecting bus locations for routeId: {}", apiRouteId);
			try { // should be refactored
				List<BusLocationBodyDto> responseList = busApiService.getBusLocationInfo(apiRouteId);
				busLocationService.saveBusLocations(responseList, apiCallNo, createdAt);
			} catch (ApiClientException apiClientException) {
				log.error("Failed to collect bus locations", apiClientException);
			}
		}
	}
}
