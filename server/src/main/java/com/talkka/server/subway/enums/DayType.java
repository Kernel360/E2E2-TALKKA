package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum DayType implements EnumCodeInterface {
	DAY("평일", "DAY"),
	SAT("토요일", "SAT"),
	SUN("일요일", "SUN");

	private String name;
	private String code;

	DayType(String name, String code) {
		this.name = name;
		this.code = code;
	}
}
