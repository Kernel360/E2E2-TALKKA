package com.talkka.server.subway.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.talkka.server.subway.dto.SubwayStationReqDto;
import com.talkka.server.subway.dto.SubwayStationRespDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "지하철 역 API", description = "지하철 역 API")
public interface SubwayStationApi {
	@Operation(
		summary = "지하철 역 조회",
		description = "지하철 역을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 역을 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = SubwayStationRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 지하철 역이 존재하지 않습니다.")
	})
	ResponseEntity<?> getStation(
		@Parameter(description = "역 ID", required = true)
		Long stationId);

	@Operation(
		summary = "지하철 역 목록 조회",
		description = "지하철 역 목록을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 역 목록을 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				array = @ArraySchema(
					schema = @Schema(
						implementation = SubwayStationRespDto.class)
				)
			))
	})
	ResponseEntity<List<SubwayStationRespDto>> getStationList(
		@Parameter(description = "지하철 역 이름")
		String name
	);

	@Operation(
		summary = "지하철 역 생성",
		description = "지하철 역을 생성합니다.",
		security = {
			@SecurityRequirement(name = "admin"),
		})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 지하철 역을 생성했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(
					implementation = SubwayStationRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.")
	})
	ResponseEntity<?> createStation(
		@RequestBody(description = "지하철 역 생성 정보", required = true)
		SubwayStationReqDto subwayStationReqDto
	);
}
