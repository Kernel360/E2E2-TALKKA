package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.PlateType;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PlateTypeConverter implements AttributeConverter<PlateType, String> {
	@Override
	public String convertToDatabaseColumn(PlateType plateType) {
		return plateType != null ? plateType.getCode() : null;
	}

	@Override
	public PlateType convertToEntityAttribute(String value) {
		return PlateType.fromCode(value);
	}
}
