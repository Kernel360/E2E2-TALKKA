package com.talkka.server.common.exception;

import lombok.Getter;

@Getter
public abstract class InvalidTypeException extends RuntimeException {
	public InvalidTypeException(String message) {
		super(message);
	}
}
