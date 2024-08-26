package com.talkka.server.bookmark.dto;

import com.talkka.server.bookmark.dao.BookmarkDetailEntity;
import com.talkka.server.bookmark.enums.TransportType;
import com.talkka.server.subway.enums.Updown;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record BookmarkDetailRespDto(
	@NotNull
	Integer seq,
	@NotNull
	@Schema(implementation = TransportType.class)
	TransportType type,
	@NotNull
	Long subwayStationId,
	@NotNull
	@Schema(implementation = Updown.class)
	Updown subwayUpdown,
	@NotNull
	Long busRouteStationId
) {
	public static BookmarkDetailRespDto of(BookmarkDetailEntity entity) {
		return new BookmarkDetailRespDto(
			entity.getSeq(),
			entity.getType(),
			entity.getSubwayStationId(),
			entity.getSubwayUpdown(),
			entity.getBusRouteStationId()
		);
	}
}
