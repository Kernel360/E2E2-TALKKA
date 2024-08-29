package com.talkka.server.admin.exception;

public class PublicApiKeyNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 api key 입니다.";

	public PublicApiKeyNotFoundException() {
		super(MESSAGE);
	}
}
