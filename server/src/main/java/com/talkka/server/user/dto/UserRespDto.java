package com.talkka.server.user.dto;

import lombok.Builder;

@Builder
public record UserRespDto(
	Long userId,
	String name,
	String email,
	String nickname,
	String oauthProvider
) {
	public static UserRespDto of(UserDto userDto) {
		return new UserRespDto(
			userDto.userId(),
			userDto.name(),
			userDto.email(),
			userDto.nickname(),
			userDto.oauthProvider()
		);
	}
}
