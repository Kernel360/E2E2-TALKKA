package com.talkka.server.bus.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.talkka.server.bus.dto.BusStationRespDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "버스 정류장 API", description = "버스 정류장 API")
public interface BusStationApi {
	@Operation(
		summary = "버스 정류장 조회",
		description = "버스 정류장을 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 정류장을 조회했습니다.")
	})
	ResponseEntity<List<BusStationRespDto>> getStations(
		@Parameter(description = "버스 정류장 이름")
		String stationName);

	@Operation(
		summary = "버스 정류장 상세 조회",
		description = "버스 정류장 상세 정보를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 정류장 상세 정보를 조회했습니다."),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 버스 정류장이 존재하지 않습니다.")
	})
	ResponseEntity<?> getStationById(
		@Parameter(description = "버스 정류장 ID", required = true)
		Long stationId);
}
