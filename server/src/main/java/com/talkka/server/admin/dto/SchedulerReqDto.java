package com.talkka.server.admin.dto;

public record SchedulerReqDto(
	String name,
	String cronString
) {
}
