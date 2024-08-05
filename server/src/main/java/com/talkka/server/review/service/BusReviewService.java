package com.talkka.server.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.common.exception.http.ForbiddenException;
import com.talkka.server.common.exception.http.NotFoundException;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.dao.BusReviewRepository;
import com.talkka.server.review.dto.BusReviewReqDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BusReviewService {

	private final BusReviewRepository busReviewRepository;
	private final UserRepository userRepository;
	private final BusRouteStationRepository busRouteStationRepository;
	private final BusRouteRepository busRouteRepository;

	public List<BusReviewRespDto> getBusReviewList(
		Long userId, Long routeId, Long busRouteStationId, Integer timeSlot
	) {
		List<BusReviewEntity> reviewList = busReviewRepository.findReviews(
			userId, routeId, busRouteStationId, timeSlot);

		return reviewList.stream()
			.map(BusReviewRespDto::of)
			.toList();
	}

	public BusReviewRespDto createBusReview(Long userId, BusReviewReqDto busReviewReqDto) {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

		BusRouteStationEntity station = busRouteStationRepository.findById(busReviewReqDto.getBusRouteStationId())
			.orElseThrow(() -> new NotFoundException("존재하지 않는 경유 정류장입니다."));

		BusRouteEntity route = busRouteRepository.findById(busReviewReqDto.getRouteId())
			.orElseThrow(() -> new NotFoundException("존재하지 않는 노선입니다."));

		BusReviewEntity entity = busReviewReqDto.toEntity(user, station, route);

		BusReviewEntity savedReview = busReviewRepository.save(entity);

		return BusReviewRespDto.of(savedReview);
	}

	public BusReviewRespDto updateBusReview(Long userId, Long busReviewId, BusReviewReqDto busReviewReqDto) {
		BusReviewEntity review = busReviewRepository.findById(busReviewId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다."));

		if (!isReviewOwner(userId, review)) {
			throw new ForbiddenException("작성자와 일치하지 않는 ID입니다.");
		}

		review.updateReview(busReviewReqDto.getContent(), busReviewReqDto.getRating(), busReviewReqDto.getTimeSlot());
		BusReviewEntity updatedReview = busReviewRepository.save(review);
		return BusReviewRespDto.of(updatedReview);
	}

	public Long deleteBusReview(Long userId, Long busReviewId) {
		BusReviewEntity review = busReviewRepository.findById(busReviewId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다."));

		if (!isReviewOwner(userId, review)) {
			throw new ForbiddenException("작성자와 일치하지 않는 ID입니다.");
		}
		busReviewRepository.deleteById(busReviewId);
		return busReviewId;
	}

	private boolean isReviewOwner(long userId, BusReviewEntity busReviewEntity) {
		return userId == busReviewEntity.getWriter().getUserId();
	}
}