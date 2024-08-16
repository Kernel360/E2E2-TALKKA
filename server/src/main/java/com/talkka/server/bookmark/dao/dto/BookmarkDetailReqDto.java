package com.talkka.server.bookmark.dao.dto;

import com.talkka.server.bookmark.dao.entity.BookmarkDetailEntity;
import com.talkka.server.bookmark.dao.entity.BookmarkEntity;
import com.talkka.server.bookmark.enums.TransportType;
import com.talkka.server.subway.enums.Updown;

public record BookmarkDetailReqDto(
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
