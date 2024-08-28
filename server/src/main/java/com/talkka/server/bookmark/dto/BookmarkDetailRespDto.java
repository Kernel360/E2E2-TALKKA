package com.talkka.server.bookmark.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bus.dto.BusRouteStationRespDto;

import jakarta.validation.constraints.NotNull;

public record BookmarkDetailRespDto(
	@NotNull
	Integer seq,
	@NotNull
	BusRouteStationRespDto routeStation
) {
	public static BookmarkDetailRespDto of(BookmarkDetailEntity entity) {
		return new BookmarkDetailRespDto(
			entity.getSeq(),
			BusRouteStationRespDto.of(entity.getRouteStation()
			));
	}
}
