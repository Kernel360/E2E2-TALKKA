package com.talkka.server.bus.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.service.BusLiveInfoService;
import com.talkka.server.common.dto.ErrorRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/live")
public class BusLiveInfoController implements BusLiveInfoApi {
	private final BusLiveInfoService busLiveInfoService;

	@Override
	@GetMapping("/{routeStationId}")
	public ResponseEntity<?> getBusLiveInfo(@PathVariable Long routeStationId) {
		ResponseEntity<?> response;
		try {
			var busLiveInfo = busLiveInfoService.getBusLiveInfo(routeStationId);
			response = ResponseEntity.ok(busLiveInfo);
		} catch (BusRouteStationNotFoundException exception) {
			response = ResponseEntity.badRequest().body(ErrorRespDto.of(exception));
		}
		return response;
	}
}
