package com.talkka.server.subway.controller;

import java.time.format.DateTimeParseException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.subway.exception.TimetableNotFoundException;
import com.talkka.server.subway.service.SubwayTimetableService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subway/timetable")
public class SubwayTimetableController {

	private final SubwayTimetableService timetableService;

	@GetMapping("/{stationId}")
	public ResponseEntity<?> getTimetable(
		@PathVariable Long stationId,
		@RequestParam String dayTypeCode,
		@RequestParam String updownCode,
		@RequestParam String time
	) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(
				timetableService.getTimetable(stationId, dayTypeCode, updownCode, time));
		} catch (DateTimeParseException exception) {
			response = ResponseEntity.badRequest().body("해당 시간 형식은 올바르지 않습니다.");
		} catch (StationNotFoundException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		} catch (TimetableNotFoundException exception) {
			response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
		}
		return response;
	}

	// 첫차 = 5:00:00, 막차 = 0:30:30 기준으로 널널하게 잡음
	@GetMapping("/{stationId}/list")
	public ResponseEntity<?> getTimetableList(
		@PathVariable Long stationId,
		@RequestParam String dayTypeCode,
		@RequestParam String updownCode,
		@RequestParam(required = false, defaultValue = "4:00:00") String startTime,
		@RequestParam(required = false, defaultValue = "1:00:00") String endTime
	) {
		ResponseEntity<?> response;
		try {
			response = ResponseEntity.ok(
				timetableService.getTimetableList(stationId, dayTypeCode, updownCode, startTime, endTime));
		} catch (DateTimeParseException exception) {
			response = ResponseEntity.badRequest().body("해당 시간 형식은 올바르지 않습니다.");
		} catch (StationNotFoundException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
