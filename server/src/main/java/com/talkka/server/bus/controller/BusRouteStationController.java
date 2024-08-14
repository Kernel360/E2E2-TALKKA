package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.service.BusRouteStationService;
import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/route-station")
public class BusRouteStationController {
	private final BusRouteStationService routeStationService;

	@GetMapping("/{id}")
	public ResponseEntity<ApiRespDto<BusRouteStationRespDto>> getRouteStationById(
		@PathVariable("id") Long routeStationId) {
		return ResponseEntity.ok(
			ApiRespDto.<BusRouteStationRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeStationService.getRouteStationById(routeStationId))
				.build()
		);
	}

	@GetMapping("")
	public ResponseEntity<ApiRespDto<List<BusRouteStationRespDto>>> getRouteStations(
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
		return ResponseEntity.ok(
			ApiRespDto.<List<BusRouteStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeStationList)
				.build()
		);
	}
}
