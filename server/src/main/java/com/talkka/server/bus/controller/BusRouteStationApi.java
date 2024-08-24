package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.talkka.server.bus.dto.BusRouteStationRespDto;
import com.talkka.server.common.dto.ErrorRespDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "버스 노선 정류장 API", description = "버스 노선 정류장 API")
public interface BusRouteStationApi {
	@Operation(
		summary = "버스 노선 정류장 조회",
		description = "버스 노선 정류장을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 노선 정류장을 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BusRouteStationRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 버스 노선 정류장이 존재하지 않습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getRouteStationById(
		@Parameter(description = "버스 노선 정류장 ID", required = true)
		Long routeStationId);

	@Operation(
		summary = "버스 노선 정류장 목록 조회",
		description = "버스 노선 정류장 목록을 조회합니다. routeId, stationId 는 각각 filter 의 역할을 합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 노선 정류장 목록을 조회했습니다.")
	})
	ResponseEntity<List<BusRouteStationRespDto>> getRouteStations(
		@Parameter(description = "버스 노선 ID")
		Long routeId,
		@Parameter(description = "버스 정류장 ID")
		Long stationId);
}
