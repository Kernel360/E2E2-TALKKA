package com.talkka.server.user.exception;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidNicknameException extends InvalidTypeException {
	private static final String MESSAGE = "닉네임은 2자 이상 20자 이하의 영문, 숫자, 한글만 사용 가능합니다.";

	public InvalidNicknameException() {
		super(MESSAGE);
	}
}
