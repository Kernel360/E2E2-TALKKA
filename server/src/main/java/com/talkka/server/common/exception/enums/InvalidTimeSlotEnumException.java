package com.talkka.server.common.exception.enums;

import com.talkka.server.common.exception.InvalidTypeException;

public class InvalidTimeSlotEnumException extends InvalidTypeException {
	private static final String MESSAGE = "잘못된 시간대입니다.";

	public InvalidTimeSlotEnumException() {
		super(MESSAGE);
	}
}
