package com.talkka.server.oauth.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserCreateReqDto;
import com.talkka.server.user.dto.UserRespDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;

@Tag(name = "인증 API", description = "인증 API")
public interface AuthApi {
	@Operation(
		summary = "OAuth 인증 후 회원가입",
		description = "OAuth 인증 후 회원가입을 진행합니다.",
		security = @SecurityRequirement(name = "unregistered")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 회원가입을 진행했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> register(
		OAuth2UserInfo userInfo,
		UserCreateReqDto userCreateReqDto,
		HttpServletRequest request);
}
