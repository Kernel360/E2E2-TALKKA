package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.LowPlate;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class LowPlateConverter implements AttributeConverter<LowPlate, String> {
	@Override
	public String convertToDatabaseColumn(LowPlate lowPlate) {
		return lowPlate != null ? lowPlate.getCode() : null;
	}

	@Override
	public LowPlate convertToEntityAttribute(String value) {
		return LowPlate.fromCode(value);
	}
}
