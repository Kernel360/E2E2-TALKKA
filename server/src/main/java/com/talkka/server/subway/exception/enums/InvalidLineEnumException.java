package com.talkka.server.subway.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidLineEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 호선입니다.";

	public InvalidLineEnumException() {
		super(MESSAGE);
	}
}
