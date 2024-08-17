package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;
import com.talkka.server.subway.exception.enums.InvalidUpdownEnumException;

import lombok.Getter;

@Getter
public enum Updown implements EnumCodeInterface {
	UP("상행(내선)", "0"), DOWN("하행(외선)", "1");

	private final String direction;
	private final String code;

	Updown(String direction, String code) {
		this.direction = direction;
		this.code = code;
	}

	public static Updown valueOfEnumString(String enumValue) {
		try {
			return Updown.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidUpdownEnumException();
		}
	}
}
