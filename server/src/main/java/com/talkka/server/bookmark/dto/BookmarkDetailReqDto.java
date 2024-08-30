package com.talkka.server.bookmark.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.bus.dao.BusRouteStationEntity;

import jakarta.validation.constraints.NotNull;

public record BookmarkDetailReqDto(
	@NotNull
	Integer seq,
	@NotNull
	Long busRouteStationId
) {
	public BookmarkDetailEntity toEntity(
		BookmarkEntity bookmark,
		BusRouteStationEntity busRouteStation
	) {
		return BookmarkDetailEntity.builder()
			.seq(seq)
			.bookmark(bookmark)
			.routeStation(busRouteStation)
			.build();
	}
}
