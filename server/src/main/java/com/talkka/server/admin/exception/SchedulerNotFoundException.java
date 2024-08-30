package com.talkka.server.admin.exception;

public class SchedulerNotFoundException extends RuntimeException {
	static final String message = "Scheduler not found";

	public SchedulerNotFoundException() {
		super(message);
	}
}
