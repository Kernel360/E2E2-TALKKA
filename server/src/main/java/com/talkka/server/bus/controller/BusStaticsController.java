package com.talkka.server.bus.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusStaticsDto;
import com.talkka.server.bus.service.BusStaticsService;

import lombok.RequiredArgsConstructor;

// 테스트용 컨트롤러입니다.
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/statics")
public class BusStaticsController {
	private final BusStaticsService busStaticsService;

	@GetMapping("/now")
	public ResponseEntity<BusStaticsDto> getStatics(
		@RequestParam(name = "routeStationId", required = true) Long routeStationId,
		@RequestParam(name = "stationNum", required = false, defaultValue = "5") Integer stationNum,
		@RequestParam(name = "timeRange", required = false, defaultValue = "10") Integer timeRange,
		@RequestParam(name = "week", required = false, defaultValue = "3") Long week
	) {
		LocalDateTime time = LocalDateTime.now();
		return ResponseEntity.ok(
			busStaticsService.getRouteStationStatics(routeStationId, stationNum, time, timeRange, week));
	}

}
