package com.talkka.server.review.dto;

import com.talkka.server.bus.dao.BusRouteEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;
import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.user.dao.UserEntity;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record BusReviewReqDto(
	@NotNull Long routeId,
	@NotNull Long busRouteStationId,
	@Size(min = 5, max = 200, message = "리뷰는 5자 이상, 200자 이하로 작성하여야 합니다.") String content,
	@NotNull @Size(min = 1, max = 2, message = "형식에 맞지 않은 timeSlot 입니다.") String timeSlot,
	@NotNull @Min(value = 1, message = "형식에 맞지 않는 rating 입니다.")
	@Max(value = 10, message = "형식에 맞지 않는 rating 입니다.") Integer rating
) {
	public BusReviewEntity toEntity(
		UserEntity user, BusRouteStationEntity station, BusRouteEntity route, TimeSlot timeSlot) {
		return new BusReviewEntity(null, user, station, route, content, timeSlot, rating, null, null);
	}
}
