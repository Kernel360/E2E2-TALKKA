package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;
import com.talkka.server.subway.exception.enums.InvalidDayTypeEnumException;

import lombok.Getter;

@Getter
public enum DayType implements EnumCodeInterface {
	DAY("평일", "DAY"),
	SAT("토요일", "SAT"),
	SUN("일요일", "SUN");

	private final String name;
	private final String code;

	DayType(String name, String code) {
		this.name = name;
		this.code = code;
	}

	public static DayType valueOfEnumString(String enumValue) {
		try {
			return DayType.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidDayTypeEnumException();
		}
	}
}
