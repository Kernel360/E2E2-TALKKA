package com.talkka.server.bus.service;

import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.admin.util.CollectedRouteProvider;
import com.talkka.server.api.core.exception.ApiClientException;
import com.talkka.server.api.datagg.service.BusApiService;
import com.talkka.server.bus.util.ApiCallNumberProvider;

@ExtendWith(MockitoExtension.class)
class BlockedApiLocationCollectServiceTest {
	@InjectMocks
	private BlockedApiLocationCollectService blockedApiLocationCollectService;

	@Mock
	private CollectedRouteProvider collectedRouteProvider;
	@Mock
	private ApiCallNumberProvider apiCallNumberProvider;
	@Mock
	private BusApiService busApiService;
	@Mock
	private BusLocationService busLocationService;

	@Test
	@DisplayName("target list 에 따라 bus location 을 수집한다.")
	void collectLocations() {
		// given
		given(collectedRouteProvider.getTargetIdList()).willReturn(List.of("200000150"));
		given(apiCallNumberProvider.getApiCallNumber()).willReturn(1);
		given(busApiService.getBusLocationInfo(anyString())).willReturn(List.of());
		// when
		blockedApiLocationCollectService.collectLocations();
		// then
		verify(collectedRouteProvider).getTargetIdList();
		verify(apiCallNumberProvider).getApiCallNumber();
		verify(busApiService).getBusLocationInfo(anyString());
		verify(busLocationService).saveBusLocations(anyList(), anyInt(), any());
	}

	@Test
	@DisplayName("target list 이 비어있을 때 bus location 을 수집하지 않는다.")
	void collectLocationsWhenTargetListIsEmpty() {
		// given
		given(collectedRouteProvider.getTargetIdList()).willReturn(List.of());
		// when
		blockedApiLocationCollectService.collectLocations();
		// then
		verify(collectedRouteProvider).getTargetIdList();
		verifyNoInteractions(apiCallNumberProvider, busApiService, busLocationService);
	}

	@Test
	@DisplayName("bus location 을 수집하는 도중 api client exception 이 발생하면 로그를 남긴다.")
	void collectLocationsWhenApiClientExceptionOccurs() {
		// given
		given(collectedRouteProvider.getTargetIdList()).willReturn(List.of("200000150"));
		given(apiCallNumberProvider.getApiCallNumber()).willReturn(1);
		given(busApiService.getBusLocationInfo(anyString())).willThrow(new ApiClientException("API client exception"));
		// when
		blockedApiLocationCollectService.collectLocations();
		// then
		verify(collectedRouteProvider).getTargetIdList();
		verify(apiCallNumberProvider).getApiCallNumber();
		verify(busApiService).getBusLocationInfo(anyString());
		verifyNoInteractions(busLocationService);
	}
}