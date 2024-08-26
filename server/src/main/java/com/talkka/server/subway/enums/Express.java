package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;
import com.talkka.server.subway.exception.enums.InvalidExpressEnumException;

import lombok.Getter;

@Getter
public enum Express implements EnumCodeInterface {
	NORMAL("일반", "0"), EXPRESS("급행", "1");

	private final String type;
	private final String code;

	Express(String type, String code) {
		this.type = type;
		this.code = code;
	}

	public static Express valueOfEnumString(String enumValue) {
		try {
			return Express.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidExpressEnumException();
		}
	}
}
