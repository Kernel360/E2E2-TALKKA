package com.talkka.server.subway.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidExpressEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 급행 여부입니다.";

	public InvalidExpressEnumException() {
		super(MESSAGE);
	}
}
