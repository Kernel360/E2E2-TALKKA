package com.talkka.server.bus.exception;

public class GetBusLiveArrivalInfoFailedException extends RuntimeException {
	private static final String MESSAGE = "버스 도착 정보 획득에 실패하였습니다.";

	public GetBusLiveArrivalInfoFailedException() {
		super(MESSAGE);
	}
}
