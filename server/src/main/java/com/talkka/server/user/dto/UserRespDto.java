package com.talkka.server.user.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class UserRespDto {
	private Long userId;
	private String name;
	private String email;
	private String nickname;
	private String oauthProvider;

	public static UserRespDto of(UserDto userDto) {
		return new UserRespDto(
			userDto.getUserId(),
			userDto.getName(),
			userDto.getEmail(),
			userDto.getNickname(),
			userDto.getOauthProvider()
		);
	}
}
