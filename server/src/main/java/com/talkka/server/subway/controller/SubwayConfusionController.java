package com.talkka.server.subway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.subway.exception.ConfusionNotFoundException;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.service.SubwayConfusionService;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
@RequestMapping("/api/subway/confusion")
public class SubwayConfusionController {

	private final SubwayConfusionService confusionService;

	@GetMapping("/{stationId}")
	public ResponseEntity<?> getConfusion(
		@PathVariable Long stationId,
		@RequestParam String dayType,
		@RequestParam String updown,
		@RequestParam String timeSlot
	) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(
				confusionService.getConfusion(stationId, dayType, updown, timeSlot));
		} catch (StationNotFoundException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		} catch (ConfusionNotFoundException exception) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
		return response;
	}

	@GetMapping("/{stationId}/list")
	public ResponseEntity<?> getConfusionList(
		@PathVariable Long stationId,
		@RequestParam String dayType,
		@RequestParam String updown,
		@RequestParam(required = false, defaultValue = "0") String startTimeSlot,
		@RequestParam(required = false, defaultValue = "47") String endTimeSlot
	) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(
				confusionService.getConfusionList(stationId, dayType, updown, startTimeSlot, endTimeSlot)
			);
		} catch (StationNotFoundException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
