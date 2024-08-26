package com.talkka.server.subway.exception;

public class TimetableNotFoundException extends RuntimeException {
	private static final String MESSAGE = "조회 가능한 시간표 정보가 없습니다.";

	public TimetableNotFoundException() {
		super(MESSAGE);
	}
}
