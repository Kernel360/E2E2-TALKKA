package com.talkka.server.user.dto;

import com.talkka.server.user.vo.Email;
import com.talkka.server.user.vo.Nickname;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "유저 정보 응답 DTO")
public record UserRespDto(
	@NotNull
	Long userId,
	@NotNull
	String name,
	@NotNull
	Email email,
	@NotNull
	Nickname nickname,
	@NotNull
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
