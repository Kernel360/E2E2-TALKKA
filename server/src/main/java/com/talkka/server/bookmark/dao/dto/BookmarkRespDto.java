package com.talkka.server.bookmark.dao.dto;

import java.util.List;

import com.talkka.server.bookmark.dao.entity.BookmarkEntity;
import com.talkka.server.user.dto.UserDto;
import com.talkka.server.user.dto.UserRespDto;

public record BookmarkRespDto(
	Long id,
	String name,
	UserRespDto user,
	List<BookmarkDetailRespDto> details
) {
	public static BookmarkRespDto of(BookmarkEntity bookmark) {
		return new BookmarkRespDto(
			bookmark.getId(),
			bookmark.getName(),
			UserRespDto.of(UserDto.of(bookmark.getUser())),
			bookmark.getDetails().stream()
				.map(BookmarkDetailRespDto::of)
				.toList()
		);
	}
}
