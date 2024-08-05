package com.talkka.server.review.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.user.dao.UserEntity;

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
public class BusReviewReqDto {

	private Long routeId;
	private Long busRouteStationId;
	private String content;
	private Integer timeSlot;
	private Integer rating;

	public BusReviewEntity toEntity(UserEntity user, BusRouteStationEntity station, BusRouteEntity route) {
		return new BusReviewEntity(null, user, station, route, content, timeSlot, rating, null, null);
	}
}
