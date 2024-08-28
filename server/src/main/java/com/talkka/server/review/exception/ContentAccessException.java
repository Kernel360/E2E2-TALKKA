package com.talkka.server.review.exception;

public class ContentAccessException extends RuntimeException {
	private static final String MESSAGE = "해당 자원에 접근할 수 없습니다.";

	public ContentAccessException() {
		super(MESSAGE);
	}
}
