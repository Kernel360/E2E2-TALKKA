package com.talkka.server.admin.dto;

import java.util.List;

public record SchedulerRespDto(
	String name,
	String cronString
) {
	public SchedulerRespDto(String name, List<String> cronList) {
		this(name, String.join(" | ", cronList));
	}
}
