package com.talkka.server.review.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.talkka.server.review.exception.InvalidReviewContentLengthException;

public record ReviewContent(String value) {
	public static final int MIN_LENGTH = 5;
	public static final int MAX_LENGTH = 200;

	public ReviewContent {
		if (value != null && !isValidContentString(value)) {
			throw new InvalidReviewContentLengthException();
		}
	}

	public static boolean isValidContentString(String value) {
		return value.length() >= MIN_LENGTH && value.length() <= MAX_LENGTH;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
