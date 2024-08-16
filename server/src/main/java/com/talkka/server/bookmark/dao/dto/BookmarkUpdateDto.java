package com.talkka.server.bookmark.dao.dto;

import java.util.ArrayList;
import java.util.List;

import com.talkka.server.bookmark.dao.entity.BookmarkEntity;
import com.talkka.server.user.dao.UserEntity;

public record BookmarkUpdateDto(
	Long id,
	String name,
	List<BookmarkDetailReqDto> details
) {
	public BookmarkEntity toEntity(UserEntity user) {
		return BookmarkEntity.builder()
			.id(id)
			.name(name)
			.user(user)
			.details(new ArrayList<>())
			.build();
	}
}
