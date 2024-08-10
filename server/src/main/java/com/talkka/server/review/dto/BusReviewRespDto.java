package com.talkka.server.review.dto;

import com.talkka.server.review.dao.BusReviewEntity;

import lombok.Builder;

@Builder
public record BusReviewRespDto(
	Long userId,
	Long routeId,
	Long busRouteStationId,
	String content,
	String timeSlot,
	Integer rating
) {
	public static BusReviewRespDto of(BusReviewEntity busEntity) {
		return new BusReviewRespDto(
			busEntity.getWriter().getId(),
			busEntity.getRoute().getId(),
			busEntity.getStation().getId(),
			busEntity.getContent(),
			busEntity.getBusTimeSlot().getCode(),
			busEntity.getRating());
	}
}
