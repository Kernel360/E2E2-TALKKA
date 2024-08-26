package com.talkka.server.bus.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.bus.util.LocationCollectingSchedulerConfigProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class LocationCollectingScheduler {
	private final LocationCollectingSchedulerConfigProperty locationCollectingSchedulerConfigProperty;
	private final BusLocationCollectService busLocationCollectService;

	public LocationCollectingScheduler(
		@Qualifier("locationCollectingSchedulerConfigProperty")
		LocationCollectingSchedulerConfigProperty locationCollectingSchedulerConfigProperty,
		@Qualifier("blockedApiLocationCollectService")
		BusLocationCollectService busLocationCollectService
	) {
		this.locationCollectingSchedulerConfigProperty = locationCollectingSchedulerConfigProperty;
		this.busLocationCollectService = busLocationCollectService;
	}

	@Transactional
	@Scheduled(fixedRate = 1000 * 60) // per minute
	public void runLocationScheduler() {
		if (isEnabled()) {
			busLocationCollectService.collectLocations();
		}
	}

	private boolean isEnabled() {
		if (!locationCollectingSchedulerConfigProperty.isEnabled()) {
			return false;
		}
		String now = String.format("%02d:00", LocalDateTime.now().getHour());
		return locationCollectingSchedulerConfigProperty.getEnabledTime().contains(now);
	}
}
