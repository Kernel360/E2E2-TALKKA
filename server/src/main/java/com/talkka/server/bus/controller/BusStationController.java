package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.bus.dto.BusStationRespDto;
import com.talkka.server.bus.exception.BusStationNotFoundException;
import com.talkka.server.bus.service.BusStationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bus/station")
public class BusStationController {
	private final BusStationService stationService;

	@GetMapping("")
	public ResponseEntity<List<BusStationRespDto>> getStations(
		@RequestParam(value = "search", required = false) String stationName) {
		List<BusStationRespDto> stationList;

		if (stationName != null) {
			stationList = stationService.getStationsByStationName(stationName);
		} else {
			stationList = stationService.getStations();
		}

		return ResponseEntity.ok(stationList);
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getStationById(@PathVariable("id") Long stationId) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(stationService.getStationById(stationId));
		} catch (BusStationNotFoundException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
