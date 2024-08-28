package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.SubwayReviewEntity;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.user.vo.Nickname;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayReviewRespDto(
	@NotNull
	Long id,
	@NotNull
	Long userId,
	@NotNull
	Nickname userName,
	@NotNull
	Long stationId,
	@NotNull
	String stationName,
	@NotNull
	@Schema(implementation = Line.class)
	Line line,
	@NotNull
	@Schema(implementation = Updown.class)
	Updown updown,
	@NotNull
	ReviewContent content,
	@NotNull
	@Schema(implementation = TimeSlot.class)
	TimeSlot timeSlot,
	@NotNull
	Rating rating
) {
	public static SubwayReviewRespDto of(SubwayReviewEntity subwayEntity) {
		return new SubwayReviewRespDto(
			subwayEntity.getId(),
			subwayEntity.getWriter().getId(),
			subwayEntity.getWriter().getNickname(),
			subwayEntity.getStation().getId(),
			subwayEntity.getStation().getStationName(),
			subwayEntity.getLine(),
			subwayEntity.getUpdown(),
			subwayEntity.getContent(),
			subwayEntity.getTimeSlot(),
			subwayEntity.getRating()
		);
	}
}
