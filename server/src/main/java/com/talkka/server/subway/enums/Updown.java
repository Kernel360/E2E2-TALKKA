package com.talkka.server.subway.enums;

import com.talkka.server.common.util.EnumCodeInterface;

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
}
