package com.talkka.server.review.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.user.dao.UserEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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

	@NotNull
	private Long routeId;

	@NotNull
	private Long busRouteStationId;

	@Size(min = 5, max = 200)
	private String content;

	@NotNull
	@Min(0)
	@Max(47)
	private Integer timeSlot;

	@NotNull
	@Min(1)
	@Max(10)
	private Integer rating;

	public BusReviewEntity toEntity(UserEntity user, BusRouteStationEntity station, BusRouteEntity route) {
		return new BusReviewEntity(null, user, station, route, content, timeSlot, rating, null, null);
	}
}
