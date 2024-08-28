package com.talkka.server.user.dto;

import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserCreateDto(
	@NotNull
	String name,
	@NotNull
	Email email,
	@NotNull
	Nickname nickname,
	@NotNull
	String oauthProvider,
	@NotNull
	String accessToken,
	@NotNull
	AuthRole authRole
) {
	public UserEntity toEntity() {
		return UserEntity.builder()
			.name(name)
			.email(email)
			.nickname(nickname)
			.oauthProvider(oauthProvider)
			.accessToken(accessToken)
			.authRole(authRole)
			.build();
	}
}
