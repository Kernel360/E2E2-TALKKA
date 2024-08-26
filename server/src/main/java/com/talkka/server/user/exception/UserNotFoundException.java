package com.talkka.server.user.exception;

public class UserNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 유저입니다.";

	public UserNotFoundException() {
		super(MESSAGE);
	}
}
