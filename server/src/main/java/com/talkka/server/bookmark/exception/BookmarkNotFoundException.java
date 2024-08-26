package com.talkka.server.bookmark.exception;

public class BookmarkNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 북마크 입니다.";

	public BookmarkNotFoundException() {
		super(MESSAGE);
	}
}
