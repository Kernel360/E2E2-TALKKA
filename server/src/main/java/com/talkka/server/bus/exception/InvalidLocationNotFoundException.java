package com.talkka.server.bus.exception;

public class InvalidLocationNotFoundException extends RuntimeException {
	private static final String MESSAGE = "올바르지 않은 위치정보 입니다.";

	public InvalidLocationNotFoundException() {
		super(MESSAGE);
	}
}
