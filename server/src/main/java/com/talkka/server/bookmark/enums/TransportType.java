package com.talkka.server.bookmark.enums;

import com.talkka.server.bookmark.exception.enums.InvalidTransportTypeEnumException;

import lombok.Getter;

@Getter
public enum TransportType {
	BUS, SUBWAY;

	TransportType() {
	}

	public static TransportType valueOfEnumString(String enumValue) {
		try {
			return TransportType.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidTransportTypeEnumException();
		}
	}

	public boolean isBus() {
		return this == BUS;
	}
}
