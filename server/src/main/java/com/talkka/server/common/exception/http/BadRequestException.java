package com.talkka.server.common.exception.http;

import org.springframework.http.HttpStatus;

public class BadRequestException extends HttpBaseException {
	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST, message);
	}
}
