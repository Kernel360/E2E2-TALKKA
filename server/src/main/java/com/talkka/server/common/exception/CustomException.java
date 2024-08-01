package com.talkka.server.common.exception;

import org.springframework.http.HttpStatus;

import com.talkka.server.common.enums.StatusCode;

import lombok.Getter;

@Getter
public abstract class CustomException extends RuntimeException {
	private final HttpStatus statusCode;
	private final StatusCode errorCode;

	public CustomException(HttpStatus statusCode, StatusCode errorCode) {
		super(errorCode.getMessage());
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}

	public CustomException(HttpStatus statusCode, StatusCode errorCode, Throwable cause) {
		super(errorCode.getMessage(), cause);
		this.statusCode = statusCode;
		this.errorCode = errorCode;
	}
}
