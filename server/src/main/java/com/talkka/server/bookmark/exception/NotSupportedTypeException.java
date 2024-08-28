package com.talkka.server.bookmark.exception;

public class NotSupportedTypeException extends RuntimeException {
	private static final String MESSAGE = "지원하지 않는 타입입니다.";

	public NotSupportedTypeException() {
		super(MESSAGE);
	}
}
