package com.talkka.server.user.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.talkka.server.oauth.enums.AuthRole;
import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import lombok.Builder;

@Builder
public record UserDto(
	Long userId,
	String name,
	Email email,
	Nickname nickname,
	String oauthProvider,
	String accessToken,
	AuthRole authRole,
	LocalDateTime createdAt,
	LocalDateTime updatedAt
) {

	public static UserDto of(UserEntity userEntity) {
		return new UserDto(
			userEntity.getId(),
			userEntity.getName(),
			userEntity.getEmail(),
			userEntity.getNickname(),
			userEntity.getOauthProvider(),
			userEntity.getAccessToken(),
			userEntity.getAuthRole(),
			userEntity.getCreatedAt(),
			userEntity.getUpdatedAt()
		);
	}

	public UserEntity toEntity() {
		return new UserEntity(
			userId,
			name,
			email,
			nickname,
			oauthProvider,
			accessToken,
			authRole,
			createdAt,
			updatedAt,
			new ArrayList<>(),
			new ArrayList<>()
		);
	}
}
