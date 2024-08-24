package com.talkka.server.review.controller;

import org.springframework.http.ResponseEntity;

import com.talkka.server.common.dto.ErrorRespDto;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.review.dto.BusReviewReqDto;
import com.talkka.server.review.dto.BusReviewRespDto;

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

@Tag(name = "버스 리뷰 API", description = "버스 리뷰 API")
public interface BusReviewApi {

	@Operation(
		summary = "버스 리뷰 조회",
		description = "버스 리뷰를 조회합니다.")
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 리뷰를 조회했습니다.",
			content = @Content(
				mediaType = "application/json",
				array = @ArraySchema(
					schema = @Schema(implementation = BusReviewRespDto.class),
					maxItems = 10
				)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> getBusReviewList(
		@Parameter(description = "버스 노선 ID", required = true)
		Long routeId,
		@Parameter(description = "버스 노선 경유 정류장 ID")
		Long busRouteStationId,
		@Parameter(description = "시간대", schema = @Schema(implementation = TimeSlot.class))
		String timeSlot);

	@Operation(
		summary = "버스 리뷰 생성",
		description = "버스 리뷰를 생성합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 리뷰를 생성했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BusReviewRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> saveBusReview(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@Parameter(description = "버스 리뷰 생성 요청 DTO", required = true)
		BusReviewReqDto busReviewReqDto);

	// 버스 리뷰 수정 (USER, ADMIN 인증 필요)
	@Operation(
		summary = "버스 리뷰 수정",
		description = "버스 리뷰를 수정합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 리뷰를 수정했습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = BusReviewRespDto.class)
			)),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "403",
			description = "권한이 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> updateBusReview(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@Parameter(description = "버스 리뷰 ID", required = true)
		Long busReviewId,
		@RequestBody(description = "버스 리뷰 수정 요청 DTO", required = true)
		BusReviewReqDto busReviewReqDto);

	// 버스 리뷰 삭제 (USER, ADMIN 인증 필요)
	@Operation(
		summary = "버스 리뷰 삭제",
		description = "버스 리뷰를 삭제합니다.",
		security = {
			@SecurityRequirement(name = "user"),
			@SecurityRequirement(name = "admin")
		})
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "정상적으로 버스 리뷰를 삭제했습니다."),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청입니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			)),
		@ApiResponse(
			responseCode = "403",
			description = "권한이 없습니다.",
			content = @Content(
				mediaType = "application/json",
				schema = @Schema(implementation = ErrorRespDto.class)
			))
	})
	ResponseEntity<?> deleteBusReview(
		@Parameter(hidden = true)
		OAuth2UserInfo oAuth2UserInfo,
		@Parameter(description = "버스 리뷰 ID", required = true)
		Long busReviewId);
}
