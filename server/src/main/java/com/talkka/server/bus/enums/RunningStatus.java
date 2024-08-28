package com.talkka.server.bus.enums;

public enum RunningStatus {
	RUN, PASS, STOP, WAIT, UNKNOWN;

	public static RunningStatus fromString(String status) {
		try {
			return RunningStatus.valueOf(status);
		} catch (Exception e) {
			return UNKNOWN;
		}
	}
}
