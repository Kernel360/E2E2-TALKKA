package com.talkka.server.common.exception.enums;

public class InvalidEnumCodeException extends RuntimeException {
	private static final String messageFormat = "Invalid Enum Code (%s) in ENUM (%s)";

	public InvalidEnumCodeException(Class<?> clazz, String code) {
		super(String.format(messageFormat, code, clazz.getSimpleName()));
	}
}
