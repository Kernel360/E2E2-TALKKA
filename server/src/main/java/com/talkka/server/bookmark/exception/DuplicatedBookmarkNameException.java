package com.talkka.server.bookmark.exception;

public class DuplicatedBookmarkNameException extends RuntimeException {
	private static final String MESSAGE = "이미 존재하는 북마크 이름입니다.";

	public DuplicatedBookmarkNameException() {
		super(MESSAGE);
	}
}
