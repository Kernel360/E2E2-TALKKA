package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.talkka.server.bus.dto.BusRouteRespDto;
import com.talkka.server.common.dto.ErrorRespDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "버스 노선 API", description = "버스 노선 API")
public interface BusRouteApi {
	@Operation(
		summary = "버스 노선 조회",
		description = "버스 노선을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 노선을 조회했습니다.")
	})
	ResponseEntity<List<BusRouteRespDto>> getRoutes(
		@Parameter(description = "버스 노선 이름")
		String routeName
	);

	@Operation(
		summary = "버스 노선 상세 조회",
		description = "버스 노선 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 노선 상세 정보를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BusRouteRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 버스 노선이 존재하지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getRouteById(
		@Parameter(description = "버스 노선 ID", required = true)
		Long routeId);
}
