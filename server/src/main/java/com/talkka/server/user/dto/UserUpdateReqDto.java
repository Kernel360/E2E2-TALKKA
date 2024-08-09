package com.talkka.server.user.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserUpdateReqDto(
	@NotNull
	@Size(min = 2, max = 20, message = "닉네임은 2자 이상 20자 이하로 입력해주세요.")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣]*$", message = "닉네임은 영문 대소문자, 한글, 숫자로만 입력해주세요.")
	String nickname
) {
}
