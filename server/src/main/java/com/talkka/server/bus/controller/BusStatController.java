package com.talkka.server.bus.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusStatReqDto;
import com.talkka.server.bus.dto.BusStatRespDto;
import com.talkka.server.bus.service.BusStatService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/stat")
public class BusStatController {
	private final BusStatService busStatService;

	@GetMapping("")
	public ResponseEntity<List<BusStatRespDto>> getBusStats(
		@RequestParam(name = "routeId", required = false) Long routeId,
		@RequestParam(name = "stationId", required = false) Long stationId,
		@RequestParam(name = "startDateTime", required = false) String startDateTime,
		@RequestParam(name = "endDateTime", required = false) String endDateTime
	) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = LocalDateTime.parse(startDateTime, formatter);
		LocalDateTime end = LocalDateTime.parse(endDateTime, formatter);
		BusStatReqDto statReqDto = new BusStatReqDto(
			routeId,
			stationId,
			start,
			end
		);
		List<BusStatRespDto> statList = busStatService.getBusStat(statReqDto);
		return ResponseEntity.ok(statList);
	}

	// 현재 시간 전후 30분 구간의
	@GetMapping("/now")
	public ResponseEntity<List<BusStatRespDto>> getBusStatsNow(
		@RequestParam(name = "routeId", required = false) Long routeId,
		@RequestParam(name = "stationId", required = false) Long stationId
	) {
		List<BusStatRespDto> statList = busStatService.getBusStatNow(routeId, stationId);
		return ResponseEntity.ok(statList);
	}

	@PostMapping("/makeStatData")
	public String makeStatData() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime start = LocalDateTime.parse("2000-01-01 00:00", formatter);
		LocalDateTime end = LocalDateTime.parse("2100-12-31 23:59", formatter);
		busStatService.makeStatDataBetween(start, end);
		return "success";
	}
}
