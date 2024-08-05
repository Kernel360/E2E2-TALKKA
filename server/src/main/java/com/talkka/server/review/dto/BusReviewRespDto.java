package com.talkka.server.review.dto;

import com.talkka.server.review.dao.BusReviewEntity;

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
@NoArgsConstructor
@AllArgsConstructor
public class BusReviewRespDto {

	private Long userId;
	private Long routeId;
	private Long busRouteStationId;
	private String content;
	private Integer timeSlot;
	private Integer rating;

	public static BusReviewRespDto of(BusReviewEntity busEntity) {
		return BusReviewRespDto.builder()
			.userId(busEntity.getWriter().getUserId())
			.routeId(busEntity.getRoute().getRouteId())
			.busRouteStationId(busEntity.getStation().getBusRouteStationId())
			.content(busEntity.getContent())
			.timeSlot(busEntity.getTimeSlot())
			.rating(busEntity.getRating())
			.build();
	}
}
