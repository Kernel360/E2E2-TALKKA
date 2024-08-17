package com.talkka.server.user.util;

import com.talkka.server.user.vo.Nickname;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class NicknameDbConverter implements AttributeConverter<Nickname, String> {
	@Override
	public String convertToDatabaseColumn(Nickname nickname) {
		return nickname.nickname();
	}

	@Override
	public Nickname convertToEntityAttribute(String nickname) {
		return new Nickname(nickname);
	}
}
