package com.talkka.server.user.dto;

import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "유저 정보 응답 DTO")
public record UserRespDto(
	Long userId,
	String name,
	Email email,
	Nickname nickname,
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
