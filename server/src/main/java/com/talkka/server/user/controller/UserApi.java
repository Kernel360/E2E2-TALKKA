package com.talkka.server.user.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.user.dto.UserRespDto;
import com.talkka.server.user.dto.UserUpdateReqDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "유저 API", description = "유저 API")
public interface UserApi {

	@Operation(
		summary = "유저 ID를 바탕으로 조회 ",
		description = "유저 ID를 바탕으로 조회합니다.",
		security = @SecurityRequirement(name = "admin")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 유저 정보를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserRespDto.class)
			)),
		@ApiResponse(
			responseCode = "404",
			description = "해당 ID의 유저가 존재하지 않음.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getUser(
		@Parameter(description = "User ID", required = true) Long userId
	);

	@Operation(
		summary = "유저 ID를 바탕으로 수정",
		description = "유저 ID를 바탕으로 유저 정보를 수정합니다.",
		security = @SecurityRequirement(name = "admin")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 유저 정보를 수정했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청, 에러 내용은 message 필드 참조.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> updateUser(
		@Parameter(description = "User ID", required = true) Long userId,
		@RequestBody(description = "User Update Request", required = true) UserUpdateReqDto userUpdateReqDto
	);

	@Operation(
		summary = "유저 ID 대상을 삭제",
		description = "유저 ID를 바탕으로 유저 정보를 삭제합니다.",
		security = @SecurityRequirement(name = "admin")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 유저 정보를 삭제했습니다."),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청, 에러 내용은 message 필드 참조.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> deleteUser(
		@Parameter(description = "User ID", required = true) Long userId
	);

	@Operation(
		summary = "로그인한 본인 정보 조회",
		description = "로그인한 본인의 정보를 조회합니다.",
		security = @SecurityRequirement(name = "user")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 유저 정보를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserRespDto.class)
			)),
		@ApiResponse(
			responseCode = "401",
			description = "권한이 없습니다. (유저가 삭제되었을 가능성)",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getMe(
		OAuth2UserInfo userInfo
	);

	@Operation(
		summary = "로그인한 본인 정보 수정",
		description = "로그인한 본인의 정보를 수정합니다.",
		security = @SecurityRequirement(name = "user")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 유저 정보를 수정했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = UserRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청, 에러 내용은 message 필드 참조.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "401",
			description = "권한이 없습니다. (유저가 삭제되었을 가능성)",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> updateMe(
		OAuth2UserInfo userInfo,
		@RequestBody(description = "User Update Request", required = true) UserUpdateReqDto userUpdateReqDto
	);
}
