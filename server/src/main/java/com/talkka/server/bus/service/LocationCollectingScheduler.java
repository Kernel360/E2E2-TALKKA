package com.talkka.server.bus.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.talkka.server.admin.util.CollectedRouteProvider;
import com.talkka.server.bus.util.LocationCollectingSchedulerConfigProperty;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@EnableScheduling
public class LocationCollectingScheduler {
	private final LocationCollectingSchedulerConfigProperty locationCollectingSchedulerConfigProperty;
	private final BusLocationCollectService busLocationCollectService;
	@Autowired
	private CollectedRouteProvider collectedRouteProvider;

	public LocationCollectingScheduler(
		@Qualifier("locationCollectingSchedulerConfigProperty")
		LocationCollectingSchedulerConfigProperty locationCollectingSchedulerConfigProperty,
		@Qualifier("blockedApiLocationCollectService")
		BusLocationCollectService busLocationCollectService
	) {
		this.locationCollectingSchedulerConfigProperty = locationCollectingSchedulerConfigProperty;
		this.busLocationCollectService = busLocationCollectService;
	}

	// 병렬 버스 위치 api 호출 메소드
	@Scheduled(fixedRate = 1000 * 60) // per minute
	public void runParallelLocationScheduler() {
		if (isEnabled()) {
			List<String> targetList = collectedRouteProvider.getTargetIdList();
			ExecutorService executor = Executors.newFixedThreadPool(20);

			// CompletableFuture 리스트 생성
			List<CompletableFuture<Void>> futures = targetList.stream()
				.map(targetId -> CompletableFuture.runAsync(() -> {
					try {
						busLocationCollectService.collectLocationsByRouteId(targetId);
					} catch (Exception e) {
						// 재시도 후에도 실패한 경우, 로그 남기기
						log.error(" 버스 위치 저장 실패 {} : {}", targetId, e.getMessage());
					}
				}, executor))
				.toList();

			// 모든 작업이 완료될 때까지 기다림
			CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

			// ExecutorService 종료
			executor.shutdown();
			try {
				if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
					executor.shutdownNow();
				}
			} catch (InterruptedException e) {
				executor.shutdownNow();
				Thread.currentThread().interrupt();
			}
		}
	}

	// 순차적 버스 위치 api 호출 메소드
	// @Scheduled(fixedRate = 1000 * 10) // per minute
	// public void runLocationScheduler() {
	// 	if (isEnabled()) {
	// 		busLocationCollectProvider.getTargetIdList()
	// 			.forEach(targetId -> {
	// 				try {
	// 					retryCollectLocations(targetId, 3);
	// 				} catch (InterruptedException e) {
	// 					log.error("{} : {}", targetId, e.getMessage());
	// 				}
	// 			});
	// 	}
	// }

	private boolean isEnabled() {
		if (!locationCollectingSchedulerConfigProperty.isEnabled()) {
			return false;
		}
		String now = String.format("%02d:00", LocalDateTime.now().getHour());
		return locationCollectingSchedulerConfigProperty.getEnabledTime().contains(now);
	}
}
