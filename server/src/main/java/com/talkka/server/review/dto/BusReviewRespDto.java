package com.talkka.server.review.dto;

import com.talkka.server.review.dao.BusReviewEntity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class BusReviewRespDto {

	private Long userId;
	private Long routeId;
	private Long busRouteStationId;
	private String content;
	private String timeSlot;
	private Integer rating;

	public static BusReviewRespDto of(BusReviewEntity busEntity) {
		return new BusReviewRespDto(
			busEntity.getWriter().getUserId(),
			busEntity.getRoute().getId(),
			busEntity.getStation().getId(),
			busEntity.getContent(),
			busEntity.getTimeSlot().getCode(),
			busEntity.getRating());
	}
}
