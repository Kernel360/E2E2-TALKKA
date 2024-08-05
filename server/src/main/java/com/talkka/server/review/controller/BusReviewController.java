package com.talkka.server.review.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.talkka.server.review.dto.BusReviewReqDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.review.service.BusReviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bus-review")
@RequiredArgsConstructor
public class BusReviewController {

	private final BusReviewService busReviewService;

	@GetMapping("")
	public List<BusReviewRespDto> getBusReviewList(
		@SessionAttribute(name = "userId") Long userId,
		@RequestParam Long routeId,
		@RequestParam Long busRouteStationId,
		@RequestParam Integer timeSlot
	) {
		return busReviewService.getBusReviewList(userId, routeId, busRouteStationId, timeSlot);
	}

	@PostMapping("")
	public void saveBusReview(
		@SessionAttribute(name = "userId") Long userId,
		@RequestBody BusReviewReqDto busReviewReqDto
	) {
		busReviewService.createBusReview(userId, busReviewReqDto);
	}

	@PutMapping("{bus_review_id}")
	public BusReviewRespDto updateBusReview(
		@SessionAttribute(name = "userId") Long userId,
		@PathVariable(name = "bus_review_id") Long busReviewId,
		@RequestBody BusReviewReqDto busReviewReqDto
	) {
		return busReviewService.updateBusReview(busReviewId, busReviewReqDto);
	}

	@DeleteMapping("{bus_review_id}")
	public void deleteBusReview(
		@SessionAttribute(name = "userId") Long userId,
		@PathVariable(name = "bus_review_id") Long busReviewId
	) {
		busReviewService.deleteBusReview(busReviewId);
	}
}
