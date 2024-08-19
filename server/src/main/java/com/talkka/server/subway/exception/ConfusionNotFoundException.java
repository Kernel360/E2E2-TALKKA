package com.talkka.server.subway.exception;

public class ConfusionNotFoundException extends RuntimeException {
	private static final String MESSAGE = "조회 가능한 혼잡도 정보가 없습니다.";

	public ConfusionNotFoundException() {
		super(MESSAGE);
	}
}
