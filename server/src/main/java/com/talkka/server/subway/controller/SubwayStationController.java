package com.talkka.server.subway.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.subway.dto.SubwayStationDto;
import com.talkka.server.subway.dto.SubwayStationReqDto;
import com.talkka.server.subway.dto.SubwayStationRespDto;
import com.talkka.server.subway.exception.StationAlreadyExistsException;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.service.SubwayStationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subway/station")
public class SubwayStationController {

	private final SubwayStationService stationService;

	@GetMapping("/{stationId}")
	public ResponseEntity<?> getStation(@PathVariable Long stationId) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(stationService.getStation(stationId));
		} catch (StationNotFoundException exception) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
		return response;
	}

	@GetMapping("")
	public ResponseEntity<List<SubwayStationRespDto>> getStationList(
		@RequestParam(value = "keyword", required = false) String name
	) {
		List<SubwayStationRespDto> stationList;

		if (name != null) {
			stationList = stationService.getStationListByStationName(name);
		} else {
			stationList = stationService.getStationList();
		}

		return ResponseEntity.ok(stationList);
	}

	@PostMapping("")
	@Secured("ADMIN")
	public ResponseEntity<?> createStation(
		@RequestBody @Valid SubwayStationReqDto subwayStationReqDto
	) {
		ResponseEntity<?> response;
		try {
			SubwayStationDto subwayStationDto = SubwayStationDto.of(subwayStationReqDto);

			response = ResponseEntity.ok(stationService.createStation(subwayStationDto));
		} catch (StationAlreadyExistsException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
