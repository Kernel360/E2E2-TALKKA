package com.talkka.server.review.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
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
		Long userId, Long routeId, Long stationId, Integer timeSlot
	) {
		Optional<List<BusReviewEntity>> optionalList = busReviewRepository.findAllByUserIdAndRouteIdAndStationIdAndTimeSlot(
			userId, routeId, stationId, timeSlot);

		List<BusReviewEntity> list = optionalList.orElseGet(ArrayList::new);

		return list.stream()
			.map(BusReviewRespDto::of)
			.collect(Collectors.toList());
	}

	public BusReviewRespDto createBusReview(Long userId, BusReviewReqDto busReviewReqDto) {
		final UserEntity user = userRepository.findById(userId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 유저입니다."));

		final BusRouteStationEntity station = busRouteStationRepository.findById(busReviewReqDto.getBusRouteStationId())
			.orElseThrow(() -> new NotFoundException("존재하지 않는 경유 정류장입니다."));

		final BusRouteEntity route = busRouteRepository.findById(busReviewReqDto.getRouteId())
			.orElseThrow(() -> new NotFoundException("존재하지 않는 노선입니다."));

		final BusReviewEntity entity = busReviewReqDto.toEntity(user, station, route);

		BusReviewEntity savedReview = busReviewRepository.save(entity);

		return BusReviewRespDto.of(savedReview);
	}

	public BusReviewRespDto updateBusReview(Long busReviewId, BusReviewReqDto busReviewReqDto) {
		final BusReviewEntity review = busReviewRepository.findById(busReviewId)
			.orElseThrow(() -> new NotFoundException("존재하지 않는 리뷰입니다."));

		review.setContent(busReviewReqDto.getContent()
			.orElse(null));
		review.setTimeSlot(busReviewReqDto.getTimeSlot());
		review.setRating(busReviewReqDto.getRating());

		BusReviewEntity updatedReview = busReviewRepository.save(review);

		return BusReviewRespDto.of(updatedReview);
	}

	public void deleteBusReview(Long busReviewId) {
		busReviewRepository.deleteById(busReviewId);
	}
}
