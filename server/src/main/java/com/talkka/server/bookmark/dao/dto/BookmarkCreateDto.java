package com.talkka.server.bookmark.dao.dto;

import java.util.List;

import com.talkka.server.bookmark.dao.BookmarkEntity;

public record BookmarkCreateDto(
	String name,
	List<BookmarkDetailCreateDto> details
) {
	public BookmarkEntity toEntity() {
		return BookmarkEntity.builder()
			.name(name)
			.build();
	}
}
