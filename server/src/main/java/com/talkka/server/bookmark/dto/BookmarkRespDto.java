package com.talkka.server.bookmark.dto;

import java.util.List;

import com.talkka.server.bookmark.dao.BookmarkEntity;

import jakarta.validation.constraints.NotNull;

public record BookmarkRespDto(
	@NotNull
	Long id,
	@NotNull
	String name,
	@NotNull
	Long userId,
	@NotNull
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
