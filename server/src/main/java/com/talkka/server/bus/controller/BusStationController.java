package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.bus.service.BusStationService;
import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/station")
public class BusStationController {
	private final BusStationService stationService;

	@GetMapping("/name/{name}")
	public ResponseEntity<ApiRespDto<List<BusStationRespDto>>> findByStationName(
		@RequestParam("name") String stationName) {
		return ResponseEntity.ok(
			ApiRespDto.<List<BusStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(stationService.findByStationName(stationName))
				.build()
		);
	}

	@GetMapping("/id/{id}")
	public ResponseEntity<ApiRespDto<BusStationRespDto>> findByStationId(@RequestParam("id") Long stationId) {
		return ResponseEntity.ok(
			ApiRespDto.<BusStationRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(stationService.findByStationId(stationId))
				.build()
		);
	}
}
