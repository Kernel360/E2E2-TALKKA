package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.bus.service.BusRouteStationService;
import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@RequestMapping("/api/bus/route-station")
public class BusRouteStationController {
	private final BusRouteStationService routeStationService;

	@GetMapping("/id/{id}")
	public ResponseEntity<ApiRespDto<List<BusRouteStationRespDto>>> findById(
		@PathVariable("id") Long routeStationId) {
		return ResponseEntity.ok(
			ApiRespDto.<List<BusRouteStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeStationService.findByStationId(routeStationId))
				.build()
		);
	}

	@GetMapping("/routeId/{routeId}")
	public ResponseEntity<ApiRespDto<List<BusRouteStationRespDto>>> findByRouteId(
		@RequestParam("routeId") Long routeId) {
		return ResponseEntity.ok(
			ApiRespDto.<List<BusRouteStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeStationService.findByRouteId(routeId))
				.build()
		);
	}

	@GetMapping("/stationId/{stationId}")
	public ResponseEntity<ApiRespDto<List<BusRouteStationRespDto>>> findByStationId(
		@RequestParam("stationId") Long stationId) {
		return ResponseEntity.ok(
			ApiRespDto.<List<BusRouteStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeStationService.findByStationId(stationId))
				.build()
		);
	}
}
