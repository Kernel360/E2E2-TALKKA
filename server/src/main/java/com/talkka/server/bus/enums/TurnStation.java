package com.talkka.server.bus.enums;

import com.talkka.server.common.util.EnumCodeInterface;

import lombok.Getter;

@Getter
public enum TurnStation implements EnumCodeInterface {
	TURN_STATION("Y"), NOT_TURN_STATION("N");

	private final String code;

	TurnStation(String code) {
		this.code = code;
	}
}
