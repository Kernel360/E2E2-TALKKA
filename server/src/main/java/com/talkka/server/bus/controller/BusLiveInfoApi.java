package com.talkka.server.bus.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.bus.dto.BusLiveInfoRespDto;
import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.enums.TimeSlot;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "버스 실시간 정보", description = "버스 실시간 정보 조회 API")
public interface BusLiveInfoApi {
	@Operation(summary = "버스 실시간 정보 조회", description = "버스 실시간 정보 조회 API")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "성공",
			content = @Content(schema = @Schema(implementation = BusLiveInfoRespDto.class))),
		@ApiResponse(responseCode = "400", description = "잘못된 요청",
			content = @Content(schema = @Schema(implementation = ErrorRespDto.class)))
	})
	ResponseEntity<?> getBusLiveInfo(
		@Parameter(description = "노선 정류장 ID", required = true)
		Long routeStationId,
		@Parameter(description = "시간대", required = true, schema = @Schema(implementation = TimeSlot.class))
		String timeSlot,
		@Parameter(description = "주차", required = true)
		Long week);
}
