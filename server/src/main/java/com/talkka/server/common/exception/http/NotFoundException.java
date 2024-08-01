package com.talkka.server.common.exception.http;

import org.springframework.http.HttpStatus;

public class NotFoundException extends HttpBaseException {
	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
}
