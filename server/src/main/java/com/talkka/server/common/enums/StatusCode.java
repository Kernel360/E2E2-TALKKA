package com.talkka.server.common.enums;

import lombok.Getter;

// NOTE: 현재 Status code 에 대해서 아직 정의하지 못하였음. 아래는 예시임.
@Getter
public enum StatusCode {
	OK(0, "OK"),
	// USER DOMAIN
	DUPLICATED_NICKNAME(1001, "Duplicated nickname"),
	// BUS DOMAIN
	NO_BUS(2002, "No bus"),
	// SUBWAY DOMAIN
	NO_SUBWAY(3001, "No subway"),
	;

	private final int code;
	private final String message;

	StatusCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public static StatusCode fromCode(int code) {
		for (StatusCode statusCode : StatusCode.values()) {
			if (statusCode.code == code) {
				return statusCode;
			}
		}
		throw new IllegalArgumentException("Invalid code: " + code);
	}
}
