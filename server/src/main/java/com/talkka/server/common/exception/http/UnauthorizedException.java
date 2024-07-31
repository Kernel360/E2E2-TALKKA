package com.talkka.server.common.exception.http;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends HttpBaseException {
	public UnauthorizedException(String message) {
		super(HttpStatus.UNAUTHORIZED, message);
	}
}
