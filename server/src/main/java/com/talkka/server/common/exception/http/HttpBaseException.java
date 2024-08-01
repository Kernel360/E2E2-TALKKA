package com.talkka.server.common.exception.http;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public abstract class HttpBaseException extends RuntimeException {
	private final HttpStatus statusCode;
	private final String message;

	public HttpBaseException(int statusCode, String message) {
		super(message);
		this.statusCode = HttpStatus.valueOf(statusCode);
		this.message = message;
	}

	public HttpBaseException(int statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = HttpStatus.valueOf(statusCode);
		this.message = message;
	}

	public HttpBaseException(HttpStatus httpStatus, String message) {
		this(httpStatus.value(), message);
	}

}
