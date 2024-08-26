package com.talkka.server.user.exception;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidEmailException extends InvalidTypeException {
	private static final String MESSAGE = "이메일 형식이 올바르지 않습니다.";

	public InvalidEmailException() {
		super(MESSAGE);
	}
}
