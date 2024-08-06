package com.talkka.server.user.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;

import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.enums.Grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class UserDto {
	private Long userId;
	private String name;
	private String email;
	private String nickname;
	private String oauthProvider;
	private String accessToken;
	private Grade grade;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	public static UserDto of(UserEntity userEntity) {
		return new UserDto(
			userEntity.getId(),
			userEntity.getName(),
			userEntity.getEmail(),
			userEntity.getNickname(),
			userEntity.getOauthProvider(),
			userEntity.getAccessToken(),
			userEntity.getGrade(),
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
			grade,
			createdAt,
			updatedAt,
			new ArrayList<>());
	}
}
