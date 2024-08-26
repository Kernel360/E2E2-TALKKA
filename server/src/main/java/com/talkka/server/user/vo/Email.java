package com.talkka.server.user.vo;

import java.util.regex.Pattern;

import com.fasterxml.jackson.annotation.JsonValue;
import com.talkka.server.user.exception.InvalidEmailException;

import jakarta.validation.constraints.NotNull;

public record Email(@NotNull String email) {
	private static final String REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(REGEX);

	public Email {
		if (!isValid(email)) {
			throw new InvalidEmailException();
		}
	}

	public static boolean isValid(String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	@JsonValue
	public String getValue() {
		return email;
	}

	@Override
	public String toString() {
		return email;
	}
}
