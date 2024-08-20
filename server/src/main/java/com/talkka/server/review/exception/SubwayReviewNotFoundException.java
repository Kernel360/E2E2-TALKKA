package com.talkka.server.review.exception;

public class SubwayReviewNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 리뷰입니다.";

	public SubwayReviewNotFoundException() {
		super(MESSAGE);
	}
}
