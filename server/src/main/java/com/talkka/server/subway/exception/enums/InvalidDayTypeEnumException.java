package com.talkka.server.subway.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidDayTypeEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 날짜 구분입니다.";

	public InvalidDayTypeEnumException() {
		super(MESSAGE);
	}
}
