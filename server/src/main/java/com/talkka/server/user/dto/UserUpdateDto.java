package com.talkka.server.user.dto;

import com.talkka.server.user.exception.InvalidNicknameException;
import com.talkka.server.user.vo.Nickname;

import lombok.Builder;

@Builder
public record UserUpdateDto(
	Long userId,
	Nickname nickname
) {

	public static UserUpdateDto of(Long userId, UserUpdateReqDto reqDto) throws InvalidNicknameException {
		return new UserUpdateDto(userId, new Nickname(reqDto.nickname()));
	}
}
