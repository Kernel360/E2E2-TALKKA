package com.talkka.server.bus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.service.BusLiveInfoService;
import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/live")
public class BusLiveInfoController implements BusLiveInfoApi {
	private final BusLiveInfoService busLiveInfoService;

	@Override
	@GetMapping("/{routeStationId}")
	public ResponseEntity<?> getBusLiveInfo(
		@PathVariable Long routeStationId,
		@RequestParam String timeSlot,
		@RequestParam Long week) {
		ResponseEntity<?> response;
		try {
			BusLiveInfoRespDto busLiveInfo = busLiveInfoService.getBusLiveInfo(routeStationId, timeSlot, week);
			response = ResponseEntity.ok(busLiveInfo);
		} catch (BusRouteStationNotFoundException | InvalidTimeSlotEnumException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		}
		return response;
	}
}
