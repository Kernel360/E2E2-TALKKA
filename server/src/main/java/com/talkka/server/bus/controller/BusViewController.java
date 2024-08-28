package com.talkka.server.bus.controller;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusViewDto;
import com.talkka.server.bus.service.BusViewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/route-info")
public class BusViewController {
	private final BusViewService busViewService;

	@GetMapping("/now")
	public ResponseEntity<BusViewDto> getNow(
		@RequestParam(name = "routeStationId", required = false, defaultValue = "17499") Long routeStationId,
		@RequestParam(name = "stationNum", required = false, defaultValue = "5") Integer stationNum,
		@RequestParam(name = "timeRange", required = false, defaultValue = "45") Integer timeRange,
		@RequestParam(name = "week", required = false, defaultValue = "2") Long week
	) {
		LocalDateTime time = LocalDateTime.now().plusHours(8);
		return ResponseEntity.ok(busViewService.getBusView(routeStationId, stationNum, time, timeRange, week));
	}

}
