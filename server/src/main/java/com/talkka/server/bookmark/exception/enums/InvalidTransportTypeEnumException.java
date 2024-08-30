package com.talkka.server.bookmark.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidTransportTypeEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 교통수단 구분입니다.";

	public InvalidTransportTypeEnumException() {
		super(MESSAGE);
	}
}
