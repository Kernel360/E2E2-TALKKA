package com.talkka.server.user.dto;

import com.talkka.server.user.dao.UserEntity;
import com.talkka.server.user.enums.Grade;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
	private String nickname;
	private String oauthProvider;
	private String accessToken;
	private Grade grade;

	public UserEntity toEntity() {
		return UserEntity.builder()
			.nickname(nickname)
			.oauthProvider(oauthProvider)
			.accessToken(accessToken)
			.grade(grade)
			.build();
	}
}
