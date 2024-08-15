package com.talkka.server.review.exception;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.review.vo.Rating;

public class InvalidRatingException extends InvalidTypeException {
	private static final String MESSAGE = String.format("리뷰 평점은 %d점부터 %d점까지만 가능합니다.", Rating.MIN, Rating.MAX);

	public InvalidRatingException() {
		super(MESSAGE);
	}
}
