package com.talkka.server.bus.util;

import com.talkka.server.bus.enums.CenterStation;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class CenterStationConverter implements AttributeConverter<CenterStation, String> {

	@Override
	public String convertToDatabaseColumn(CenterStation attribute) {
		return attribute.getCode();
	}

	@Override
	public CenterStation convertToEntityAttribute(String dbData) {
		return CenterStation.fromCode(dbData);
	}
}
