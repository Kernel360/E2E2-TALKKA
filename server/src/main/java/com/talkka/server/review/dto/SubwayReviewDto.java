package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.review.dao.SubwayReviewEntity;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.subway.dao.SubwayStationEntity;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.user.dao.UserEntity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record SubwayReviewDto(
	@NotNull
	Long id,
	@NotNull
	Long userId,
	@NotNull
	Long stationId,
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
	public static SubwayReviewDto of(Long reviewId, Long userId, SubwayReviewReqDto reqDto) throws
		InvalidTypeException {

		Line line = Line.valueOfEnumString(reqDto.line());
		Updown updown = Updown.valueOfEnumString(reqDto.updown());
		ReviewContent contentValue = new ReviewContent(reqDto.content());
		TimeSlot timeSlotValue = TimeSlot.valueOfEnumString(reqDto.timeSlot());
		Rating ratingValue = new Rating(Integer.parseInt(reqDto.rating()));

		return new SubwayReviewDto(
			reviewId,
			userId,
			reqDto.stationId(),
			line,
			updown,
			contentValue,
			timeSlotValue,
			ratingValue
		);
	}

	public static SubwayReviewDto of(Long userId, SubwayReviewReqDto reqDto) {
		return of(null, userId, reqDto);
	}

	public static SubwayReviewDto of(SubwayReviewEntity entity) {
		return new SubwayReviewDto(
			entity.getId(),
			entity.getWriter().getId(),
			entity.getStation().getId(),
			entity.getLine(),
			entity.getUpdown(),
			entity.getContent(),
			entity.getTimeSlot(),
			entity.getRating()
		);
	}

	public SubwayReviewEntity toEntity(UserEntity user, SubwayStationEntity station) {
		return new SubwayReviewEntity(
			id,
			user,
			station,
			line,
			updown,
			content,
			timeSlot,
			rating,
			null,
			null
		);
	}
}
