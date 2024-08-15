package com.talkka.server.review.exception;

public class BusReviewNotFoundException extends RuntimeException {
	private static final String MESSAGE = "존재하지 않는 리뷰입니다.";

	public BusReviewNotFoundException() {
		super(MESSAGE);
	}
}
