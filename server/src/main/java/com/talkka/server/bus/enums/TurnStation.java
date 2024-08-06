package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum TurnStation implements EnumCodeInterface {
	TURN_STATION("1"), NOT_TURN_STATION("0");

	private final String code;

	TurnStation(String code) {
		this.code = code;
	}
}
