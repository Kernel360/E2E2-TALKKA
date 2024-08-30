package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.admin.util.CollectedRouteProvider;
import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.dto.BusLocationBodyDto;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.util.ApiCallNumberProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BlockedApiLocationCollectService implements BusLocationCollectService {
	private final CollectedRouteProvider collectedRouteProvider;
	private final ApiCallNumberProvider apiCallNumberProvider;
	private final BusApiService busApiService;
	private final BusLocationService busLocationService;

	public BlockedApiLocationCollectService(
		@Qualifier("persistenceCollectedRouteProvider")
		CollectedRouteProvider collectedRouteProvider,
		@Qualifier("minuteApiCallNumberProvider")
		ApiCallNumberProvider apiCallNumberProvider,
		@Qualifier("simpleBusApiService")
		BusApiService busApiService,
		@Qualifier("busLocationService")
		BusLocationService busLocationService
	) {
		this.collectedRouteProvider = collectedRouteProvider;
		this.apiCallNumberProvider = apiCallNumberProvider;
		this.busApiService = busApiService;
		this.busLocationService = busLocationService;
	}

	@Override
	@Transactional
	public void collectLocations() {
		List<String> apiRouteIdList = collectedRouteProvider.getTargetIdList();
		if (apiRouteIdList.isEmpty()) {
			log.info("No target routeId to collect bus locations");
			return;
		}
		apiRouteIdList.forEach(this::collectLocationsByRouteId);
	}

	@Override
	@Transactional
	public void collectLocationsByRouteId(String apiRouteId) throws ApiClientException {
		log.info("Collecting bus locations for routeId: {}", apiRouteId);
		Integer apiCallNo = apiCallNumberProvider.getApiCallNumber();
		LocalDateTime createdAt = LocalDateTime.now();
		List<BusLocationBodyDto> responseList = busApiService.getBusLocationInfo(apiRouteId);
		busLocationService.saveBusLocations(responseList, apiCallNo, createdAt);
	}
}
