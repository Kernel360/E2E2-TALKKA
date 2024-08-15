package com.talkka.server.review.vo;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.talkka.server.review.exception.InvalidRatingException;

class RatingTest {

	@DisplayName("평점이 1이상 10 이하일 경우 true를 반환한다.")
	@ParameterizedTest
	@ValueSource(ints = {1, 5, 10})
	void isValidRating(int value) {
		// given
		// when
		boolean actual = Rating.isValidRating(value);
		// then
		assertThat(actual).isTrue();
	}

	@DisplayName("평점이 범위를 벗어날 경우 false를 반환한다.")
	@ParameterizedTest
	@ValueSource(ints = {0, -1, -10, 11, 100})
	void isValidRatingWhenBelowMin(int value) {
		// given
		// when
		boolean actual = Rating.isValidRating(value);
		// then
		assertThat(actual).isFalse();

	}

	@ParameterizedTest
	@DisplayName("Rating 객체 생성시 범위 내의 값이라면 생성한다.")
	@ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
	void ratingConstructor() {
		// given
		int value = 5;
		// when
		Rating rating = new Rating(value);
		// then
		assertThat(rating).isNotNull();
	}

	@ParameterizedTest
	@DisplayName("Rating 객체 생성시 범위를 벗어난 값이라면 예외를 발생시킨다.")
	@ValueSource(ints = {0, -1, -10, 11, 100})
	void ratingConstructorNotInRange(int value) {
		// given
		// when
		// then
		assertThatThrownBy(() -> new Rating(value)).isInstanceOf(InvalidRatingException.class);
	}
}