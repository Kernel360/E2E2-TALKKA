package com.talkka.server.subway.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.subway.dto.SubwayTimetableRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "지하철 시간표 API", description = "지하철 시간표 API")
public interface SubwayTimetableApi {
	@Operation(
		summary = "지하철 시간표 조회",
		description = "지하철 시간표를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 시간표를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SubwayTimetableRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 지하철 시간표가 존재하지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getTimetable(
		@Parameter(description = "역 ID", required = true)
		Long stationId,
		@Parameter(description = "요일 타입", schema = @Schema(implementation = DayType.class))
		String dayTypeCode,
		@Parameter(description = "상행/하행", schema = @Schema(implementation = Updown.class))
		String updownCode,
		@Parameter(description = "시간 (HH:MM)", example = "08:00")
		String time
	);

	@Operation(
		summary = "지하철 시간표 목록 조회",
		description = "지하철 시간표 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 시간표 목록을 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SubwayTimetableRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getTimetableList(
		@Parameter(description = "역 ID", required = true)
		Long stationId,
		@Parameter(description = "요일 타입", schema = @Schema(implementation = DayType.class))
		String dayTypeCode,
		@Parameter(description = "상행/하행", schema = @Schema(implementation = Updown.class))
		String updownCode,
		@Parameter(description = "시작 시간 (HH:MM)", required = true, example = "08:00")
		String startTime,
		@Parameter(description = "종료 시간 (HH:MM)", required = true, example = "08:00")
		String endTime
	);
}
