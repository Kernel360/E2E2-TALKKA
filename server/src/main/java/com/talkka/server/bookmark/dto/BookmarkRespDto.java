package com.talkka.server.bookmark.dto;

import java.util.List;

import com.talkka.server.bookmark.dao.BookmarkEntity;

public record BookmarkRespDto(
	Long id,
	String name,
	Long userId,
	List<BookmarkDetailRespDto> details
) {
	public static BookmarkRespDto of(BookmarkEntity bookmark) {
		return new BookmarkRespDto(
			bookmark.getId(),
			bookmark.getName(),
			bookmark.getUser().getId(),
			bookmark.getDetails().stream()
				.map(BookmarkDetailRespDto::of)
				.toList()
		);
	}
}
