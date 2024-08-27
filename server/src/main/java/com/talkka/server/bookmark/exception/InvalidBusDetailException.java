package com.talkka.server.bookmark.exception;

public class InvalidBusDetailException extends RuntimeException {
	private static final String MESSAGE = "잘못된 버스 상세 정보입니다.";

	public InvalidBusDetailException() {
		super(MESSAGE);
	}
}
