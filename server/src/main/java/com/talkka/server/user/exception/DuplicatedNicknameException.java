package com.talkka.server.user.exception;

public class DuplicatedNicknameException extends RuntimeException {
	private static final String MESSAGE = "이미 존재하는 닉네임입니다.";

	public DuplicatedNicknameException() {
		super(MESSAGE);
	}
}
