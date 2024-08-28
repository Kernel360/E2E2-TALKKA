package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.oauth.enums.AuthRole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "개발자 로그인 API", description = "개발자 로그인 API")
public interface DevLoginApi {
	@Operation(
		summary = "수동 인증",
		description = "수동 인증을 진행합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 로그인 되었습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = String.class))),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)))
	})
	ResponseEntity<?> manualAuth(
		@Parameter(name = "authRole", schema = @Schema(implementation = AuthRole.class))
		String authRole,
		HttpServletRequest request);
}
