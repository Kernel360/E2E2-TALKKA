package com.talkka.server.admin.exception;

public class InvalidCronExpressionException extends RuntimeException {
	private static final String MESSAGE = "잘못된 크론 표현식입니다";

	public InvalidCronExpressionException(String cron) {
		super(MESSAGE + " : " + cron);
	}
}
