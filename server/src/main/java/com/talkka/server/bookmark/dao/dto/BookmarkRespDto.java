package com.talkka.server.bookmark.dao.dto;

import java.util.List;

import com.talkka.server.bookmark.dao.BookmarkEntity;

public record BookmarkRespDto(
	String name,
	List<BookmarkDetailRespDto> details
) {
	public static BookmarkRespDto of(BookmarkEntity bookmarkEntity) {
		return new BookmarkRespDto(
			bookmarkEntity.getName(),
			bookmarkEntity.getBookmarkDetails().stream()
				.map(BookmarkDetailRespDto::of)
				.toList()
		);
	}
}
