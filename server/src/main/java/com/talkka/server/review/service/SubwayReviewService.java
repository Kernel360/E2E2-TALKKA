package com.talkka.server.review.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.common.validator.ContentAccessValidator;
import com.talkka.server.review.dao.SubwayReviewEntity;
import com.talkka.server.review.dao.SubwayReviewRepository;
import com.talkka.server.review.dto.SubwayReviewDto;
import com.talkka.server.review.dto.SubwayReviewRespDto;
import com.talkka.server.review.exception.ContentAccessException;
import com.talkka.server.review.exception.SubwayReviewNotFoundException;
import com.talkka.server.review.exception.UserNotFoundException;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.dao.SubwayStationRepository;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.subway.exception.StationNotFoundException;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.dao.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubwayReviewService {

	private final SubwayReviewRepository subwayReviewRepository;
	private final UserRepository userRepository;
	private final SubwayStationRepository stationRepository;
	private final ContentAccessValidator contentAccessValidator;

	public List<SubwayReviewRespDto> getSubwayReviewList(
		Long subwayId
	) {
		List<SubwayReviewEntity> reviewEntityList = subwayReviewRepository.findAllByStationIdOrderByCreatedAtDesc(
			subwayId);
		return reviewEntityList.stream()
			.map(SubwayReviewRespDto::of)
			.toList();
	}

	public List<SubwayReviewRespDto> getSubwayReviewList(
		Long subwayId, String updown
	) throws InvalidTypeException {
		List<SubwayReviewEntity> reviewEntityList = subwayReviewRepository.findAllByStationIdAndUpdownOrderByCreatedAtDesc(
			subwayId, Updown.valueOfEnumString(updown));
		return reviewEntityList.stream()
			.map(SubwayReviewRespDto::of)
			.toList();
	}

	public List<SubwayReviewRespDto> getSubwayReviewList(
		Long subwayId, String updown, String timeSlot
	) throws InvalidTypeException {
		List<SubwayReviewEntity> reviewEntityList = subwayReviewRepository.findAllByStationIdAndUpdownAndTimeSlotOrderByCreatedAtDesc(
			subwayId, Updown.valueOfEnumString(updown), TimeSlot.valueOfEnumString(timeSlot));
		return reviewEntityList.stream()
			.map(SubwayReviewRespDto::of)
			.toList();
	}

	@Transactional
	public SubwayReviewRespDto createSubwayReview(SubwayReviewDto subwayReviewDto)
		throws UserNotFoundException, StationNotFoundException {
		Long userId = subwayReviewDto.userId();
		Long stationId = subwayReviewDto.stationId();

		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		SubwayStationEntity station = stationRepository.findById(stationId)
			.orElseThrow(() -> new StationNotFoundException(stationId));

		return SubwayReviewRespDto.of(subwayReviewRepository.save(subwayReviewDto.toEntity(user, station)));
	}

	@Transactional
	public SubwayReviewRespDto updateSubwayReview(SubwayReviewDto subwayReviewDto)
		throws UserNotFoundException, StationNotFoundException, ContentAccessException {
		Long userId = subwayReviewDto.userId();
		Long reviewId = subwayReviewDto.id();

		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		SubwayReviewEntity review = subwayReviewRepository.findById(reviewId)
			.orElseThrow(SubwayReviewNotFoundException::new);

		contentAccessValidator.validateOwnerContentAccess(userId, user.getAuthRole(), review.getWriter().getId());
		review.updateReview(subwayReviewDto.content(), subwayReviewDto.timeSlot(), subwayReviewDto.rating());
		var saved = subwayReviewRepository.save(review);
		return SubwayReviewRespDto.of(saved);
	}

	@Transactional
	public Long deleteSubwayReview(Long userId, Long reviewId)
		throws UserNotFoundException, StationNotFoundException, ContentAccessException {
		UserEntity user = userRepository.findById(userId)
			.orElseThrow(UserNotFoundException::new);
		SubwayReviewEntity review = subwayReviewRepository.findById(reviewId)
			.orElseThrow(SubwayReviewNotFoundException::new);

		contentAccessValidator.validateOwnerContentAccess(userId, user.getAuthRole(), review.getWriter().getId());

		subwayReviewRepository.deleteById(reviewId);
		return reviewId;
	}
}
