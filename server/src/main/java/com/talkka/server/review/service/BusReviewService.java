package com.talkka.server.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteRepository;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.bus.dao.BusRouteStationRepository;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;
import com.talkka.server.common.validator.ContentAccessValidator;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.dao.BusReviewRepository;
import com.talkka.server.review.dto.BusReviewDto;
import com.talkka.server.review.dto.BusReviewRespDto;
import com.talkka.server.review.exception.BusReviewNotFoundException;
import com.talkka.server.review.exception.BusRouteNotFoundException;
import com.talkka.server.review.exception.BusStationNotFoundException;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.review.exception.UserNotFoundException;
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
	private final ContentAccessValidator contentAccessValidator;

	public List<BusReviewRespDto> getUsersBusReviewList(
		Long userId, Long routeId, Long busRouteStationId, String timeSlot
	) throws InvalidTimeSlotEnumException {
		List<BusReviewEntity> reviewList = busReviewRepository
			.findAllByWriterIdAndRouteIdAndStationIdAndTimeSlotOrderByUpdatedAtDesc(
				userId, routeId, busRouteStationId, TimeSlot.valueOfEnumString(timeSlot));

		return reviewList.stream()
			.map(BusReviewRespDto::of)
			.toList();
	}

	public List<BusReviewRespDto> getBusReviewList(Long routeId) {
		List<BusReviewEntity> reviewEntityList = busReviewRepository.findAllByRouteIdOrderByCreatedAtDesc(routeId);
		return reviewEntityList.stream()
			.map(BusReviewRespDto::of)
			.toList();
	}

	public List<BusReviewRespDto> getBusReviewList(Long routeId, Long busRouteStationId) {
		List<BusReviewEntity> reviewEntityList = busReviewRepository.findAllByRouteIdAndStationIdOrderByCreatedAtDesc(
			routeId, busRouteStationId);
		return reviewEntityList.stream()
			.map(BusReviewRespDto::of)
			.toList();
	}

	public List<BusReviewRespDto> getBusReviewList(Long routeId, Long busRouteStationId, String timeSlot) throws
		InvalidTimeSlotEnumException {
		List<BusReviewEntity> reviewEntityList = busReviewRepository
			.findAllByRouteIdAndStationIdAndTimeSlotOrderByCreatedAtDesc(
				routeId, busRouteStationId, TimeSlot.valueOfEnumString(timeSlot));
		return reviewEntityList.stream()
			.map(BusReviewRespDto::of)
			.toList();
	}

	@Transactional
	public BusReviewRespDto createBusReview(BusReviewDto busReviewDto)
		throws UserNotFoundException, BusStationNotFoundException, BusRouteNotFoundException {
		Long userId = busReviewDto.userId();
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		BusRouteStationEntity station = busRouteStationRepository.findById(busReviewDto.busRouteStationId())
			.orElseThrow(BusStationNotFoundException::new);
		BusRouteEntity route = busRouteRepository.findById(busReviewDto.routeId())
			.orElseThrow(BusRouteNotFoundException::new);

		BusReviewEntity entity = busReviewDto.toEntity(user, station, route);
		BusReviewEntity savedReview = busReviewRepository.save(entity);
		return BusReviewRespDto.of(savedReview);
	}

	@Transactional
	public BusReviewRespDto updateBusReview(BusReviewDto busReviewDto)
		throws ContentAccessException, BusReviewNotFoundException, UserNotFoundException {
		Long userId = busReviewDto.userId();
		Long reviewId = busReviewDto.id();

		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		BusReviewEntity review = busReviewRepository.findById(reviewId)
			.orElseThrow(BusReviewNotFoundException::new);

		contentAccessValidator.validateOwnerContentAccess(userId, user.getGrade(), review.getWriter().getId());
		review.updateReview(busReviewDto.content(), busReviewDto.timeSlot(), busReviewDto.rating());
		var saved = busReviewRepository.save(review);
		return BusReviewRespDto.of(saved);
	}

	@Transactional
	public Long deleteBusReview(Long userId, Long busReviewId) throws
		ContentAccessException,
		BusReviewNotFoundException, UserNotFoundException {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		BusReviewEntity review = busReviewRepository.findById(busReviewId)
			.orElseThrow(BusReviewNotFoundException::new);

		contentAccessValidator.validateOwnerContentAccess(userId, user.getGrade(), review.getWriter().getId());

		busReviewRepository.deleteById(busReviewId);
		return busReviewId;
	}

}
