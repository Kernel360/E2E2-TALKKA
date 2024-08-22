package com.talkka.server.review.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.oauth.domain.OAuth2UserInfo;
import com.talkka.server.review.dto.SubwayReviewDto;
import com.talkka.server.review.dto.SubwayReviewReqDto;
import com.talkka.server.review.dto.SubwayReviewRespDto;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.review.exception.SubwayReviewNotFoundException;
import com.talkka.server.review.service.SubwayReviewService;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.user.exception.UserNotFoundException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/subway-review")
public class SubwayReviewController {

	private final SubwayReviewService subwayReviewService;

	@GetMapping("")
	public ResponseEntity<?> getSubwayReviewList(
		@RequestParam Long stationId,
		@RequestParam(required = false) String updown,
		@RequestParam(required = false) String timeSlot
	) {
		List<SubwayReviewRespDto> reviewData;
		try {
			if (updown != null && timeSlot != null) {
				reviewData = subwayReviewService.getSubwayReviewList(stationId);
			} else if (updown != null) {
				reviewData = subwayReviewService.getSubwayReviewList(stationId, updown);
			} else {
				reviewData = subwayReviewService.getSubwayReviewList(stationId, updown, timeSlot);
			}
		} catch (InvalidTypeException exception) {
			return ResponseEntity.badRequest().body(exception.getMessage());
		}
		return ResponseEntity.ok(reviewData);
	}

	@PostMapping("")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> createSubwayReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@RequestBody @Valid SubwayReviewReqDto subwayReviewReqDto
	) {
		ResponseEntity<?> response;
		try {
			SubwayReviewDto reviewDto = SubwayReviewDto.of(oAuth2UserInfo.getUserId(), subwayReviewReqDto);
			SubwayReviewRespDto createdReview = subwayReviewService.createSubwayReview(reviewDto);
			response = ResponseEntity.ok(createdReview);
		} catch (UserNotFoundException | StationNotFoundException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}

		return response;
	}

	@PutMapping("/{subway_review_id}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> updateSubwayReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable(name = "subway_review_id") Long subwayReviewId,
		@RequestBody @Valid SubwayReviewReqDto subwayReviewReqDto
	) {
		ResponseEntity<?> response;

		try {
			SubwayReviewDto reviewDto = SubwayReviewDto.of(
				subwayReviewId, oAuth2UserInfo.getUserId(), subwayReviewReqDto);
			SubwayReviewRespDto updatedReview = subwayReviewService.updateSubwayReview(reviewDto);
			response = ResponseEntity.ok(updatedReview);
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(exception.getMessage());
		} catch (UserNotFoundException | SubwayReviewNotFoundException | InvalidTypeException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}

	@DeleteMapping("/{subway_review_id}")
	@Secured({"USER", "ADMIN"})
	public ResponseEntity<?> deleteSubwayReview(
		@AuthenticationPrincipal OAuth2UserInfo oAuth2UserInfo,
		@PathVariable(name = "subway_review_id") Long subwayReviewId
	) {
		ResponseEntity<?> response;

		try {
			subwayReviewService.deleteSubwayReview(oAuth2UserInfo.getUserId(), subwayReviewId);
			response = ResponseEntity.ok().build();
		} catch (ContentAccessException exception) {
			response = ResponseEntity.status(HttpStatus.FORBIDDEN)
				.body(exception.getMessage());
		} catch (UserNotFoundException | SubwayReviewNotFoundException exception) {
			response = ResponseEntity.badRequest().body(exception.getMessage());
		}
		return response;
	}
}
