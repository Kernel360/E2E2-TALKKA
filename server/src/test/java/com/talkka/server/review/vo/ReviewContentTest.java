package com.talkka.server.review.vo;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.talkka.server.review.exception.InvalidReviewContentLengthException;

class ReviewContentTest {

	@DisplayName("리뷰 내용이 최소 길이보다 작을 경우 false를 반환한다.")
	@Test
	void isValidContentStringWhenBelowMinLength() {
		// given
		String content = "a".repeat(ReviewContent.MIN_LENGTH - 1);
		// when
		// then
		assertFalse(ReviewContent.isValidContentString(content));
	}

	@DisplayName("리뷰 내용이 최대 길이보다 클 경우 false를 반환한다.")
	@Test
	void isValidContentStringWhenOverMaxLength() {
		// given
		String content = "a".repeat(ReviewContent.MAX_LENGTH + 1);
		// when
		// then
		assertFalse(ReviewContent.isValidContentString(content));
	}

	@DisplayName("리뷰 내용이 최소 길이와 최대 길이 사이일 경우 true를 반환한다.")
	@Test
	void isValidContentString() {
		// given
		String content = "a".repeat(ReviewContent.MIN_LENGTH);
		// when
		// then
		assertTrue(ReviewContent.isValidContentString(content));
	}

	@DisplayName("리뷰 내용이 최소 길이보다 작을 경우 예외를 발생시킨다.")
	@Test
	void reviewContentConstructorWhenBelowMinLength() {
		// given
		String content = "a".repeat(ReviewContent.MIN_LENGTH - 1);
		// when
		// then
		assertThrows(InvalidReviewContentLengthException.class, () -> new ReviewContent(content));
	}

	@DisplayName("리뷰 내용이 최대 길이보다 클 경우 예외를 발생시킨다.")
	@Test
	void reviewContentConstructorWhenOverMaxLength() {
		// given
		String content = "a".repeat(ReviewContent.MAX_LENGTH + 1);
		// when
		// then
		assertThrows(InvalidReviewContentLengthException.class, () -> new ReviewContent(content));
	}

	@DisplayName("리뷰 내용이 최소 길이와 최대 길이 사이일 경우 객체를 생성한다.")
	@Test
	void reviewContentConstructor() {
		// given
		String content = "a".repeat(ReviewContent.MIN_LENGTH);
		// when
		ReviewContent reviewContent = new ReviewContent(content);
		// then
		assertNotNull(reviewContent);
	}

}