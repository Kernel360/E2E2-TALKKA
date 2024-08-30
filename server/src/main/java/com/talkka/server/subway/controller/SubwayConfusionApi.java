package com.talkka.server.subway.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.subway.dto.SubwayConfusionRespDto;
import com.talkka.server.subway.enums.DayType;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "지하철 혼잡도 API", description = "지하철 혼잡도 API")
public interface SubwayConfusionApi {

	@Operation(
		summary = "지하철 혼잡도 조회",
		description = "지하철 혼잡도를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 혼잡도를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = SubwayConfusionRespDto.class)
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
			description = "해당 ID의 지하철 혼잡도가 존재하지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
	})
	ResponseEntity<?> getConfusion(
		@Parameter(description = "역 ID", required = true)
		Long stationId,
		@Parameter(description = "요일 타입", schema = @Schema(implementation = DayType.class))
		String dayType,
		@Parameter(description = "상행/하행", schema = @Schema(implementation = Updown.class))
		String updown,
		@Parameter(description = "시작 시간대", schema = @Schema(implementation = TimeSlot.class))
		String timeSlot
	);

	@Operation(
		summary = "지하철 혼잡도 목록 조회",
		description = "지하철 혼잡도 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 혼잡도 목록을 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				array = @ArraySchema(schema = @Schema(implementation = SubwayConfusionRespDto.class))
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getConfusionList(
		@Parameter(description = "역 ID", required = true)
		Long stationId,
		@Parameter(description = "요일 타입", required = true, schema = @Schema(implementation = DayType.class))
		String dayType,
		@Parameter(description = "상행/하행", required = true, schema = @Schema(implementation = Updown.class))
		String updown,
		@Parameter(description = "시작 시간대", schema = @Schema(implementation = TimeSlot.class))
		String startTimeSlot,
		@Parameter(description = "종료 시간대", schema = @Schema(implementation = TimeSlot.class))
		String endTimeSlot
	);
}
