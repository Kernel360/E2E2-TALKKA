package com.talkka.server.user.dto;

import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.enums.Grade;

import lombok.Builder;

@Builder
public record UserCreateDto(
	String name,
	String email,
	String nickname,
	String oauthProvider,
	String accessToken,
	Grade grade
) {
	public UserEntity toEntity() {
		return UserEntity.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.oauthProvider(oauthProvider)
			.accessToken(accessToken)
			.grade(grade)
			.build();
	}
}
