package com.talkka.server.bus.service;

import static org.mockito.BDDMockito.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.talkka.server.bus.util.LocationCollectingSchedulerConfigProperty;

@ExtendWith(MockitoExtension.class)
class LocationCollectingSchedulerTest {
	@InjectMocks
	private LocationCollectingScheduler locationCollectingScheduler;
	@Mock
	private LocationCollectingSchedulerConfigProperty locationCollectingSchedulerConfigProperty;
	@Mock
	private BusLocationCollectService busLocationCollectService;

	@Test
	@DisplayName("스케줄러가 비활성화 되어있을 때 위치정보를 수집하지 않는다.")
	void runLocationScheduler() {
		// given
		given(locationCollectingSchedulerConfigProperty.isEnabled()).willReturn(false);
		// when
		locationCollectingScheduler.runLocationScheduler();
		// then
		verify(locationCollectingSchedulerConfigProperty).isEnabled();
		verifyNoInteractions(busLocationCollectService);
	}

	@Test
	@DisplayName("스케줄러가 활성화 되어있을 때 위치정보를 수집한다.")
	void runLocationSchedulerWhenEnabled() {
		// given
		given(locationCollectingSchedulerConfigProperty.isEnabled()).willReturn(true);
		given(locationCollectingSchedulerConfigProperty.getEnabledTime()).willReturn(List.of(
			"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", "07:00", "08:00", "09:00",
			"10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", "19:00",
			"20:00", "21:00", "22:00", "23:00"
		));
		// when
		locationCollectingScheduler.runLocationScheduler();
		// then
		verify(locationCollectingSchedulerConfigProperty).isEnabled();
		verify(busLocationCollectService).collectLocations();
	}

	@Test
	@DisplayName("스케줄러가 활성화 되어있지만 현재 시간이 활성화 시간이 아닐 때 위치정보를 수집하지 않는다.")
	void runLocationSchedulerWhenEnabledButNotEnabledTime() {
		// given
		given(locationCollectingSchedulerConfigProperty.isEnabled()).willReturn(true);
		given(locationCollectingSchedulerConfigProperty.getEnabledTime()).willReturn(List.of());
		// when
		locationCollectingScheduler.runLocationScheduler();
		// then
		verify(locationCollectingSchedulerConfigProperty).isEnabled();
		verifyNoInteractions(busLocationCollectService);
	}
}