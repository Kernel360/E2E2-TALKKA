package com.talkka.server.common.exception.domain;

import org.springframework.http.HttpStatus;

public abstract class DomainBaseException extends RuntimeException {
	private final HttpStatus statusCode;
	private final String errorCode;
	private final String message;

	public DomainBaseException(int statusCode, String errorCode, String message) {
		super(message);
		this.statusCode = HttpStatus.valueOf(statusCode);
		this.errorCode = errorCode;
		this.message = message;
	}

	public DomainBaseException(int statusCode, String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = HttpStatus.valueOf(statusCode);
		this.errorCode = errorCode;
		this.message = message;
	}
}
