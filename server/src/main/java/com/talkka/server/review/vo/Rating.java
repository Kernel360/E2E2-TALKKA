package com.talkka.server.review.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.talkka.server.review.exception.InvalidRatingException;

public record Rating(int value) {
	public static final int MIN = 1;
	public static final int MAX = 10;

	public Rating {
		if (!isValidRating(value)) {
			throw new InvalidRatingException();
		}
	}

	public static boolean isValidRating(int value) {
		return value >= MIN && value <= MAX;
	}

	@JsonValue
	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return String.valueOf(value);
	}
}
