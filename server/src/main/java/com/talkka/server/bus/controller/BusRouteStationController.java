package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.exception.BusRouteStationNotFoundException;
import com.talkka.server.bus.service.BusRouteStationService;
import com.talkka.server.common.dto.ErrorRespDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/route-station")
public class BusRouteStationController implements BusRouteStationApi {
	private final BusRouteStationService routeStationService;

	@Override
	@GetMapping("/{id}")
	public ResponseEntity<?> getRouteStationById(
		@PathVariable("id") Long routeStationId) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(routeStationService.getRouteStationById(routeStationId));
		} catch (BusRouteStationNotFoundException exception) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorRespDto.of(exception));
		}
		return response;
	}

	@Override
	@GetMapping("")
	public ResponseEntity<List<BusRouteStationRespDto>> getRouteStations(
		@RequestParam(value = "routeId", required = false) Long routeId,
		@RequestParam(value = "stationId", required = false) Long stationId) {
		List<BusRouteStationRespDto> routeStationList;

		if (routeId != null && stationId != null) {
			routeStationList = routeStationService.getRouteStationsByRouteIdAndStationId(routeId, stationId);
		} else if (routeId != null) {
			routeStationList = routeStationService.getRouteStationsByRouteId(routeId);
		} else if (stationId != null) {
			routeStationList = routeStationService.getRouteStationsByStationId(stationId);
		} else {
			routeStationList = routeStationService.getRouteStations();
		}
		return ResponseEntity.ok(routeStationList);
	}
}
