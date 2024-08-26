package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.BusReviewEntity;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.user.vo.Nickname;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BusReviewRespDto(
	@NotNull
	Long id,
	@NotNull
	Long userId,
	@NotNull
	Nickname userName,
	@NotNull
	Long routeId,
	@NotNull
	String routeName,
	@NotNull
	Long busRouteStationId,
	@NotNull
	String stationName,
	@NotNull
	ReviewContent content,
	@NotNull
	@Schema(implementation = TimeSlot.class)
	TimeSlot timeSlot,
	@NotNull
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
