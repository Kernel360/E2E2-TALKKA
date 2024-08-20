package com.talkka.server.review.dto;

import com.talkka.server.common.enums.TimeSlot;
import com.talkka.server.review.dao.SubwayReviewEntity;
import com.talkka.server.review.vo.Rating;
import com.talkka.server.review.vo.ReviewContent;
import com.talkka.server.subway.enums.Line;
import com.talkka.server.subway.enums.Updown;
import com.talkka.server.user.vo.Nickname;

import lombok.Builder;

@Builder
public record SubwayReviewRespDto(
	Long id,
	Long userId,
	Nickname userName,
	Long stationId,
	String stationName,
	Line line,
	Updown updown,
	ReviewContent content,
	TimeSlot timeSlot,
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
