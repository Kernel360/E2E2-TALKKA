package com.talkka.server.review.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.enums.InvalidTimeSlotEnumException;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.exception.InvalidRatingException;
import com.talkka.server.review.exception.InvalidReviewContentLengthException;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.user.dao.UserEntity;

import lombok.Builder;

@Builder
public record BusReviewDto(
	Long id,
	Long userId,
	Long routeId,
	Long busRouteStationId,
	ReviewContent content,
	TimeSlot timeSlot,
	Rating rating
) {
	public static BusReviewDto of(Long reviewId, Long userId, BusReviewReqDto reqDto) throws
		InvalidRatingException, InvalidReviewContentLengthException,
		InvalidTimeSlotEnumException {
		ReviewContent contentValue = new ReviewContent(reqDto.content());
		TimeSlot timeSlotValue = TimeSlot.valueOfEnumString(reqDto.timeSlot());
		Rating ratingValue = new Rating(Integer.parseInt(reqDto.rating()));

		return new BusReviewDto(
			reviewId,
			userId,
			reqDto.routeId(),
			reqDto.busRouteStationId(),
			contentValue,
			timeSlotValue,
			ratingValue
		);
	}

	public static BusReviewDto of(Long userId, BusReviewReqDto reqDto) {
		return of(null, userId, reqDto);
	}

	public static BusReviewDto of(BusReviewEntity entity) {
		return new BusReviewDto(
			entity.getId(),
			entity.getWriter().getId(),
			entity.getRoute().getId(),
			entity.getStation().getId(),
			entity.getContent(),
			entity.getTimeSlot(),
			entity.getRating()
		);
	}

	public BusReviewEntity toEntity(UserEntity user, BusRouteStationEntity station, BusRouteEntity route) {
		return new BusReviewEntity(
			id,
			user,
			station,
			route,
			content,
			timeSlot,
			rating,
			null,
			null
		);
	}
}
