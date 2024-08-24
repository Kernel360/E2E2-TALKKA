package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.oauth.enums.AuthRole;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "개발자 로그인 API", description = "개발자 로그인 API")
public interface DevLoginApi {
	@Operation(
		summary = "수동 인증",
		description = "수동 인증을 진행합니다.")
	ResponseEntity<String> manualAuth(
		@Parameter(name = "authRole", schema = @Schema(implementation = AuthRole.class))
		String authRole,
		HttpServletRequest request);
}
