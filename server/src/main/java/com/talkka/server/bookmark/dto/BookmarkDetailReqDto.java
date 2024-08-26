package com.talkka.server.bookmark.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.bookmark.enums.TransportType;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BookmarkDetailReqDto(
	@NotNull
	Integer seq,
	@NotNull
	String type,
	@NotNull
	Long subwayStationId,
	@NotNull
	@Schema(implementation = Updown.class)
	Updown subwayUpdown,
	@NotNull
	Long busRouteStationId
) {
	public BookmarkDetailEntity toEntity(
		BookmarkEntity bookmark
	) {
		return BookmarkDetailEntity.builder()
			.seq(seq)
			.bookmark(bookmark)
			.type(TransportType.valueOf(type))
			.subwayStationId(subwayStationId)
			.subwayUpdown(subwayUpdown)
			.busRouteStationId(busRouteStationId)
			.build();
	}
}
