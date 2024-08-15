package com.talkka.server.review.exception;

import com.talkka.server.common.exception.InvalidTypeException;
import com.talkka.server.review.vo.ReviewContent;

public class InvalidReviewContentLengthException extends InvalidTypeException {
	private static final String MESSAGE = String.format("리뷰는 최소 %d자부터 최대 %d자 작성할 수 있습니다.",
		ReviewContent.MIN_LENGTH, ReviewContent.MAX_LENGTH);

	public InvalidReviewContentLengthException() {
		super(MESSAGE);
	}
}
