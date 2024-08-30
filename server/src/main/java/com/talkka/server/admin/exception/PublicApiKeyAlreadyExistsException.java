package com.talkka.server.admin.exception;

public class PublicApiKeyAlreadyExistsException extends RuntimeException {
	private static final String MESSAGE = "이미 등록된 api key 입니다.";

	public PublicApiKeyAlreadyExistsException() {
		super(MESSAGE);
	}
}
