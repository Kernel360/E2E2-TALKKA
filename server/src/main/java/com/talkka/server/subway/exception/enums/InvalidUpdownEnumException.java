package com.talkka.server.subway.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidUpdownEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 상하 구분입니다.";

	public InvalidUpdownEnumException() {
		super(MESSAGE);
	}
}
