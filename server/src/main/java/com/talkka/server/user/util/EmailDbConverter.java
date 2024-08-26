package com.talkka.server.user.util;

import com.talkka.server.user.vo.Email;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class EmailDbConverter implements AttributeConverter<Email, String> {

	@Override
	public String convertToDatabaseColumn(Email email) {
		return email.email();
	}

	@Override
	public Email convertToEntityAttribute(String email) {
		return new Email(email);
	}
}
