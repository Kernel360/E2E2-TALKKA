package com.talkka.server.review.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.talkka.server.common.dto.ApiRespDto;
import com.talkka.server.common.enums.StatusCode;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.review.dto.BusReviewReqDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.review.service.BusReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bus-review")
@RequiredArgsConstructor
public class BusReviewController {

	private final BusReviewService busReviewService;

	@GetMapping("")
	public ResponseEntity<ApiRespDto<List<BusReviewRespDto>>> getBusReviewList(
		@RequestParam(required = true) Long routeId,
		@RequestParam(required = false) Long busRouteStationId,
		@RequestParam(required = false) String timeSlot
	) {
		List<BusReviewRespDto> reviewData;
		if (busRouteStationId != null && timeSlot != null) {
			reviewData = busReviewService.getBusReviewList(routeId, busRouteStationId, timeSlot);
		} else if (busRouteStationId != null) {
			reviewData = busReviewService.getBusReviewList(routeId, busRouteStationId);
		} else {
			reviewData = busReviewService.getBusReviewList(routeId);
		}

		return ResponseEntity.ok(
			ApiRespDto.<List<BusReviewRespDto>>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(reviewData)
				.build());
	}

	@PostMapping("")
	public ResponseEntity<ApiRespDto<BusReviewRespDto>> saveBusReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@RequestBody @Valid BusReviewReqDto busReviewReqDto
	) {
		BusReviewRespDto createdReview = busReviewService.createBusReview(oAuth2UserInfo.getUserId(), busReviewReqDto);

		return ResponseEntity.ok(
			ApiRespDto.<BusReviewRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(createdReview)
				.build());
	}

	@PutMapping("{bus_review_id}")
	public ResponseEntity<ApiRespDto<BusReviewRespDto>> updateBusReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable(name = "bus_review_id") Long busReviewId,
		@RequestBody @Valid BusReviewReqDto busReviewReqDto
	) {
		BusReviewRespDto updatedReview = busReviewService.updateBusReview(oAuth2UserInfo.getUserId(), busReviewId,
			busReviewReqDto);

		return ResponseEntity.ok(
			ApiRespDto.<BusReviewRespDto>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(updatedReview)
				.build());
	}

	@DeleteMapping("{bus_review_id}")
	public ResponseEntity<ApiRespDto<Void>> deleteBusReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable(name = "bus_review_id") Long busReviewId
	) {
		busReviewService.deleteBusReview(oAuth2UserInfo.getUserId(), busReviewId);

		return ResponseEntity.ok(
			ApiRespDto.<Void>builder()
				.statusCode(StatusCode.OK.getCode())
				.message(StatusCode.OK.getMessage())
				.data(null)
				.build());
	}
}
