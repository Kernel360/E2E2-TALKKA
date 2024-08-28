package com.talkka.server.bookmark.dto;

import java.util.ArrayList;
import java.util.List;

import com.talkka.server.bookmark.dao.BookmarkEntity;
import com.talkka.server.user.dao.UserEntity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BookmarkReqDto(
	@NotNull
	@Size(min = 2, max = 10, message = "북마크 이름의 길이는 2자 이상 10자 미만 입니다.")
	String name,
	@NotNull
	List<BookmarkDetailReqDto> details
) {
	public BookmarkEntity toEntity(UserEntity user) {
		return BookmarkEntity.builder()
			.name(name)
			.user(user)
			.details(new ArrayList<>())
			.build();
	}
}
