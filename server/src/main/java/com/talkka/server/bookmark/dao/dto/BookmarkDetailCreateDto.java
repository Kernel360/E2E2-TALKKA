package com.talkka.server.bookmark.dao.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.bookmark.enums.TransportType;
import com.talkka.server.subway.enums.Updown;

public record BookmarkDetailCreateDto(
	Integer seq,
	TransportType type,
	Long subwayStationId,
	Updown subwayUpdown,
	Long busRouteStationId
) {
	public BookmarkDetailEntity toEntity(
		BookmarkEntity bookmark
	) {
		return BookmarkDetailEntity.builder()
			.seq(seq)
			.bookmark(bookmark)
			.type(type)
			.subwayStationId(subwayStationId)
			.subwayUpdown(subwayUpdown)
			.busRouteStationId(busRouteStationId)
			.build();
	}
}
