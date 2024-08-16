package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;

import lombok.Builder;

@Builder
public record BusReviewRespDto(
	Long id,
	Long userId,
	String userName,
	Long routeId,
	String routeName,
	Long busRouteStationId,
	String stationName,
	ReviewContent content,
	TimeSlot timeSlot,
	Rating rating
) {
	public static BusReviewRespDto of(BusReviewEntity busEntity) {
		return new BusReviewRespDto(
			busEntity.getId(),
			busEntity.getWriter().getId(),
			busEntity.getWriter().getNickname(),
			busEntity.getRoute().getId(),
			busEntity.getRoute().getRouteName(),
			busEntity.getStation().getId(),
			busEntity.getStation().getStationName(),
			busEntity.getContent(),
			busEntity.getTimeSlot(),
			busEntity.getRating()
		);
	}
}
