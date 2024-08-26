package com.talkka.server.review.exception;

public class ContentAccessException extends RuntimeException {
	private static final String MESSAGE = "리뷰 작성자가 일치하지 않습니다.";

	public ContentAccessException() {
		super(MESSAGE);
	}
}
