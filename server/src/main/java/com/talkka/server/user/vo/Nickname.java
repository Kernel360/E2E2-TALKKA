package com.talkka.server.user.vo;

import com.fasterxml.jackson.annotation.JsonValue;
import com.talkka.server.user.exception.InvalidNicknameException;

public record Nickname(String nickname) {
	private static final String REGEX = "^[a-zA-Z0-9가-힣]{2,20}$";

	public Nickname {
		if (!isValid(nickname)) {
			throw new InvalidNicknameException();
		}
	}

	public static boolean isValid(String nickname) {
		return nickname.matches(REGEX);
	}

	@JsonValue
	public String getValue() {
		return nickname;
	}

	@Override
	public String toString() {
		return nickname;
	}
}
