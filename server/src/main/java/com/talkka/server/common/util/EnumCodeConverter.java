package com.talkka.server.common.util;

import jakarta.persistence.AttributeConverter;

public abstract class EnumCodeConverter<T extends Enum<T> & EnumCodeInterface>
	implements AttributeConverter<T, String> {

	private final Class<T> attributeClass;

	public EnumCodeConverter(Class<T> clazz) {
		this.attributeClass = clazz;
	}

	@Override
	public String convertToDatabaseColumn(T attribute) {
		return attribute.getCode();
	}

	@Override
	public T convertToEntityAttribute(String dbData) {
		return EnumCodeConverterUtils.fromCode(attributeClass, dbData);
	}
}
