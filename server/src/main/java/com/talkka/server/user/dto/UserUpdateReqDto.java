package com.talkka.server.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record UserUpdateReqDto(
	@NotNull
	String nickname
) {
}
