package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.BusReviewEntity;

import lombok.Builder;

@Builder
public record BusReviewRespDto(
	Long userId,
	String userName,
	Long routeId,
	String routeName,
	Long busRouteStationId,
	String stationName,
	String content,
	TimeSlot timeSlot,
	Integer rating
) {
	public static BusReviewRespDto of(BusReviewEntity busEntity) {
		return new BusReviewRespDto(
			busEntity.getWriter().getId(),
			busEntity.getWriter().getName(),
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
