package com.talkka.server.bookmark.exception;

public class BookmarkUserNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 사용자 입니다.";

	public BookmarkUserNotFoundException() {
		super(MESSAGE);
	}
}
