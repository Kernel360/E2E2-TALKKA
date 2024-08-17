package com.talkka.server.bookmark.enums;

import com.talkka.server.bookmark.exception.enums.InvalidTransportTypeEnumException;

import lombok.Getter;

@Getter
public enum TransportType {
	BUS("bus"), SUBWAY("subway");

	private final String type;

	TransportType(String type) {
		this.type = type;
	}

	public static TransportType valueOfEnumString(String enumValue) {
		try {
			return TransportType.valueOf(enumValue);
		} catch (IllegalArgumentException exception) {
			throw new InvalidTransportTypeEnumException();
		}
	}
}
