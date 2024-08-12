package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.bus.service.BusRouteService;
import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/route")
public class BusRouteController {
	private final BusRouteService busRouteService;

	@GetMapping("")
	public ResponseEntity<ApiRespDto<List<BusRouteRespDto>>> getRoutes(
		@RequestParam(value = "search", required = false) String routeName) {
		List<BusRouteRespDto> routeList;

		if (routeName != null) {
			routeList = busRouteService.getRoutesByRouteName(routeName);
		} else {
			routeList = busRouteService.getRoutes();
		}
		return ResponseEntity.ok(
			ApiRespDto.<List<BusRouteRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(routeList)
				.build()
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiRespDto<BusRouteRespDto>> getRouteById(@PathVariable("id") Long routeId) {
		return ResponseEntity.ok(
			ApiRespDto.<BusRouteRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(busRouteService.getRouteById(routeId))
				.build()
		);
	}
}
