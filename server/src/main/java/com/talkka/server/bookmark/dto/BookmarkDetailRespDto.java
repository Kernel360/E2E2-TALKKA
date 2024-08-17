package com.talkka.server.bookmark.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.subway.enums.Updown;

public record BookmarkDetailRespDto(
	Integer seq,
	String type,
	Long subwayStationId,
	Updown subwayUpdown,
	Long busRouteStationId
) {
	public static BookmarkDetailRespDto of(BookmarkDetailEntity entity) {
		return new BookmarkDetailRespDto(
			entity.getSeq(),
			entity.getType().getType(),
			entity.getSubwayStationId(),
			entity.getSubwayUpdown(),
			entity.getBusRouteStationId()
		);
	}
}
