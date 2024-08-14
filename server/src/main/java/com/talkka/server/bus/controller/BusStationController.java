package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

	@GetMapping("")
	public ResponseEntity<ApiRespDto<List<BusStationRespDto>>> getStations(
		@RequestParam(value = "search", required = false) String stationName) {
		List<BusStationRespDto> stationList;

		if (stationName != null) {
			stationList = stationService.getStationsByStationName(stationName);
		} else {
			stationList = stationService.getStations();
		}
		return ResponseEntity.ok(
			ApiRespDto.<List<BusStationRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(stationList)
				.build()
		);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiRespDto<BusStationRespDto>> getStationById(@PathVariable("id") Long stationId) {
		return ResponseEntity.ok(
			ApiRespDto.<BusStationRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(stationService.getStationById(stationId))
				.build()
		);
	}
}
